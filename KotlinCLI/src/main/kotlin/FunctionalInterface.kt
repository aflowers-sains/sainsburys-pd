fun interface FunctionalInterface {
    fun dostuff(p: String)
}

fun createprinter() = FunctionalInterface { s -> println("sending ${s}") }

fun main() {
    val x = createprinter()

    x.dostuff("hi")
}
