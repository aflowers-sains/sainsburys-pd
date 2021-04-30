import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun CoroutineScope.produceNumbers() = produce<Int> {
    repeat(20) {
        send(it)
    }
}

fun main(args: Array<String>) = runBlocking {
    val producer = produceNumbers()

    reader(producer)

//    val result = AtomicLong()
//
//    println("Start :: ${System.currentTimeMillis()}")
//    val job = withTimeout(500) {
//        repeat(1_500_000) {
//            result.getAndIncrement()
//            delay(1)
//            yield()
//        }
//    }
//    println("Job configured :: ${System.currentTimeMillis()}")
//
//
//    println("Delay Done :: ${System.currentTimeMillis()}")
//    println(result.get())
//    println("Job == ${job} :: ${System.currentTimeMillis()}")
}

private suspend fun reader(producer: ReceiveChannel<Int>) {
    producer.consumeEach {
        println(it)
    }
}
