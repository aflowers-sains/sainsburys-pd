class ByKeyword(
    val id: String,
    private val stuff: List<String>,
    val stuff2: List<Int>
) : List<String> by stuff {
    fun dump() = onEach { println(it) }
}

fun main() {
    val bk = ByKeyword("", listOf("1", "2", "3"), listOf(1, 2, 3, 4))

    bk.dump()
    println(bk[0])
}
