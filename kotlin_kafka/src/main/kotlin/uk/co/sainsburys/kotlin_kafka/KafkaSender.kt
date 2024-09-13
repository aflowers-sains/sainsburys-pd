package uk.co.sainsburys.kotlin_kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaSender(val kafkaTemplate: KafkaTemplate<String?, String?>) {
    fun send(message: String) {
        kafkaTemplate.send("sainsburys_topic", "message", message)
    }
}
