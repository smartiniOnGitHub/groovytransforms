import groovyx.transform.Scalify

@Scalify
class GroovySubject {
  String name
  String witness

  GroovySubject(String name) {
    this.name = name
  }

  GroovySubject plus(GroovySubject other)       { witness = "$name + ${other.name}"; this }
  GroovySubject minus(GroovySubject other)      { witness = "$name - ${other.name}"; this }
  GroovySubject multiply(GroovySubject other)   { witness = "$name * ${other.name}"; this }
  GroovySubject div(GroovySubject other)        { witness = "$name / ${other.name}"; this }
  GroovySubject mod(GroovySubject other)        { witness = "$name % ${other.name}"; this }
  GroovySubject xor(GroovySubject other)        { witness = "$name ^ ${other.name}"; this }
  GroovySubject and(GroovySubject other)        { witness = "$name & ${other.name}"; this }
  GroovySubject or(GroovySubject other)         { witness = "$name | ${other.name}"; this }
  GroovySubject power(GroovySubject other)      { witness = "$name ** ${other.name}"; this }
  GroovySubject leftShift(GroovySubject other)  { witness = "$name << ${other.name}"; this }
  GroovySubject rightShift(GroovySubject other) { witness = "$name >> ${other.name}"; this }
  GroovySubject negative()      { witness = "-"+name; this }
  GroovySubject positive()      { witness = "+"+name; this }
  GroovySubject bitwiseNegate() { witness = "~"+name; this }
}
