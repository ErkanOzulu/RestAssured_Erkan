package com.cydeo.day4;

import com.cydeo.utilities.HRTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class ORDSApiWithJsonPath extends HRTestBase {
/*
    There is a lot of good information about Groovy GPath in REST Assured in below site
    https://www.james-willett.com/rest-assured-gpath-json/


 */
    @DisplayName("GET request to Countries")
    @Test
    public void test1() {

        Response response = get("/countries");

        //to use jsonpath we assign response to JsonPath
        //get the second country name with JsonPath
        JsonPath jsonPath = response.jsonPath();

        String secondCountryName = jsonPath.getString("items[1].country_name");
        System.out.println("secondCountryName = " + secondCountryName);

        //get all country ids
        //items.country_id

        List<String> allCountryIds = jsonPath.getList("items.country_id");
        System.out.println(allCountryIds);

        //get all country names where their region id is equal to 2

        List<String> countryNameWithRegionId2 = jsonPath.getList("items.findAll {it.region_id==2}.country_name");
        System.out.println(countryNameWithRegionId2);
    }


    @DisplayName("GET requesto /employees with query param")
    @Test
    public void test2() {
        //we added limit query param to get 107 employees

        Response response = given().queryParam("limit", 107)
                .when().get("/employees");

        JsonPath jsonPath = response.jsonPath();
//response.prettyPrint();
        //jsonPath.prettyPrint();

        //get me all email of employees who is working as IT_PROG
        List<String> emailITProgs = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println(emailITProgs);

        //get me all firstName of employees who is working as IT_PROG
        List<String> nameITProgs = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.first_name");
        System.out.println(nameITProgs);



        //get me first name of employees who is making more than 10000
        List<String> empNames= jsonPath.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println(empNames);

        //get the max salary first_name

        String kingFirstName =  jsonPath.getString("items.max {it.salary}.first_name");
        String kingNameWithPathMethod =response.path("items.max {it.salary}.first_name");
        System.out.println("kingFirstName = " + kingFirstName);
        System.out.println("kingNameWithPathMethod = " + kingNameWithPathMethod);


    }
}
