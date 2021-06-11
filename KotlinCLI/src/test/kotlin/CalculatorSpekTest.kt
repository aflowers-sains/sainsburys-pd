import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals

object CalculatorSpekTest : Spek({
    Feature("Calculator adds numbers") {
        Scenario("adding numbers") {
            val calculator by memoized { Calculator() }

            var result: Int? = null

            When("Adding 2 and 3") {
                result = calculator.add(2, 3)
            }

            Then("We get 5") {
                assertEquals(result, 5)
            }
        }
    }
})
