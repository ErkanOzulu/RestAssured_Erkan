package com.cydeo.utilities;


import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanAuthTestBase {

    @BeforeAll
    public static void init(){
        /* save baseurl inside this variable so that we dont need to type each http method. */
       baseURI = "http://3.238.70.42:7000";


    }


}
