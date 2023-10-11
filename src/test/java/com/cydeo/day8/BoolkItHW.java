package com.cydeo.day8;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cydeo.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apiguardian.api.API;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.*;


public class BoolkItHW {
    /*
    HOMEWORK
   try to get name,role,batch number, campus, team name from api for one student
      it will be multiple api request
  responses returns batch name team name with students information
  first make sure your student is inside the respones then get those info
      prepare one list of information about student and compare with ui
  UI vs API

  verify same information vs
  connect db and get the same information which requires joins multiple tables

     */
    static Statement statement;
    static Connection connection;


    @BeforeAll
    public static void init() throws SQLException {
        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";

        connection = DriverManager.getConnection(ConfigurationReader.get("dbUrl"), ConfigurationReader.get("dbUsername"), ConfigurationReader.get("dbPassword"));
       statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    }

    @DisplayName("BookIt three layers verifications")
    @Test()
    public void test() throws SQLException {


        String teacherToken = given().accept(ContentType.JSON)
                .queryParams("email", ConfigurationReader.get("teacher_email"), "password", ConfigurationReader.get("teacher_password"))
                .when()
                .get("/sign").then().statusCode(200).extract().jsonPath().getString("accessToken");

        String token = "Bearer " + teacherToken;

        //post a new student

        Map<String, Object> newStudent = new HashMap<>();
        newStudent.put("first-name", "Mustafa");
        newStudent.put("last-name", "İsmail");
        newStudent.put("email", "mismail@gmail.com");
        newStudent.put("password", "mis123");
        newStudent.put("role", "student-team-member");
        newStudent.put("campus-location", "VA");
        newStudent.put("batch-number", 8);
        newStudent.put("team-name", "Nukes");

        int postedId = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .queryParams(newStudent)
                .header("Authorization", token)
                .post("/api/students/student")
                .then().statusCode(201).extract().jsonPath().getInt("entryiId");


        // try to get name,role,batch number, campus, team name from api for one student
        //    it will be multiple api request

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", postedId)
                .header("Authorization", token)
                .when().get("/api/students/{id}")
                .then().statusCode(200)
                .extract().jsonPath();
        String nameApi = jsonPath.getString("firstName") + " " + jsonPath.getString("lastName");
        String roleApi = jsonPath.getString("role");

        String myToken = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .queryParam("email", newStudent.get("email"))
                .queryParam("password", newStudent.get("password"))
                .get("/sign")
                .then().statusCode(200).extract().response().path("accessToken");

        String newStudentToken = "Bearer " + myToken;

        String campusApi = given().accept(ContentType.JSON)
                .header("Authorization", newStudentToken)
                .get("/api/campuses/my")
                .then().statusCode(200).extract().response().path("location");

        int batchApi = given().accept(ContentType.JSON)
                .header("Authorization", newStudentToken)
                .get("/api/batches/my")
                .then().statusCode(200).extract().response().path("number");

        String teamApi = given().accept(ContentType.JSON)
                .header("Authorization", newStudentToken)
                .get("/api/teams/my")
                .then().statusCode(200).extract().response().path("name");

        List<Object> apiResult = new LinkedList<>();

        apiResult.addAll(Arrays.asList(nameApi, roleApi, batchApi, campusApi, teamApi));
        for (Object s : apiResult) {
            System.out.println(s);
        }

        //=========================================//

        String query = "select firstname||' '|| lastname as name,role,t.batch_number,c.location as campus,t.name as teamname from users u join team t on u.team_id= t.id join campus c on t.campus_id= c.id where u.id=" + postedId;


        ResultSet resultSet = statement.executeQuery(query);


        ResultSetMetaData rsmd = resultSet.getMetaData();
        Map<String, Object> dbResult = new LinkedHashMap<>();
        resultSet.next();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {

            dbResult.put(rsmd.getColumnName(i), resultSet.getObject(i));
            System.out.println("dbResult.get("+i+") = " + resultSet.getObject(i));
        }

        assertEquals(dbResult.get("name"), apiResult.get(0));
        assertEquals(dbResult.get("role"), apiResult.get(1));
        assertEquals(dbResult.get("batch_number"), apiResult.get(2));
        assertEquals(dbResult.get("campus"), apiResult.get(3));
        assertEquals(dbResult.get("teamname"), apiResult.get(4));

        resultSet.close();
        statement.close();
        connection.close();



    }




}
