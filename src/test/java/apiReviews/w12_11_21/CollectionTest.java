package apiReviews.w12_11_21;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class CollectionTest {

    String spartanUrl = "http://3.239.148.14:8000";
    String zipUrl = "http://api.zippopotam.us/";  // zipUrl + "US/{zipCode}, 45414

    @Test
    public void oneSpartanWithMap(){
        /*
    {
    "id": 7,
    "name": "Hershel",
    "gender": "Male",
    "phone": 5278678322
}
     */

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",7)
                .when().get(spartanUrl+"/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        Map<String,Object> spartan7 = response.body().as(Map.class);

        System.out.println(spartan7);

        assertEquals(spartan7.get("id"),7);
        assertEquals(spartan7.get("name"),"Hershel");
        assertEquals(spartan7.get("gender"),"Male");
        assertEquals(spartan7.get("phone"),5278678322L);



    }


}
