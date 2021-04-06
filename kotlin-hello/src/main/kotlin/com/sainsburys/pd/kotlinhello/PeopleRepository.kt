package com.sainsburys.pd.kotlinhello

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PeopleRepository : CrudRepository<People, Long> {
    fun findByName(name: String): Flux<People>
}
