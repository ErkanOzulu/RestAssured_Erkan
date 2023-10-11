package com.cydeo.utilities;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
public class BookItUtil {

    //create BookItUtil then create a method, that accepts email and password return token Bearer +yourToken as a String

public static String yourToken(String email, String password){
    String token = given().accept(ContentType.JSON)
            .queryParams("email", email, "password", password)
            .when().get("/sign")
            .then().statusCode(200)
            .extract().jsonPath().getString("accessToken");

    String yourToken="Bearer "+token;

    return yourToken;
}

}
