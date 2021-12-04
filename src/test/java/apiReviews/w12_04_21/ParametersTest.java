package apiReviews.w12_04_21;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParametersTest {

    String hrurl = "http://54.92.248.102:1000/ords/hr";
    String zipUrl = "http://api.zippopotam.us/";
    String spartanUrl = "http://54.92.248.102:8000";


    @Test
    public void pathParamTest(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("id",15)
                .when().get(spartanUrl+"/api/spartans/{id}");

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Peter"));


    }

}
