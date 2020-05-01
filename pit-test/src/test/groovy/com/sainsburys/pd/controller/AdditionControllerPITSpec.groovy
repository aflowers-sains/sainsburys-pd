package com.sainsburys.pd.controller

import io.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given

// Integration test against a RestAssured MOCK Spring Boot application
class AdditionControllerPITSpec extends Specification {
    def setupSpec() {
        RestAssuredMockMvc.standaloneSetup(new AdditionController())
    }

    def "failure as far as PIT Mutation testing is concerned - BAD TEST"() {
        given: "two positive integers"
        def given = given().queryParam("first", 1).queryParam("second", 2)

        when: "calling the addition REST API"
        def response = given.when().get("/add")

        then: "we get the expected result of adding the two numbers"
        response.statusCode == 200
//      NO TEST FOR EXPECTED ANSWER, SO THE TEST MARKED POOR WHEN MUTATED AS SHOULD HAVE FAILED
    }
 }