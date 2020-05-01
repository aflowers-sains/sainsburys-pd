package com.sainsburys.pd.controller

import spock.lang.Specification

import io.restassured.module.mockmvc.*
import spock.lang.Unroll

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*

// Integration test against a RestAssured MOCK Spring Boot application
class AdditionControllerSpec extends Specification {
    def setupSpec() {
        RestAssuredMockMvc.standaloneSetup(new AdditionController())
    }

    @Unroll("Adding #first to #second and expecting #answer")
    def "can add two numbers together"() {
        given: "two positive integers"
        def given = given().queryParam("first", first).queryParam("second", second)

        when: "calling the addition REST API"
        def response = given.when().get("/add")

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

    def "expect an error 400 if parameters not provided"() {
        given: "two positive integers"
        def given = given().queryParam(firstParam, 1).queryParam(secondParam, 2)

        when: "calling the addition REST API"
        def response = given.when().get("/add")

        then: "bad param status returned"
        response.statusCode == 400

        where:

        firstParam   | secondParam
        "firstWRONG" | "second"
        "first"      | "secondWRONG"
    }

    def "expect an error 400 if parameter values are not numbers"() {
        given: "two positive integers"
        def given = given().queryParam("first", first).queryParam("second", second)

        when: "calling the addition REST API"
        def response = given.when().get("/add")

        then: "bad param status returned"
        response.statusCode == 400

        where:

        first   | second
        "one"   | 2
        1       | "two"
        "one"   | "two"
    }
}