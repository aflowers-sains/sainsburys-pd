package com.sainsburys.pd.fcrskotlin.orderinformation.routes

import com.sainsburys.pd.fcrskotlin.orderinformation.service.OrderInformationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class OrderRoutes {
    @Bean
    fun routes(orderInformationService: OrderInformationService) = router {
        "/api".nest {
            POST("/orderInformation") {
                return@POST ServerResponse.ok().bodyValue(orderInformationService.orderInformation(it.bodyToFlux(String::class.java)))
            }
        }
    }
}
