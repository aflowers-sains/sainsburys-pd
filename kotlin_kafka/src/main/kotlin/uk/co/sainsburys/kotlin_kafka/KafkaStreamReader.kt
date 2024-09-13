package uk.co.sainsburys.kotlin_kafka

import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer
import java.util.function.Function


@Configuration
class KafkaStreamReader {
    @Bean
    fun logger() = Consumer { input: KStream<String, String> ->
        input.foreach { key: String, value: String -> println("Key: $key Value: $value") }
    }

    @Bean
    fun uppercase() = Function<KStream<String, String>, KStream<String, String>> { input: KStream<String, String> ->
        input
            .flatMapValues { value -> listOf<String>(value.uppercase()) }
            .peek { key, value -> println("Uppercase Peek Key: $key Value: $value") }
    }

    @Bean
    fun wordcount() = Function<KStream<String, String>, KStream<String, Int>> { input: KStream<String, String> ->
        input
            .peek { key, value -> println("Word count Peek Key: $key Value: $value") }
            .mapValues { value -> value.split(" ").size }
    }

    @Bean
    fun loggerOfUppercase() = Consumer { input: KStream<String, String> ->
        input.foreach { key: String, value: String -> println("logger:: Uppercase Key: $key Value: $value") }
    }

    @Bean
    fun loggerOfWordcount() = Consumer { input: KStream<String, Int> ->
        input.foreach { key: String, value: Int -> println("logger:: Wordcount Key: $key Value: $value") }
    }
}
