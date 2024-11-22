package com.sainsburys.pd.controller;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.RestAssured;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(DataProviderRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AdditionControllerTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @DataProvider
    public static Object[][] validNumbersProvider() {
        return new Integer[][] {
                {2, 5, 7},
                {-2, 5, 3},
                {2, -5, -3},
                {0, 0, 0}
        };
    }

    @Test
    @UseDataProvider("validNumbersProvider")
    public void canAddTwoNumbersTogether(int first, int second, int expectedAnswer) {
        given()
            .queryParam("first", first)
            .queryParam("second", second)
        .when()
            .get("/add")
        .then()
            .assertThat()
                .statusCode(200)
                .body("answer", is(expectedAnswer));
    }


    @DataProvider
    public static Object[][] notNumbersProvider() {
        return new Object[][] {
                {"one", 2},
                {1, "two"},
                {"one", "two"}
        };
    }

    @Test
    @UseDataProvider("notNumbersProvider")
    public void expectAnError400IfParameterValuesAreNotNumbers(Object first, Object second) {
        given()
                .queryParam("first", first)
                .queryParam("second", second)
        .when()
                .get("/add")
        .then()
                .assertThat().statusCode(400);
    }

    @DataProvider
    public static Object[][] missingQueryParamsProvider() {
        return new String[][] {
                { "firstWRONG", "second" },
                { "first", "secondWRONG" },
                { "firstWRONG", "secondWRONG" },
        };
    }

    @Test
    @UseDataProvider("missingQueryParamsProvider")
    public void expectAnError400IfParametersNotProvided(String firstParam, String secondParam) {
        given()
                .queryParam(firstParam, 1)
                .queryParam(secondParam, 2)
        .when()
                .get("/add")
        .then()
                .assertThat().statusCode(400);
    }
}