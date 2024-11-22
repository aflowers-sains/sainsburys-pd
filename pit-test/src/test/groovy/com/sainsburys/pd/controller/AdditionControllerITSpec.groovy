package com.sainsburys.pd.controller

import io.restassured.RestAssured
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification
import spock.lang.Unroll

import static io.restassured.RestAssured.*
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

// Integration test against a running Spring Boot application
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AdditionControllerITSpec extends Specification {
    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate testRestTemplate

    def setup() {
        RestAssured.port = port
    }

    @Unroll("Adding #first to #second and expecting #answer")
    def "can add two numbers together"() {
        given: "two integers"
        def restAssuredGiven = given().queryParam("first", first).queryParam("second", second)

        when: "calling the addition REST API"
        def response = restAssuredGiven.when().get("/add")

        then: "we get the expected result of adding the two numbers"
        response.statusCode == 200
        response.jsonPath().getInt("answer") == answer

        where:

        first | second | answer
        2     | 5      | 7
        -2    | 5      | 3
        2     | -5     | -3
        0     | 0      | 0
    }

    @Unroll("Adding #first to #second and expecting #answer using TestRestTemplate")
    def "can add two numbers together using TestRestTemplate"() {
        given: "two integers"

        when: "calling the addition REST API"
        def response = testRestTemplate.getForEntity("/add?first={first}&second={second}", String.class, first, second)

        then: "we get the expected result of adding the two numbers"
        response.statusCodeValue == 200
        JSONAssert.assertEquals("{\"answer\":${answer}}", response.body, false);

        where:

        first | second | answer
        2     | 5      | 7
        -2    | 5      | 3
        2     | -5     | -3
        0     | 0      | 0
    }

    @Unroll("Trying query params #firstParam and #secondParam")
    def "expect an error 400 if parameters not provided"() {
        given: "two positive integers"
        def restAssuredGiven = given().queryParam(firstParam, 1).queryParam(secondParam, 2)

        when: "calling the addition REST API"
        def response = restAssuredGiven.when().get("/add")

        then: "bad param status returned"
        response.statusCode == 400

        where:

        firstParam   | secondParam
        "firstWRONG" | "second"
        "first"      | "secondWRONG"
    }

    @Unroll("Trying invalid query params #first and #second")
    def "expect an error 400 if parameter values are not numbers"() {
        given: "two positive integers"
        def restAssuredGiven = given().queryParam("first", first).queryParam("second", second)

        when: "calling the addition REST API"
        def response = restAssuredGiven.when().get("/add")

        then: "bad param status returned"
        response.statusCode == 400

        where:

        first   | second
        "one"   | 2
        1       | "two"
        "one"   | "two"
    }
}