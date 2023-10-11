package com.cydeo.day4;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanWithJsonPath extends SpartanTestBase {
    /*
        Given accept type is json
        And path param id is 10
        When user sends a get request to "api/spartans/{id}"
        Then status code is 200
        And content-type is "application/json"
        And response payload values match the following:
             id is 10,
             name is "Lorenza",
             gender is "Female",
             phone is 3312820936
      */
    @DisplayName("GET one spartan with JsonPath")
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("api/spartans/{id}");


        //Then status code is 200
        assertEquals(200, response.statusCode());

        //And content-type is "application/json"
        assertEquals("application/json", response.contentType());


        //print name with path method
        System.out.println(response.path("name").toString());

        //And response payload values match the following:

        //assigning response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        //id is 10,
        int id = jsonPath.getInt("id");


        //name is "Lorenza",
        String name = jsonPath.getString("name");


        //gender is "Female",
        String gender = jsonPath.getString("gender");

        //phone is 3312820936
        long phone = jsonPath.getLong("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);



    }

}
