import java.io.File
import javax.script.ScriptEngineManager

fun main(args: Array<String>) {
    val engine = ScriptEngineManager().getEngineByExtension("kts")!!

    if(args.size > 0) {
        engine.eval(File(args[0]).bufferedReader())
    } else {
        val orders: Orders = engine.eval(File("./sample.dsl").bufferedReader()) as Orders
        orders.orders().forEach { println(it) }
        orders.process()
    }
}

@DslMarker
annotation class OrdersDslMarker

//@OrdersDslMarker
class Driver {
    lateinit var name: String

    fun process() {
        println("Processing driver with name $name")
    }

    override fun toString(): String {
        return "Driver(name='$name')"
    }
}

//@OrdersDslMarker
class Order {
    lateinit var id: String
    lateinit var start: String
    var duration: Int = 0

    val drivers: MutableList<Driver> = mutableListOf()

    val starts = Starts()
    inner class Starts {
        infix fun at(whenStarts: String) {
            start = whenStarts
        }
    }

    fun driver(init: Driver.() -> Unit) {
        val driver = Driver()
        driver.init()
        drivers.add(driver)
    }

    fun process() {
        println("Saving Order $id to database, starts at $start for duration $duration")
        drivers.forEach {
            it.process()
        }
    }

    override fun toString(): String {
        return "Order(id='$id', start='$start', duration=$duration, drivers=$drivers)"
    }
}

@OrdersDslMarker
class Orders {
    private val orders: MutableList<Order> = mutableListOf()

    fun order(init: Order.() -> Unit) {
        val order = Order()
        order.init()
        orders.add(order)
    }

    fun orders(): List<Order> {
        return buildList<Order> { addAll(orders) }
    }

    fun process() {
        orders.forEach {
            it.process()
        }
    }
}

fun orders(init: Orders.() -> Unit): Orders {
    return Orders().apply(init)
}
