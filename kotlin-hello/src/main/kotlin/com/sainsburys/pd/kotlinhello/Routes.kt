package com.sainsburys.pd.kotlinhello

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

@Configuration
class Routes(val peopleRepository: PeopleRepository) {
    @Bean
    fun apiRoutes() = router {
        accept(MediaType.APPLICATION_JSON).nest() {
            GET("/hellorouter/{name}") { request ->
                val name: String = request.pathVariable("name")
                ServerResponse.ok().bodyValue("Hello router $name")
            }

            GET("/waitingrouter/{name}") { request ->
                val name: String = request.pathVariable("name")
                ServerResponse.ok().bodyValue("Waiting router $name").delayElement(Duration.ofSeconds(10))
            }

            GET("/dbrouter/{name}") { request ->
                val name: String = request.pathVariable("name")
                peopleRepository.findByName(name)
                    .next()
                    .map { p -> "DB router ${p.greeting}" }
                    .flatMap { msg -> ServerResponse.ok().bodyValue(msg) }
                    .switchIfEmpty { ServerResponse.notFound().build() }
            }

        }
    }
}
