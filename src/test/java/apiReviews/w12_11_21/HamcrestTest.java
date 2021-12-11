package apiReviews.w12_11_21;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HamcrestTest {

    /*
    {
    "id": 7,
    "name": "Hershel",
    "gender": "Male",
    "phone": 5278678322
}
     */
    String spartanUrl = "http://3.239.148.14:8000";
    String zipUrl = "http://api.zippopotam.us/";  // zipUrl + "US/{zipCode}, 45414

    @Test
    public void oneSpartanTest(){

       given().log().all().accept(ContentType.JSON)
               .and().pathParam("id",7)
               .when().get(spartanUrl+"/api/spartans/{id}")
               .then().assertThat().statusCode(200).and().contentType("application/json")

         .log().all()
         .body("id",equalTo(7),"name",equalTo("Hershel")
         ,"gender",equalTo("Male"),"phone",equalTo(5278678322L));

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
    ]
}
 */

    @Test
    public void zipTest(){

        given().accept(ContentType.JSON)
                .and().pathParam("zipCode",45414)
                .when().get(zipUrl+"US/{zipCode}")
                .then().assertThat().statusCode(200)
                .body("country",equalTo("United States"),
                        "places.state[0]",equalTo("Ohio"),
                    "'post code'",equalTo("45414"),
                        "'country abbreviation'",equalTo("US"));





    }


}
