package com.cydeo.day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class CBTrainingApiWithJsonPath {



    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "https://api.training.cydeo.com";

    }

    @DisplayName("GET Request to individual student")
    @Test
    public void test1(){
        //send a get request to student id 45 as a path parameter and accept header application/json
        //verify status code=200 /content type=application/json;charset=UTF-8 /transfer-encoding = chunked
        //verify Date header exists
        //assert that
            /*
                firstName Andres
                batch 11
                section N/A
                emailAddress jroblettb@apple.com
                companyName Fivespan
                state Texas
                zipCode 62892
                using JsonPath
             */

        Response response=given().accept(ContentType.JSON)
                .and().pathParam("id",45)
        .get("/student/{id}");

        assertEquals(200,response.statusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());
        assertEquals("chunked",response.header("transfer-encoding"));
        assertTrue(response.headers().hasHeaderWithName("date"));

        JsonPath jsonPath=response.jsonPath();
       assertEquals("Andres",jsonPath.getString("students[0].firstName"));
       assertEquals("11",jsonPath.getString("students[0].batch"));

       assertEquals("N/A",jsonPath.getString("students[0].section"));
       assertEquals("jroblettb@apple.com",jsonPath.getString("students[0].contact.emailAddress"));
       assertEquals("Texas",jsonPath.getString("students[0].company.address.state"));
       assertEquals("Fivespan",jsonPath.getString("students[0].company.companyName"));
       assertEquals("62892",jsonPath.getString("students[0].company.address.zipCode"));








    }
}
