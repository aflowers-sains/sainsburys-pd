@DslMarker
annotation class ShiftsDslMarker

@ShiftsDslMarker
class Delivery {
    lateinit var orderNumber: String
    lateinit var deliveryTime: String
}

@ShiftsDslMarker
class Shift {
    lateinit var id: String
    lateinit var start: String
    var duration: Int = 0

    val deliveries: MutableList<Delivery> = mutableListOf()

    fun delivery(init: Delivery.() -> Unit) {
        val delivery = Delivery()
        delivery.init()
        deliveries.add(delivery)
    }
}

@ShiftsDslMarker
class Shifts {
    private var shifts: MutableList<Shift> = mutableListOf()

    fun shift(init: Shift.() -> Unit) {
        val shift = Shift()
        shift.init()
        shifts.add(shift)
    }

    fun build(): List<Shift> {
        return shifts
    }
}

fun shifts(init: Shifts.() -> Unit): List<Shift> {
    val shifts = Shifts()
    shifts.init()
    return shifts.build()
}
