package apiReviews.w12_11_21;

import static io.restassured.RestAssured.*;

import Day6_POJO.Spartan;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
public class CreateSpartan {

    @BeforeClass
    public void setUp(){
        baseURI = "http://3.239.148.14:8000";
        basePath = "/api/spartans";
    }

    @Test
    public void postSpartan(){

        Spartan mySpartan = new Spartan();
        mySpartan.setName("Oscar");
        mySpartan.setGender("Male");
        mySpartan.setPhone(2341234567L);

        Response response = given()
                .accept(ContentType.JSON)         // hey API I want JSON response
                .and()
                .contentType(ContentType.JSON)    // hey API I am sending JSON as a value (***This is needed for POST method)

        .and().body(mySpartan)              // serializition from JAVA to JSON

            .when().post();  // since I provided URI and Path as base parameter at BeforeClass

       int idFromPost = response.path("data.id");

        System.out.println(idFromPost);


     //    System.out.println(response.body().asString());

        // make a get request to spartan with this created id


        given().log().all().accept(ContentType.JSON)
                .pathParam("id",idFromPost)
                .when().get("/{id}")
                .then().statusCode(200);


    }
}
