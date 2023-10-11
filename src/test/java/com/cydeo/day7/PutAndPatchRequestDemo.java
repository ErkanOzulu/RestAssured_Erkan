package com.cydeo.day7;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
public class PutAndPatchRequestDemo extends SpartanTestBase {
    @DisplayName("PUT request to one spartan for update with Map")
    @Test
    public void PUTRequest() {
        //just like post request we have different options to send body, we will go with map

        Map<String, Object>putRequestMap=new LinkedHashMap<>();
        putRequestMap.put("name","MustafaSpartan");
        putRequestMap.put("gender", "Male");
        putRequestMap.put("phone", 12334523411L);

        Response responsePost = given().and().contentType(ContentType.JSON)//hey api I am sending JSON body
                .body(putRequestMap).log().body()
                .and().pathParam("id", 110)
                .when().put("/api/spartans/{id}")
                .then()
                .statusCode(204).extract().response();


        //HW
        //send a GET request after update, make sure updated field changed, or the new info matching
        //with requestBody that we send


        given().accept(ContentType.JSON)
                .and().pathParam("id",110)
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .body("name",is(putRequestMap.get("name")))
                .body("gender",is("Male"))
                .body("phone",is(putRequestMap.get("phone")));



         }
    @DisplayName("PATCH request to one spartan for partial update with Map")
    @Test
    public void PATCHRequest(){
        //just like post request we have different options to send body, we will go with map
        Map<String,Object> putRequestMap = new LinkedHashMap<>();
        putRequestMap.put("phone",8811111111L);
        putRequestMap.put("name","Peter");

        given().contentType(ContentType.JSON) //hey api I am sending JSON body
                .body(putRequestMap).log().body()
                .and().pathParam("id",100)
                .when().patch("/api/spartans/{id}")
                .then()
                .statusCode(204);


    }

    @DisplayName("DELETE one spartan")
    @Test
    public void deleteSpartan(){
        int idToDelete= 110;

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", idToDelete)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204).extract().response();

        //send a get request after you delete make sure you are getting 404


        given().pathParam("id",idToDelete)
                        .when().get("/api/spartans/{id}")
                        .then().statusCode(404);



    }



}
