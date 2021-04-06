package com.sainsburys.pd.kotlinhello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import java.nio.file.Files
import java.nio.file.Path

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinHelloApplicationTests(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun initialise() {
        Files.deleteIfExists(Path.of("./data/testdb.mv.db"))
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun `Simple hello REST controller answers requests`() {
        val entity = restTemplate.getForEntity<String>("/hello/kotlin")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Hello kotlin")
    }

    @Test
    fun `Simple hello REST router answers requests`() {
        val entity = restTemplate.getForEntity<String>("/hellorouter/kotlin")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Hello router kotlin")
    }

    @Test
    fun `Simple waiting hello REST router answers requests`() {
        val start = System.currentTimeMillis()
        val entity = restTemplate.getForEntity<String>("/waitingrouter/waited")
        val end = System.currentTimeMillis()

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Waiting router waited")
        assertThat(end - start).isGreaterThan(9000)
    }

    @Test
    fun `Simple database backed hello REST router answers requests for something not in the database with a 404`() {
        val entity = restTemplate.getForEntity<String>("/dbrouter/unknown")

        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Simple database backed hello REST router answers requests for something in the database`() {
        val entity = restTemplate.getForEntity<String>("/dbrouter/fred")

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("DB router freddo")
    }
}
