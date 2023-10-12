package com.cydeo.day10;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class SSLTest {


    @Test
    public void test1() {

        given().
                relaxedHTTPSValidation(). //even if it doesn't have valid certificate I want to send request

                when().get("https://untrusted-root.badssl.com/")
                .prettyPrint();
    }

    @Test
    public void keyStore(){

        given()
                .keyStore("pathtofile","password")
                .when().get("apiurl");

    }


}