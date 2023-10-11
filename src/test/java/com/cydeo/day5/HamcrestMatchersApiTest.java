package com.cydeo.day5;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersApiTest {

    /*
       given accept type is Json
       And path param id is 15
       When user sends a get request to spartans/{id}
       Then status code is 200
       And content type is Json
       And json data has following
           "id": 15,
           "name": "Meta",
           "gender": "Female",
           "phone": 1938695106
        */

    @DisplayName("OneSpartan with Hamcrest and chaining")
    @Test
    public void test1() {

        given().log().all()
        .accept(ContentType.JSON)
                .pathParam("id", 15)
                .when()
                .get("http://3.238.70.42:8000/api/spartans/{id}")
                .then()
                .statusCode(200)
                .and().contentType("application/json")
                .and()
                .body("id", is(15), "name", is("Meta"), "gender", is("Female"),
                        "phone", is(1938695106))
                .log().all();


    }

    @DisplayName("CBTraining Teacher request with chaining and matchers")
    @Test
    public void teacherData() {
        given().accept(ContentType.JSON)
                .and().pathParam("id", 3)
                .when()
                .get("https://api.training.cydeo.com/teacher/{id}")
                .then()
                .statusCode(200)
                .and()
                .contentType("application/json")
                .and()
                .header("server", is("envoy"))
                .and()
                .header("transfer-encoding", is("chunked"))
                .and()
                .header("date", notNullValue())
                .body("teachers[0].firstName", is("Tet"), "teachers[0].lastName", is("DS"))
                .body("teachers[0].gender", equalTo("Male")).log().all();

    }

    @DisplayName("Get request to teacher/all and chaining")
    @Test
    public void teachersTest() {
                given()
                    .accept(ContentType.JSON)
                .when()
                    .get("https://api.training.cydeo.com/teacher/all")
                        .then()
                        .statusCode(200)
                        .and()
                        .body("teachers.firstName",hasItems("Tet","Valter","Mario"));

    }


}
