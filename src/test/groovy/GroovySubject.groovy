/*
 * Copyright 2009-2015 the original author or authors.
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
