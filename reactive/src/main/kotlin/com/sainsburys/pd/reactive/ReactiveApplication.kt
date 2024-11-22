package com.sainsburys.pd.reactive

import com.sainsburys.pd.reactive.persistence.Mapping
import com.sainsburys.pd.reactive.persistence.NameMappingRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux

@SpringBootApplication
open class ReactiveApplication {
    @Bean
    open fun init(repository: NameMappingRepository, dbClient: DatabaseClient) = ApplicationRunner {
        try {
            val initDb = dbClient.sql("CREATE TABLE MAPPING(name varchar(50), mapping varchar(50));")

            val saveAll = Flux.just(Mapping("Andy", "dev"), Mapping("andy", "dev")).flatMap(repository::save)

            initDb.then().thenMany(saveAll).thenMany(repository.findAll()).subscribe(::println)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ReactiveApplication::class.java, *args)
        }
    }
}
