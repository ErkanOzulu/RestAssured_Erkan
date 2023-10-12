package com.cydeo.day12;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class MockApi {


    //https://c8f1b466-398d-43c6-a0eb-85cd45005494.mock.pstmn.io


    @Test
    public void test1() {

        given().baseUri("https://c8f1b466-398d-43c6-a0eb-85cd45005494.mock.pstmn.io")
                .accept(ContentType.JSON)
        .when()
                .get("/customer")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName",is("John")).log().all();


//     for create dammy data   https://mockaroo.com/


    }


    @Test
    public void test2() {

        given().baseUri("https://c8f1b466-398d-43c6-a0eb-85cd45005494.mock.pstmn.io")
                .accept(ContentType.JSON)
                .when()
                .get("/employees")
                .prettyPrint();


    }
}
