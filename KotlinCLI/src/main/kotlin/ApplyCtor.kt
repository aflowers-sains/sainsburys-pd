class ApplyCtor {
    val strings: MutableList<String> = mutableListOf()

    fun add(s: String) = strings.add(s)
}

fun main() {
    val stuff = ApplyCtor().apply {
        add("1")
        add("2")
    }

    println(stuff.strings)
}
