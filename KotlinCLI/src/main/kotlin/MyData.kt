data class MyData(val data1: String, val data2: Int) {

    val combined: String get() = "Data::$data1 && Data::$data2"

    override fun toString(): String {
        return "MyData(data1='$data1', data2=$data2)"
    }
}


fun main() {
    val md = MyData("d1", 1)

    println("combined = ${md.combined}")
}
