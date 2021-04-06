package com.sainsburys.pd.fcrskotlin.orderinformation.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class OrderInformationService {
    fun orderInformation(orders: Flux<String>): String {
        return "order information"
    }
}
