package apiReviews.w12_11_21;

import Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class SpartanFlow {


    String spartanUrl = "http://3.239.148.14:8000";
    Response mockSpartan;
    int idFromPost;
   @BeforeClass
    public void createSpartan(){

        // I want to send get request to my Mock API to receive a spartan object

        mockSpartan = given().accept(ContentType.JSON)
                .header("X-API-Key","cb98a4c0")
                .when().get("https://my.api.mockaroo.com/eu6Spartans.json");

       System.out.println("mockSpartan.body() = " + mockSpartan.body().asString());

   }

   @AfterClass
   public void deleteSpartan(){

       given().pathParam("id",idFromPost)
               .when().delete(spartanUrl+"/api/spartans/{id}").then().log().all()
               .assertThat().statusCode(204);


   }


    @Test()
    public void postSpartan(){

        Spartan mySpartan = new Spartan();
        mySpartan.setName(mockSpartan.path("name"));
        mySpartan.setGender(mockSpartan.path("gender"));
        Long phone = Long.valueOf(mockSpartan.path("phone").toString());
        mySpartan.setPhone(phone);

        Response postResponse = given()
                .accept(ContentType.JSON)         // hey API I want JSON response
                .and()
                .contentType(ContentType.JSON)    // hey API I am sending JSON as a value (***This is needed for POST method)

                .and().body(mySpartan)              // serializition from JAVA to JSON

                .when().post(spartanUrl+"/api/spartans");  // since I provided URI and Path as base parameter at BeforeClass

        idFromPost = postResponse.path("data.id");

        System.out.println(idFromPost);

        Assert.assertEquals(postResponse.path("success"),"A Spartan is Born!");


        //    System.out.println(response.body().asString());

        // make a get request to spartan with this created id


        given().log().all().accept(ContentType.JSON)
                .pathParam("id",idFromPost)
                .when().get(spartanUrl+"/api/spartans/{id}")
                .then().statusCode(200);


    }


}
