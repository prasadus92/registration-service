package com.gamesys.registration.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;

@RunWith (SpringJUnit4ClassRunner.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest {

    @LocalServerPort
    protected int port;

    @Before
    public void beforeTest() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testRegistrationSucceedsForValidRequest() {
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Bruce\",\n" +
                      "  \"password\" : \"Wayne123\",\n" +
                      "  \"dob\" : \"1992-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"456789032780458\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_CREATED);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsForInvalidUsername() {
        // Username with space
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Bruc e\",\n" +
                      "  \"password\" : \"Wayne123\",\n" +
                      "  \"dob\" : \"1992-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"456789032780458\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on

        // Empty string as username
        //language=JSON
        user = "{\n" +
               "  \"username\" : \"\",\n" +
               "  \"password\" : \"Wayne123\",\n" +
               "  \"dob\" : \"1992-12-13\",\n" +
               "  \"paymentCardNumber\" : \"456789032780458\"\n" +
               "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsForInvalidPassword() {
        // Password less than 4 chars
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Bruce\",\n" +
                      "  \"password\" : \"Way\",\n" +
                      "  \"dob\" : \"1992-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"456789032780458\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on

        // Password without uppercase letters
        //language=JSON
        user = "{\n" +
               "  \"username\" : \"Bruce\",\n" +
               "  \"password\" : \"wayne123\",\n" +
               "  \"dob\" : \"1992-12-13\",\n" +
               "  \"paymentCardNumber\" : \"456789032780458\"\n" +
               "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on

        // Password without any digits
        //language=JSON
        user = "{\n" +
               "  \"username\" : \"Bruce\",\n" +
               "  \"password\" : \"Wayne\",\n" +
               "  \"dob\" : \"1992-12-13\",\n" +
               "  \"paymentCardNumber\" : \"456789032780458\"\n" +
               "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsIfMinimumAgeCriteriaFailed() {
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Bruce\",\n" +
                      "  \"password\" : \"Wayne12\",\n" +
                      "  \"dob\" : \"2010-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"456789032780458\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsForInvalidDOB() {
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Bruce\",\n" +
                      "  \"password\" : \"Wayne12\",\n" +
                      "  \"dob\" : \"1992-12-13 bla\",\n" +
                      "  \"paymentCardNumber\" : \"456789032780458\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsForInvalidPaymentCardNumber() {
        // Payment Card Number with only 14 digits
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Bruce\",\n" +
                      "  \"password\" : \"Wayne123\",\n" +
                      "  \"dob\" : \"1992-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"45678903278045\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on

        // Payment Card Number with only 20 digits
        //language=JSON
        user = "{\n" +
               "  \"username\" : \"Bruce\",\n" +
               "  \"password\" : \"Wayne123\",\n" +
               "  \"dob\" : \"1992-12-13\",\n" +
               "  \"paymentCardNumber\" : \"45678903278045890875\"\n" +
               "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on

        // Empty Payment Card Number
        //language=JSON
        user = "{\n" +
               "  \"username\" : \"Bruce\",\n" +
               "  \"password\" : \"Wayne123\",\n" +
               "  \"dob\" : \"1992-12-13\",\n" +
               "  \"paymentCardNumber\" : \"\"\n" +
               "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsForAlreadyExistingUser() {
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Batman\",\n" +
                      "  \"password\" : \"Wayne123\",\n" +
                      "  \"dob\" : \"1992-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"456789032780459\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_CREATED);
        //@formatter:on

        //language=JSON
        user = "{\n" +
               "  \"username\" : \"Batman\",\n" +
               "  \"password\" : \"Wayne123\",\n" +
               "  \"dob\" : \"1992-12-13\",\n" +
               "  \"paymentCardNumber\" : \"456789032780458\"\n" +
               "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_CONFLICT);
        //@formatter:on
    }

    @Test
    public void testRegistrationFailsForBlockedIssuerIdNumber() {
        //language=JSON
        String user = "{\n" +
                      "  \"username\" : \"Catwoman\",\n" +
                      "  \"password\" : \"Wayne123\",\n" +
                      "  \"dob\" : \"1992-12-13\",\n" +
                      "  \"paymentCardNumber\" : \"123456032780459\"\n" +
                      "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post("api/v1/user/registration")
            .then()
            .statusCode(HttpStatus.SC_NOT_ACCEPTABLE);
        //@formatter:on
    }
}
