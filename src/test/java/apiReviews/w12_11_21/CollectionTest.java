package apiReviews.w12_11_21;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
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

    /*
{
    "post code": "45414",
    "country": "United States",
    "country abbreviation": "US",
    "places": [
        {
            "place name": "Dayton",
            "longitude": "-84.2024",
            "state": "Ohio",
            "state abbreviation": "OH",
            "latitude": "39.8285"
        }
         {
            "place name": "New York",
            "longitude": "-84.2024",
            "state": "Ohio",
            "state abbreviation": "OH",
            "latitude": "39.8285"
        }
    ]
}
 */
    @Test
    public void zipTestCollection(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("zipCode",45414)
                .when().get(zipUrl+"US/{zipCode}");

        assertEquals(response.statusCode(),200);

        // to reach my response body and for assertions
        Map<String,Object> pc45414 = response.body().as(Map.class);  // deserialization

        System.out.println(pc45414);

        assertEquals(pc45414.get("country"),"United States");
        assertEquals(pc45414.get("post code"),"45414");


        // I am getting "places" key from my Map and putting the value in a List<Map>
        List<Map<String,Object>> placesFor45414 = (List<Map<String, Object>>) pc45414.get("places");

        assertEquals(placesFor45414.get(0).get("state"),"Ohio");
        assertEquals(placesFor45414.get(0).get("state abbreviation"),"OH");

   //      placesFor45414.get(1).get("place name")


    }



}
