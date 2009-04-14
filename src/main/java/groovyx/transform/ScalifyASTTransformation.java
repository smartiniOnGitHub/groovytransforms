/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package groovyx.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.objectweb.asm.Opcodes;

import scala.ScalaObject;

/**
 * Handles generation of code for the {@Scalify} annotation.<p>
 * It adds Scala-friendly property accessors for each property
 * found in the annotated class. It also generates Scala compliant
 * operator overloading methods for the following operators:
 * +, -, *, /, %, ^, &amp, |,**, &lt;&lt;, &gt;&gt;, - (unary),
 * + (unary), ~ (unary); whenever their Groovy counterparts are
 * present.
 *
 * Heavily influenced by Dano Ferrin's work on @Bindable.<p>
 *
 * @author Andres Almiray
 */
@GroovyASTTransformation(phase= CompilePhase.CANONICALIZATION)
public class ScalifyASTTransformation implements ASTTransformation, Opcodes {
    protected static ClassNode boundClassNode = new ClassNode(Scalify.class);
    private static final ClassNode SCALA_OBJECT_IFACE = new ClassNode(ScalaObject.class);
    private static final Map<String,MethodInfo> METHOD_INFO_MAP = new HashMap<String,MethodInfo>();

    static {
        METHOD_INFO_MAP.put("plus", new MethodInfo("$plus",1));
        METHOD_INFO_MAP.put("minus", new MethodInfo("$minus",1));
        METHOD_INFO_MAP.put("multiply", new MethodInfo("$times",1));
        METHOD_INFO_MAP.put("div", new MethodInfo("$div",1));
        METHOD_INFO_MAP.put("mod", new MethodInfo("$percent",1));
        METHOD_INFO_MAP.put("xor", new MethodInfo("$up",1));
        METHOD_INFO_MAP.put("and", new MethodInfo("$amp",1));
        METHOD_INFO_MAP.put("or", new MethodInfo("$bar",1));
        METHOD_INFO_MAP.put("power", new MethodInfo("$times$times",1));
        METHOD_INFO_MAP.put("leftShift", new MethodInfo("$less$less",1));
        METHOD_INFO_MAP.put("rightShift", new MethodInfo("$greater$greater",1));
        METHOD_INFO_MAP.put("negative", new MethodInfo("unary_$minus",0));
        METHOD_INFO_MAP.put("positive", new MethodInfo("unary_$plus",0));
        METHOD_INFO_MAP.put("bitwiseNegate", new MethodInfo("unary_$tilde",0));
    }

    public static boolean hasScalifyAnnotation(AnnotatedNode node) {
        for (AnnotationNode annotation : (Collection<AnnotationNode>) node.getAnnotations()) {
            if (boundClassNode.equals(annotation.getClassNode())) {
                return true;
            }
        }
        return false;
    }

    public void visit(ASTNode[] nodes, SourceUnit source) {
        if (!(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new RuntimeException("Internal error: wrong types: $node.class / $parent.class");
        }
        AnnotationNode node = (AnnotationNode) nodes[0];
        AnnotatedNode parent = (AnnotatedNode) nodes[1];

        ClassNode declaringClass = parent.getDeclaringClass();
        if (parent instanceof FieldNode) {
            scalifyProperty(source, node, declaringClass, (FieldNode) parent);
        } else if (parent instanceof ClassNode) {
            scalifyClass(source, node, (ClassNode) parent);
        }
    }

    private void scalifyProperty(SourceUnit source, AnnotationNode node, ClassNode declaringClass, FieldNode field) {
        String fieldName = field.getName();
        for (PropertyNode propertyNode : (Collection<PropertyNode>) declaringClass.getProperties()) {
            if (propertyNode.getName().equals(fieldName)) {
                if (field.isStatic()) {
                    //noinspection ThrowableInstanceNeverThrown
                    source.getErrorCollector().addErrorAndContinue(
                                new SyntaxErrorMessage(new SyntaxException(
                                    "@Scalify cannot annotate a static property.",
                                    node.getLineNumber(),
                                    node.getColumnNumber()),
                                    source));
                } else {
                    createScalaAccessors(source, node, declaringClass, propertyNode);
                }
                return;
            }
        }
        //noinspection ThrowableInstanceNeverThrown
        source.getErrorCollector().addErrorAndContinue(
                new SyntaxErrorMessage(new SyntaxException(
                        "@Scalify must be on a property, not a field.  Try removing the private, protected, or public modifier.",
                        node.getLineNumber(),
                        node.getColumnNumber()),
                        source));
    }

    private void scalifyClass(SourceUnit source, AnnotationNode node, ClassNode classNode) {
        List<MethodNode> translatedMethods = new ArrayList<MethodNode>();
        for( MethodNode methodNode : classNode.getMethods()) {
           MethodInfo mi = METHOD_INFO_MAP.get(methodNode.getName());
           if( mi != null &&
               !methodNode.isPublic() ||
               methodNode.isStatic() ||
               methodNode.getReturnType().equals(ClassHelper.VOID_TYPE) ||
               methodNode.getReturnType().equals(ClassHelper.void_WRAPPER_TYPE) ||
               methodNode.getParameters().length != mi.argCount ) continue;

           ArgumentListExpression ale = mi.argCount == 0 ? ArgumentListExpression.EMPTY_ARGUMENTS :
                new ArgumentListExpression(new Expression[]{
                    new VariableExpression(methodNode.getParameters()[0].getName())});
           ExpressionStatement st =  new ExpressionStatement(
                 new MethodCallExpression(
                         VariableExpression.THIS_EXPRESSION,
                         methodNode.getName(), ale));

           translatedMethods.add( new MethodNode(mi.name,
                 methodNode.getModifiers(),
                 methodNode.getReturnType(),
                 methodNode.getParameters(),
                 methodNode.getExceptions(),
                 new ReturnStatement(st)));
        }
        for( MethodNode mn : translatedMethods ) classNode.addMethod(mn);

        for (PropertyNode propertyNode : (Collection<PropertyNode>) classNode.getProperties()) {
            FieldNode field = propertyNode.getField();
            // look to see if per-field handlers will catch this one...
            if (hasScalifyAnnotation(field)
                || field.isStatic())
            {
                // explicitly labeled properties are already handled,
                // don't transform static properties
                continue;
            }
            createScalaAccessors(source, node, classNode, propertyNode);
        }
        final ClassNode[] ifaces = classNode.getInterfaces();
        final ClassNode[] newIfaces = new ClassNode[ifaces.length + 1];
        System.arraycopy(ifaces, 0, newIfaces, 0, ifaces.length);
        newIfaces[ifaces.length] = SCALA_OBJECT_IFACE;
        classNode.setInterfaces(newIfaces);
        classNode.addMethod(
                new MethodNode(
                        "$tag",
                        ACC_PUBLIC,
                        ClassHelper.int_TYPE,
                        Parameter.EMPTY_ARRAY,
                        ClassNode.EMPTY_ARRAY,
                        new ReturnStatement(
                                new ExpressionStatement(
                                        new ConstantExpression("0")))));
        // TODO generate pickle class attribute!!!
    }


    private void createScalaAccessors(SourceUnit source, AnnotationNode node, ClassNode classNode, PropertyNode propertyNode) {
        String setterName = propertyNode.getName()+"_$eq";
        if ((propertyNode.getModifiers() & Opcodes.ACC_FINAL) == 0 &&
            classNode.getMethods(setterName).isEmpty()) {
            Statement setterBlock =  new ExpressionStatement(
                new MethodCallExpression(
                        VariableExpression.THIS_EXPRESSION,
                        "set" + MetaClassHelper.capitalize(propertyNode.getName()),
                        new ArgumentListExpression(
                                new Expression[]{
                                        new VariableExpression("value")})));

            // create method void <setter>(<type> fieldName)
            createSetterMethod(classNode, propertyNode, setterName, setterBlock);
        }
        String getterName = propertyNode.getName();
        if (classNode.getMethods(getterName).isEmpty()) {
            classNode.addMethod(
                new MethodNode(
                        getterName,
                        propertyNode.getModifiers(),
                        propertyNode.getType(),
                        Parameter.EMPTY_ARRAY,
                        ClassNode.EMPTY_ARRAY,
                        new ReturnStatement(
                                new ExpressionStatement(
                                        new MethodCallExpression(
                                                VariableExpression.THIS_EXPRESSION,
                                                "get" + MetaClassHelper.capitalize(propertyNode.getName()),
                                                ArgumentListExpression.EMPTY_ARGUMENTS)))));
        }
    }

    protected void createSetterMethod(ClassNode declaringClass, PropertyNode propertyNode, String setterName, Statement setterBlock) {
        Parameter[] setterParameterTypes = {new Parameter(propertyNode.getType(), "value")};
        MethodNode setter =
                new MethodNode(setterName, propertyNode.getModifiers(), ClassHelper.VOID_TYPE, setterParameterTypes, ClassNode.EMPTY_ARRAY, setterBlock);
        // setter.setSynthetic(true);
        // add it to the class
        declaringClass.addMethod(setter);
    }

    private static class MethodInfo {
       private String name;
       private int argCount;

       public MethodInfo(String name, int argCount) {
           super();
           this.name = name;
           this.argCount = argCount;
       }
    }
}