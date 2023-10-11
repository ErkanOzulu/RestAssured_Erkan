package com.cydeo.day4;

import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSApiTestWithPath extends HRTestBase {



    @DisplayName("GET request to /countries with Query Param")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                           .when()
                .get("/countries").prettyPeek();

        assertEquals(200, response.statusCode());
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        //print hasMore
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        //print first CountryID
        String firstCountryID=response.path("items[0].country_id");

        System.out.println("firstCountryID = " + firstCountryID);

        //print second country name

        String secondCountryName = response.path("items[1].country_name");
        System.out.println("secondCountryName = " + secondCountryName);

        //print "http://52.207.61.129:1000/ords/hr/countries/CA"

       String thirdHref =    response.path("items[2].links[0].href");
        System.out.println("thirdHref = " + thirdHref);


        //get me all country names

        List<String> countryNames = response.path("items.country_name");
        System.out.println("countryNames = " + countryNames);

        //assert that all regions ids are equal to 2

        List<Integer> regionIDs= response.path("items.region_id");

        for (Integer each : regionIDs) {
            System.out.println("region_id"+each);
            assertEquals(2,each);
        }



    }
    @DisplayName("GET request to /employees with Query Param")
    @Test
    public void test2(){

        Response response=given().accept(ContentType.JSON)
                .and().queryParam("q","{\"job_id\": \"IT_PROG\"}")
                .when().get("/employees").prettyPeek();


        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.header("Content-Type"));

        //make sure we have only IT_PROG as a job_id




       List<String>allJobIDs= response.path("items.job_id");

        for (String jobID : allJobIDs) {

            System.out.println("jobID = " + jobID);

            assertEquals("IT_PROG",jobID);

        }
        //HW
        //print name of each IT_PROGs

        List<String>allFirstNamesOfIT_Prog=   response.path("items.first_name");
        for (String each : allFirstNamesOfIT_Prog) {
            System.out.println("First names of IT_Programmers = "+each);
        }

    }





    }
