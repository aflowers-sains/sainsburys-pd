fun main() {
    topLevelMethod()
    anotherTopLevelMethod()
}

fun topLevelMethod() {
    val name: String = getName()
    println("Hello : $name")
}

fun getName(): String {
    return "World"
}

fun anotherTopLevelMethod() = println("${simpleNameFunction()}")

fun simpleNameFunction(): String = "Hello There"
