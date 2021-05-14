class FunctionResult {
}

fun createFunction(): (String) -> Unit = {
    println(it)
}

fun createFunctionReturningString(): (String) -> String = {
    println(it)
    "printed ${it}"
}

fun main() {
    val func: (String) -> Unit = createFunction()
    val func2: (String) -> String = createFunctionReturningString()

    func("a string")
    println("func2 = " + func2("stuff"))
}
