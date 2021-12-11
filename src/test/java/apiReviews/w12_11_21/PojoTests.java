package apiReviews.w12_11_21;


import Day6_POJO.Spartan;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class PojoTests {
    String spartanUrl = "http://3.239.148.14:8000";
    String zipUrl = "http://api.zippopotam.us/";

    @Test
    public void spartanWithPojo(){


        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",7)
                .when().get(spartanUrl+"/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        Spartan spartan7 = response.body().as(Spartan.class); // de-serialization
        // as() method to map my json object to Spartan Object
        System.out.println("spartan7.getName() = " + spartan7.getName());

        assertEquals(spartan7.getName(),"Hershel");


    }


    @Test
    public void zipTestWithPojo(){

        Gson gson = new Gson();
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("zipCode",45414)
                .when().get(zipUrl+"US/{zipCode}");

        assertEquals(response.statusCode(),200);

        System.out.println(response.body().asString());


        PostalCode pc45414 = gson.fromJson(response.body().asString(),PostalCode.class);

      //  PostalCode pc45414 = response.body().as(PostalCode.class);

        System.out.println(pc45414.getCountry());
        System.out.println(pc45414.getPlaces().get(0).getPlaceName());

    }


}
