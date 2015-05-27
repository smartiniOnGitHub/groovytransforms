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

import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.matchers.ShouldMatchers
import org.junit.Assert._
import org.junit.Test

class ScalifyTests extends AssertionsForJUnit with ShouldMatchers {
    val subject1 = new GroovySubject("A")
    val subject2 = new GroovySubject("B")

    @Test def plus() {
        subject1 + subject2
        subject1.witness should be ("A + B")
    }

    @Test def minus() {
        subject1 - subject2
        subject1.witness should be ("A - B")
    }

    @Test def multiply() {
        subject1 * subject2
        subject1.witness should be ("A * B")
    }

    @Test def div() {
        subject1 / subject2
        subject1.witness should be ("A / B")
    }

    @Test def mod() {
        subject1 % subject2
        subject1.witness should be ("A % B")
    }

    @Test def xor() {
        subject1 ^ subject2
        subject1.witness should be ("A ^ B")
    }

    @Test def and() {
        subject1 & subject2
        subject1.witness should be ("A & B")
    }

    @Test def or() {
        subject1 | subject2
        subject1.witness should be ("A | B")
    }

    @Test def power() {
        subject1 ** subject2
        subject1.witness should be ("A ** B")
    }

    @Test def leftShift() {
        subject1 << subject2
        subject1.witness should be ("A << B")
    }

    @Test def rightShift() {
        subject1 >> subject2
        subject1.witness should be ("A >> B")
    }

    @Test def negative() {
        -subject1
        subject1.witness should be ("-A")
    }

    @Test def positive() {
        +subject1
        subject1.witness should be ("+A")
    }

    @Test def bitwiseNegate() {
        ~subject1
        subject1.witness should be ("~A")
    }

}
