class Lazy(val name: String) {
    val lower: String = dolower()

    val lower2: String
        get() {
            println("calc on access")
            return name.toLowerCase()
        }

    val upper: String by lazy {
        println("lazy upper")
        name.toUpperCase()
    }

    fun dolower(): String {
        println("lower not lazy")
        return name.toLowerCase()
    }
}

fun main() {
    val l = Lazy("Name")
    println(l.name)
    println(l.upper)
    println(l.lower)
    println(l.lower2)
}
