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

}
