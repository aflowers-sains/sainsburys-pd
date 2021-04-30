class UnusedParams {
    val stuff: List<MyData> = listOf(MyData("data1", 1))

    fun doStuff() {
        stuff.stream().mapToDouble { (_, data2) -> data2.toDouble() }
            .forEach { dbl: Double -> println(dbl.toMoneyString()) }
    }

    private fun Double.toMoneyString() = this.formattedAs("%#.2f")

    private fun Double.formattedAs(format: String) = String.format(format, this)
}

fun main() {
    UnusedParams().doStuff()
}
