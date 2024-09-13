package uk.co.sainsburys.kotlin_kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaReader {
    @KafkaListener(id = "sainsburys", topics = ["sainsburys_topic"])
    fun listen(message: String) {
        println("*** Read a message from Kafka *** - '${message}'")
    }
}
