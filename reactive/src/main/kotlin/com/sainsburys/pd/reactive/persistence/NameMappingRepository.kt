package com.sainsburys.pd.reactive.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface NameMappingRepository : ReactiveCrudRepository<Mapping?, String?> {
    fun findByName(name: String?): Mono<Mapping?>?
}
