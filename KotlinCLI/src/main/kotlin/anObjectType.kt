object anObjectType {
    fun doSomeStuff(data: Int) {
        println("doing some stuff in a static context")
        println("false == ${couldbenull(false)?.data1}")
        println("true == ${couldbenull(true)?.data1}")
        println("true == ${couldbenull(true)!!.data1}")
        println("false == ${couldbenull(false)!!.data1}")
    }

    private fun couldbenull(well: Boolean): MyData? {
        if (well) {
            return MyData("1", 1)
        } else {
            return null
        }
    }
}
