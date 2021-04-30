fun main() {
    topLevelMethod()
}

fun topLevelMethod() {
    val name: String = getName()
    println("Hello : $name")
}

fun getName(): String {
    return "World"
}
