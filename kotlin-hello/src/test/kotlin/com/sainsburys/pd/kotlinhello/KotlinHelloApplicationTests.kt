package com.sainsburys.pd.kotlinhello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinHelloApplicationTests(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun `Simple hello REST controller answers requests`() {
        val entity = restTemplate.getForEntity<String>("/hello/kotlin")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Hello kotlin")
    }

}
