package com.sainsburys.pd.kotlinhello

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class HelloRoute {
    @Bean
    fun helloRouter() = router {
        accept(MediaType.APPLICATION_JSON).nest() {
            GET("/hellorouter/{name}") { request ->
                val name: String = request.pathVariable("name")
                ServerResponse.ok().bodyValue("Hello router $name")
            }
        }
    }
}
