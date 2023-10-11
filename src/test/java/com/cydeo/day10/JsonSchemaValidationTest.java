package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class JsonSchemaValidationTest  extends SpartanAuthTestBase {


    @DisplayName("GET request to verify one spartan against to schema")
    @Test
    public void schemaValidation(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id",15)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"))
                .log().all();


    }


    @DisplayName("GET request to all spartans and verify schema")
    @Test
    public void allSpartanSchemaTest(){

        given()
                .accept(ContentType.JSON)
                .auth().basic("admin","admin")
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(200)
                //what if you have your .json file not under resources following way -->
                 .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/AllSpartansSchema.json")));

    }

    //homework
    //put your post json schema under day10
    //post one spartan using dynamic input(name,gender,phone)
    //verify your post response matching with json schema,,



    @DisplayName("Post new spartan and verify schema under same folder" )
    @Test
    public void postSpartanValidation(){

        Map<String,Object>spartan=new HashMap<>();
        spartan.put("name","Kerim");
        spartan.put("gender","Male");
        spartan.put("phone",1231312323L);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin")
                .body(spartan)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/SpartanPostJsonSchema.json"))).log().all();



    }



}
