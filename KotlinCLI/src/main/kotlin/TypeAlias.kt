class TypeAlias {
}

typealias Stuff = MutableList<TypeAlias>

fun Stuff.zyzzy(): Int {
    return this.size
}

fun main() {
    val s: Stuff = mutableListOf()
    s.add(TypeAlias())
    println(s.zyzzy())
}
