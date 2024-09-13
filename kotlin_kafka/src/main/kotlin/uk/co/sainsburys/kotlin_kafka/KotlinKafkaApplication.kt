package uk.co.sainsburys.kotlin_kafka

import org.apache.kafka.common.config.TopicConfig
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import kotlin.system.exitProcess

@SpringBootApplication
class KotlinKafkaApplication(val kafkaSender: KafkaSender) : CommandLineRunner {
    override fun run(vararg args: String?) {
        while (true) {
            println("Enter message to send (X to quit):")
            val line = readLine()
            if ("X".equals(line, true)) {
                exitProcess(0)
            } else {
                println("Sending message to Kafka '${line}'")
                kafkaSender.send(line!!)
            }
        }
    }

    @Bean
    fun pdTopic() = TopicBuilder
        .name("sainsburys_topic")
        .config(TopicConfig.RETENTION_MS_CONFIG, "500")
        .build()

}

fun main(args: Array<String>) {
    runApplication<KotlinKafkaApplication>(*args)
}
