package apiReviews.w12_04_21;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class pathMethodTest {

    /*
    1:
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- content type application/json
- has header "Date"
- And count is 6
- And hasMore is false
2:
- And all regions_id is 3
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */


    @BeforeClass
    public void setUp(){baseURI="http://54.92.248.102:1000/ords/hr";}

    @Test
    public void pathMethodCountries(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.headers().hasHeaderWithName("Date"));
       //And count is 6
        int actualCount = response.path("count");
        assertEquals(actualCount,6);
        //- And hasMore is false
        boolean actualHasMore = response.path("hasMore");
        assertEquals(actualHasMore,false);

        // "country_name": "India",  how can I reach this with gpath syntax
        String actualCountry = response.path("items[2].country_name");
        System.out.println("actualCountry = " + actualCountry);

        System.out.println(response.path("items[2].links[0].href").toString()); // http://54.92.248.102:1000/ords/hr/countries/IN
      //  System.out.println((String) response.path("items[2].links[0].href"))
        // check all items region ids

        List<Integer> regions = response.path("items.region_id");
        for (int region : regions) {
            assertEquals(region,3);  // wrapper Integer can not be equal to primitive
        }

        // Australia,China,India,Japan,Malaysia,Singapore
        List<String> expectedCountries = new ArrayList(Arrays.asList("Australia","India","Japan","China","Malaysia","Singapore"));
        Collections.sort(expectedCountries);
        System.out.println("expectedCountries = " + expectedCountries);
        List<String> actualCountries = response.path("items.country_name");
        Collections.sort(actualCountries);
        System.out.println("actualCountries = " + actualCountries);

        assertEquals(actualCountries,expectedCountries);


    }
}
