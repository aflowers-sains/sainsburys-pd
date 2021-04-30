fun main() {
    topLevelMethod()
    anotherTopLevelMethod()
    anotherTopLevelMethodWithParam(::simpleNameFunctionAsParam)
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

fun anotherTopLevelMethodWithParam(func: () -> String) = println("${func()}")

fun simpleNameFunctionAsParam(): String = "Hello There from a function as a param"
