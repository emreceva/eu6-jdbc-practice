package apiReviews.w12_04_21;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.baseURI;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;
public class jsonPathMethod {

    @BeforeClass
    public void setUp(){baseURI="http://54.92.248.102:1000/ords/hr";}


    @Test
    public void jsonPathTest(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.headers().hasHeaderWithName("Date"));


        JsonPath jsonPath = response.jsonPath();

        // we reach some useful methods

        //And count is 6
        assertEquals(jsonPath.getInt("count"),6);

        // And hasMore is false
        assertEquals(jsonPath.getBoolean("hasMore"),false);

        // "country_name": "India",  how can I reach this with gpath syntax

    //  String actualCountry = jsonPath.getString("items.country_name[2]");
        String actualCountry = jsonPath.getString("items[2].country_name");
        System.out.println("actualCountry = " + actualCountry);

    //   System.out.println(response.path("items[2].links[0].href").toString()); // http://54.92.248.102:1000/ords/hr/countries/IN

        System.out.println(jsonPath.getString("items.links[2].href[0]"));

        List<String> expectedCountries = new ArrayList(Arrays.asList("Australia","India","Japan","China","Malaysia","Singapore"));
        Collections.sort(expectedCountries);
        System.out.println("expectedCountries = " + expectedCountries);
        List<String> actualCountries = jsonPath.getList("items.country_name");
        Collections.sort(actualCountries);
        System.out.println("actualCountries = " + actualCountries);

        assertEquals(actualCountries,expectedCountries);

    }




}
