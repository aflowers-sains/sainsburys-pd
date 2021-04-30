class UnusedParams {
    val stuff: List<MyData> = listOf(MyData("data1", 1))

    fun doStuff() {
        stuff.stream().mapToDouble { (_, data2) -> data2.toDouble() }.forEach { dbl: Double -> println(dbl) }
    }
}

fun main() {
    UnusedParams().doStuff()
}
