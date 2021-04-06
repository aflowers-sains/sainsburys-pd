package com.sainsburys.pd.kotlinhello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello/{name}")
    fun simpleHello(@PathVariable name: String) = run { "Hello $name" }
}
