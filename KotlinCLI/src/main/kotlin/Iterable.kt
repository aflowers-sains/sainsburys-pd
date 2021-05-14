class Iterable {
    val strings: List<String> = listOf("1", "2", "  ", "3", "", "4")

    fun blank1(): Int {
        return strings.filter { it.isNotBlank() }.count()
    }

    fun blank2(): Int {
        return strings.asSequence().filter { it.isNotBlank() }.count()
    }
}

fun main() {
    val i = Iterable()

    println(i.blank1())
    println(i.blank2())
}
