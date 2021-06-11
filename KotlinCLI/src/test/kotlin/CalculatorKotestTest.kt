import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CalculatorKotestTest : BehaviorSpec({
    Given("A calculator") {
        val calculator = Calculator()

        When("Adding 2 numbers") {
            val result = calculator.add(2, 3)

            Then("Result should be 5") {
                result shouldBe 5
            }
        }
    }
})
