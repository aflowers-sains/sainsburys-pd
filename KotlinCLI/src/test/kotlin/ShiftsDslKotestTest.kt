import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ShiftsDslKotestTest : BehaviorSpec({
    Given("A set of shifts") {
        val result = shifts {
            shift {
                id = "shift 1"
                start = "08:00"
                duration = 1800

                `get deliveries`()
            }

            shift {
                id = "shift 2"
                start = "08:00"
                duration = 1800
            }
        }

        Then("Retrieving the shifts") {
            result.size shouldBe 2
            result[0].id shouldBe "shift 1"
            result[0].deliveries.size shouldBe 1
            result[1].id shouldBe "shift 2"
            result[1].deliveries.size shouldBe 0
        }
    }
})

private fun Shift.`get deliveries`() {
    for (i in 1..10) {
        delivery {
            orderNumber = "1234567890"
            deliveryTime = "08:15"
        }
    }
}
