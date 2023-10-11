package com.cydeo.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanNegativeTestCase {

    @BeforeAll
    public static void init() {  // The static used is very important otherwise it works like hook class
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://3.238.70.42:8000";

    }
 /*TASK
    Given Accept type application/xml
    When user send GET request to /api/spartans/10 end point
    Then status code must be 406
    And response Content Type must be application/xml;charset=UTF-8
    */

    @DisplayName("Get request to /api/spartans/10 ")
    @Test
    public void test1() {

        Response response = given()
                                    .accept(ContentType.XML)
                            .when()
                                     .get("/api/spartans/10");

        //Then status code must be 406

        assertEquals(406, response.statusCode());


        //response Content Type
        assertEquals("application/xml;charset=UTF-8", response.contentType());

    }


}
