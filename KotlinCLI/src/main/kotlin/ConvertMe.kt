class ConvertMe(val state1: String, val state2: Int) {
//    private val state1: String
//    private val state2: Int
//
//    constructor(state1: String, state2: Int) {
//        this.state1 = state1
//        this.state2 = state2
//    }

    constructor(s1: String) : this(s1, 1) {
    }
    
    companion object {
        fun doStuff() {
            println("do stuff")
        }

        fun doOtherStuff(): Int {
            println("do other stuff")
            return 1
        }
    }
}
