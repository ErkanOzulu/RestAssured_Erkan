package com.cydeo.day5;

import com.cydeo.utilities.DBUtils;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanAPIvsDB extends SpartanTestBase {

    @DisplayName("Get one spartan from api and database")
    @Test
    public void testDB1(){


        //get id, name, gender, phone from database
        //get same information from api
        //compare
        
        String query="select SPARTAN_ID,NAME,GENDER,PHONE\n" +
                "from SPARTANS\n" +
                "where SPARTAN_ID=15";
        //save data inside the map

        Map<String, Object> dbMap = DBUtils.getRowMap(query);
        System.out.println(dbMap);

        //2.get info from api

//        Response response = given().accept(ContentType.JSON)
//                .pathParam("id", 15)
//                .when()
//                .get("/api/spartans/{id}")
//                .then()
//                .statusCode(200)
//                .and().contentType("application/json")
//                .extract().response();
//
//        Map <String,Object>apiMap = response.as(Map.class);

        System.out.println("---same with above---");


        Map <String,Object> apiMap = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .and().contentType("application/json")
                .extract().response().as(Map.class);


        System.out.println(apiMap);


        //3.compare database vs api ---> we assume expected result is db

        assertThat(apiMap.get("id").toString(),is(dbMap.get("SPARTAN_ID").toString()));
        assertThat(apiMap.get("name").toString(),is(dbMap.get("NAME").toString()));
        assertThat(apiMap.get("gender").toString(),is(dbMap.get("GENDER").toString()));
        assertThat(apiMap.get("phone").toString(),is(dbMap.get("PHONE").toString()));

    }

}
