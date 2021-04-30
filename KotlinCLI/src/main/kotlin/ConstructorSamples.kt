class ConstructorSamples(
    val first: String = "first",
    val second: Int = 2
) {
    override fun toString(): String {
        return "ConstructorSamples(first='$first', second=$second)"
    }
}

fun interestingstuff() {
    val first = ConstructorSamples("two")
    val second = ConstructorSamples(second = 3)

    println(first)
    println(second)
}

fun main() {
    interestingstuff()
}
