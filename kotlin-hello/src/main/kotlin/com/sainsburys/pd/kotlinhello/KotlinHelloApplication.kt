package com.sainsburys.pd.kotlinhello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinHelloApplication

fun main(args: Array<String>) {
    runApplication<KotlinHelloApplication>(*args)
}
