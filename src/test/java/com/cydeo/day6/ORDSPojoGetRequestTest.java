package com.cydeo.day6;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Link;
import com.cydeo.pojo.Region;
import com.cydeo.pojo.Regions;
import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ORDSPojoGetRequestTest extends HRTestBase {

    //https://www.jsonschema2pojo.org/


    @Test
    public void tetst1() {


        Region region1 = given().accept(ContentType.JSON)
                .when().get("/regions")
                .then().statusCode(200)
                .log().body()
                .extract().jsonPath().getObject("items[0]", Region.class);

        System.out.println("region1 = " + region1);
        System.out.println("region1.getRegion_id() = " + region1.getRegionId());

        /*
        as we added jackson-databind dependency in pom.xml, we can use jsonPath() to convert Json to java or vice versa
        -- we can also achive this result using gson dependency
        it allows
        Strin jsonBody="{"name=krhwe",......}";
             ---  Abc abc1 =gson.fromJson(jsonBody, Abc.class)
             ---  String jsonBody=gson.toJson(Abc)

         */


        System.out.println("region1.getLinks().get(0).getHref() = " + region1.getLinks().get(0).getHref());

        System.out.println("=================second way===================");

        Link link1 = region1.getLinks().get(0);
        System.out.println("link1.getHref() = " + link1.getHref());


    }

    @DisplayName("GET request to /employees and only get couple of values as a Pojo class")
    @Test
    public void employeeGet() {

        Employee employee1 = get("/employees").then().statusCode(200)
                .extract().jsonPath().getObject("items[0]", Employee.class);
        System.out.println(employee1);
    }

    /* send a get request to regions
        verify that region ids are 1,2,3,4
        verify that regions names Europe ,Americas , Asia, Middle East and Africa
        verify that count is 4
        try to use pojo as much as possible
        ignore non used fields
     */

    @DisplayName("GET request to region only some fields test")
    @Test
    public void regionPojoTest() {
        //send a get request and save everthing inside the regions object
        //since we prepare pojo also for the all properties we dont need to use any path so as() method is enough

        Regions regions = get("regions").then().statusCode(200)
                .extract().response().as(Regions.class);

        //verify count is 4
        assertThat(regions.getCount(), is(4));

        //create empty list to store values

        List<String> regionNames = new ArrayList<>();
        List<Integer> regionIds = new ArrayList<>();


        //get list of regions out of regions object

        List<Region> items = regions.getItems();

        //loop through each of the region, save their ids and names to empty list that we prepare
        for (Region region : items) {

            regionIds.add(region.getRegionId());
            regionNames.add(region.getRegion_name());

        }

        System.out.println("regionIds = " + regionIds);
        System.out.println("regionNames = " + regionNames);

         //prepare expected result

        List<Integer>expectedRegionIds= Arrays.asList(1,2,3,4);
        List<String>expectedregionNames=Arrays.asList("Europe", "Americas", "Asia", "Middle East and Africa");

        //compare two result

        assertThat(regionIds,is(expectedRegionIds));
        assertThat(regionNames,is(expectedregionNames));


    }

}
