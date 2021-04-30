object anObjectType {
    fun doSomeStuff(data: Int) {
        println("doing some stuff in a static context")
        println("false == ${couldbenull(false)?.dootherstuff()?.data1}")
        println("true == ${couldbenull(true)?.dootherstuff()?.data1}")
        println("true == ${couldbenull(true)!!.dootherstuff()!!.data1}")
        println("false == ${couldbenull(false)!!.dootherstuff()!!.data1}")
    }

    private fun couldbenull(well: Boolean): MyData? {
        return when {
            well == true -> MyData("1", 1)
            well == false -> null
            else -> null
        }
//        if (well) {
//            return MyData("1", 1)
//        } else {
//            return null
//        }
    }

    private fun MyData.dootherstuff(): MyData {
        println("extended mydata ${data1}")
        return this
    }
}
