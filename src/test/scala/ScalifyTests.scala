import org.scalatest.testng.TestNGSuite
import org.scalatest.matchers.ShouldMatchers
import org.testng.annotations._

class ScalifyTests extends TestNGSuite with ShouldMatchers {
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