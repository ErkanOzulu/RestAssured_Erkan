package com.cydeo.day8;

import com.cydeo.utilities.BookItUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;


public class BookItTest {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "https://cybertek-reservation-api-qa3.herokuapp.com";

    }

    String accessToken ="Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNDAiLCJhdWQiOiJzdHVkZW50LXRlYW0tbGVhZGVyIn0.xNvdQRyrYMb3Ec5QByHwXowBo3zPK2jQQS1eJ2RYIto";

    @DisplayName("GET all campuses")
    @Test
    public void testAuth1() {

        //how to pass bearer token for bookit ? use header method to give as key value header
        given()
                .header("Authorization",accessToken)
                .and().accept(ContentType.JSON)
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .log().all();

    }

    //create BookItUtil then create a method, that accepts email and password return token Bearer +yourToken as a String
    @DisplayName("Get Token via BookItUtil")
    @Test
    public void testAuth2(){
        System.out.println("BookItUtil.yourToken() = " + BookItUtil.yourToken("sbirdbj@fc2.com","asenorval"));
    }

    }
