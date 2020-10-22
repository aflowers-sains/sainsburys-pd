package com.sainsburys.pd.reactive.controller

import com.sainsburys.pd.reactive.persistence.NameMappingRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
internal class HelloWorldController(repository: NameMappingRepository) {
    private val repository: NameMappingRepository = repository

    @GetMapping("/hello/{name}")
    fun helloWorld(@PathVariable("name") name: String): Mono<String> {
        return repository.findByName(name)!!.map { "Hello " + name + " - " + it!!.mapping }
    }

    @GetMapping("/hellos")
    fun hellosWorld(): Flux<String> {
         return repository.findAll().map { "Hello " + it!!.name + " - " + it.mapping }
    }
}
