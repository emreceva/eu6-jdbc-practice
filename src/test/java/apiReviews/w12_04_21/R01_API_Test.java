package apiReviews.w12_04_21;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class R01_API_Test {

    String hrurl = "http://54.92.248.102:1000/ords/hr";
    String zipUrl = "http://api.zippopotam.us/";
    String spartanUrl = "http://54.92.248.102:8000";

    // get me all employees from HR api
    @Test
    public void testOne(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrurl+"/employees");

        // response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
    }

    // get me all regions from HR api     hrurl+"/regions"

    @Test
    public void testTwo(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrurl+"/regions");

        // response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        // does the response have "Europe" in it?
        assertTrue(response.body().asString().contains("Europe"));
        //does my response have the header name "Date" in it
        assertTrue(response.headers().hasHeaderWithName("Date"));
        // Transfer-Encoding  = chunked
        assertEquals(response.header("Transfer-Encoding"),"chunked");

    }

    // do a request to Zip Url and get 45414 , verify it contains Dayton, verify status code and contentType

    @Test
    public void testThree(){
        Response response = given().accept(ContentType.JSON)   // static import
                        .when().get(zipUrl+"US/45414");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("Dayton"));
        response.prettyPrint();
    }
    @Test
    public void testFour(){
        given().accept(ContentType.JSON).when().get(zipUrl+"US/45414")
                .then().assertThat().contentType("application/json").and().statusCode(200);


    }




}
