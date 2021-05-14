sealed class SealedClass {
    abstract fun dostuff()
}

fun SealedClass.dootherstuff() = when (this) {
    is dostuff1 -> {
        println("do other stuff for dostuff 1")
    }
    is dostuff2 -> {
        println("do other stuff for dostuff 2")
    }
//    else -> println("something else")
}

class dostuff1(val state: String) : SealedClass() {
    override fun dostuff() {
        println("do ${state}")
    }
}

class dostuff2(val state: Int) : SealedClass() {
    override fun dostuff() {
        println("do ${state}")
    }
}

//class dostuff3(val state: Int) : SealedClass() {
//    override fun dostuff() {
//        println("do ${state}")
//    }
//}

fun main() {
    val d1 = dostuff1("str")
    val d2 = dostuff2(2)

    d1.dostuff()
    d2.dostuff()
    d1.dootherstuff()
    d2.dootherstuff()
}
