package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.junit.Assert;
import org.openqa.selenium.json.Json;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.*;



public class stepDefinitions extends Utils {
    public static RequestSpecification res;
    public static ResponseSpecification resspec;
    public static Response response;
    public static String place_id;
    public static String issueId;
    SessionFilter session = new SessionFilter();
    String response1;


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
    public void theAPICallGotSuccessWithStatusCall(int actualStatusCode) {

       assertEquals(response.getStatusCode(), actualStatusCode);

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

    @Given("Login with user {string} and {string}")
    public void login_with_user_and(String name, String pass) {
        baseURI="http://localhost:8080/";
        response1=given().header("Content-Type", "application/json").body("{ \"username\": \""+name+"\", \"password\": \""+pass+"\" }").log().all()
                .filter(session).when().post("rest/auth/1/session").then().log().all().extract().response().asString();
    }

    @When("User calls AddComment with post http request")
    public void user_calls_add_comment_with_post_http_request() {
        baseURI="http://localhost:8080/";
        response = given().baseUri("http://localhost:8080/").pathParam("key", "10101").log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"Drugi comment . Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.\"\n" +
                        "}").filter(session).when().post("rest/api/2/issue/{key}/comment");
    }


    @When("User calls AddAttachment with post http request")
    public void user_calls_add_attachment_with_post_http_request() {

        response = given().baseUri("http://localhost:8080/").header("X-Atlassian-Token","no-check").header("Content-Type", "multipart/form-data").filter(session).pathParam("key", "10101")
                .multiPart("file",new File("jira.txt"))
                .when().post("rest/api/2/issue/{key}/attachments");

    }

    @When("User calls AddIssue  with post http request")
    public void user_calls_add_issue_with_post_http_request() {

        response = given().baseUri("http://localhost:8080/").header("Content-Type", "application/json").body("{\r\n    \"fields\": {\r\n       \"project\":\r\n       {\r\n          \"key\": \"APIF\"\r\n       },\r\n       \"summary\": \"REST assured.\",\r\n       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n       \"issuetype\": {\r\n          \"name\": \"Bug\"\r\n       }\r\n   }\r\n}")
                .filter(session).when().post("/rest/api/2/issue");
        issueId = getJsonPath(response, "id");


    }

    @Then("Verify issueId")
    public void verify_issue_id() {
        String issueDetails = given().filter(session).pathParam("key",issueId).queryParam("fields","comment")
                .when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
        System.out.println(issueDetails);
        JsonPath js = new JsonPath(issueDetails);
        String issueActual = js.get("id").toString();
        Assert.assertEquals(issueId, issueActual);
        System.out.println("OVO JE JEDAN ISSUE ID "+issueId+" A OVO JE DRUGI "+issueActual);

    }



}
