package com.cydeo.day8;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SpartanWithAuthTest extends SpartanAuthTestBase {

    @DisplayName("GET /api/spartans as a public user (guest) expect 401")
    @Test
    public void tets1() {
        given().accept(ContentType.JSON)
                .when()
                .get("/api/spartans")
                .then().statusCode(401)
                .log().all();

    }

    @DisplayName("GET /api/spartans as admin user expect 200")
    @Test
    public void testAdmin() {
        //how to pass admin admin as a username and password ?
        given().auth().basic("admin", "admin")
                .and().accept(ContentType.JSON)
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(200)
                .log().all();


    }

    @DisplayName("DELETE /spartans/{id} as editor user expect 403")
    @Test
    public void testEditorDelete() {

        given().auth()
                .basic("editor","editor")
                .and().accept(ContentType.JSON)
                .pathParam("id",10)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(403)
                .log().body();

    }


    /*
        As a homework,write a detealied test for Role Base Access Control(RBAC)
            in Spartan Auth app (7000)
            Admin should be able take all CRUD
            Editor should be able to take all CRUD
                other than DELETE
            User should be able to only READ data
                not update,delete,create (POST,PUT,PATCH,DELETE)
       --------------------------------------------------------
        Can guest even read data ? 401 for all

     */
   @DisplayName("HW-Role Base Access Control(RBAC)/as admin")
    @Test
    public void testRBAC1(){
       //Admin should be able take all CRUD

       //GET all spartan as admin, user expected to get 200
       given().auth()
               .basic("admin","admin")
               .and(). accept(ContentType.JSON)
               .when().get("/api/spartans")
               .then().statusCode(200);

       //GET individual spartan as admin, user expected to get 200

       given().auth().basic("admin","admin")
               .and().accept(ContentType.JSON)
               .pathParam("id",100)
               .when().get("/api/spartans/{id}")
               .then().statusCode(200);

       //POST  a new spartan as admin, user expected to get 201

       String requestedSpartan="{ \"gender\": \"Male\", \"name\": \"Mustafa\", \"phone\": 1234566545}";

       int postedId = given()
               .auth().basic("admin", "admin")
               .and().accept(ContentType.JSON)
               .and().contentType(ContentType.JSON)
               .body(requestedSpartan)
               .when().post("/api/spartans")
               .then().statusCode(201)
               .body("success", is("A Spartan is Born!")).extract().jsonPath().getInt("data.id");

       //PUT  a spartan as admin, user expected to get 204
       String updatedBody=" { \n" +
               "    \"gender\": \"Male\",\n" +
               "    \"name\": \"Mustafa Ismail\",\n" +
               "    \"phone\": 1234566511\n" +
               "}";

       given()
               .auth().basic("admin","admin")
               .and().contentType(ContentType.JSON)
               .pathParam("id",postedId)
               .body(updatedBody)
               .when().put("/api/spartans/{id}")
               .then().statusCode(204);

       //PATCH  a spartan as admin, user expected get 204

       given()
               .auth().basic("admin","admin")
               .and().contentType(ContentType.JSON)
               .pathParam("id",postedId)
               .body("{\"phone\":\"1234566533\"}")
               .when().patch("/api/spartans/{id}")
               .then().statusCode(204);

       //DELETE a spartan as admin, user expected get

       given()
               .auth().basic("admin","admin")
               .pathParam("id",postedId)
               .when().delete("/api/spartans/{id}")
               .then().statusCode(204);

    }

    @DisplayName("HW-Role Base Access Control(RBAC)/as editor")
    @Test
    public void testRBAC2(){
    
       //Editor should be able to take all CRUD other than DELETE
        //GET all spartan as editor, user expected to get 200
        given().auth()
                .basic("editor","editor")
                .and(). accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().statusCode(200);

        //GET individual spartan as editor, user expected to get 200

        given().auth().basic("editor","editor")
                .and().accept(ContentType.JSON)
                .pathParam("id",100)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200);

        //POST  a new spartan as editor, user expected to get 201

        String requestedSpartan="{ \"gender\": \"Male\", \"name\": \"Ebubekir\", \"phone\": 1234561111}";

        int postedId = given()
                .auth().basic("editor", "editor")
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(requestedSpartan)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .body("success", is("A Spartan is Born!")).extract().jsonPath().getInt("data.id");

        //PUT  a spartan as editor, user expected to get 204
        String updatedBody=" { \n" +
                "    \"gender\": \"Male\",\n" +
                "    \"name\": \"Ebubekir Sıddık\",\n" +
                "    \"phone\": 1234566533\n" +
                "}";

        given()
                .auth().basic("editor","editor")
                .and().contentType(ContentType.JSON)
                .pathParam("id",postedId)
                .body(updatedBody)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);

        //PATCH  a spartan as editor, user expected get 204

        given()
                .auth().basic("editor","editor")
                .and().contentType(ContentType.JSON)
                .pathParam("id",postedId)
                .body("{\"name\":\"Sıddık-ı Ekber\"}")
                .when().patch("/api/spartans/{id}")
                .then().statusCode(204);

       // DELETE a spartan as editor, user expected get

        given()
                .auth().basic("editor","editor")
                .pathParam("id",postedId)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);
        
        
    }


}