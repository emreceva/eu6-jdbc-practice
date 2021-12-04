package apiReviews.w12_04_21;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class R01_API_Test {

    String hrurl = "http://54.92.248.102:1000/ords/hr";
    String zipUrl = "http://api.zippopotam.us/US/";
    String spartanUrl = "http://54.92.248.102:8000";

    // get me all employees from HR api
    @Test
    public void testOne(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrurl+"/employees");

        // response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
    }



}
