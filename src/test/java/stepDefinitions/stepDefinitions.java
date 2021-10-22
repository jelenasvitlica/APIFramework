package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.*;



public class stepDefinitions extends Utils {
    public static RequestSpecification res;
    public static ResponseSpecification resspec;
    public static Response response;
    public static String place_id;


    TestDataBuild data = new TestDataBuild();


    @Given("Add place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {

        res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources resourcesAPI = APIResources.valueOf(resource);

        if(method.equalsIgnoreCase("post")){
            response = res.when().post(resourcesAPI.getResource());

        }else if(method.equalsIgnoreCase("get")){
            response = res.when().get(resourcesAPI.getResource());

        }

    }

    @Then("the API call got success with status call {int}")
    public void theAPICallGotSuccessWithStatusCall(int arg0) {

       assertEquals(response.getStatusCode(), 200);

    }

    @And("{string} in response body is {string}")
    public void something_in_response_body_is_something(String keyValue, String expectedValue) {



      assertEquals(getJsonPath(response, keyValue),expectedValue);


    }

    @Then("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {

        place_id = getJsonPath(response, "place_id");
        res = given().spec(requestSpecification()).queryParam("place_id", place_id);
        user_calls_with_http_request(resource,"get");
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedName);

    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }


}
