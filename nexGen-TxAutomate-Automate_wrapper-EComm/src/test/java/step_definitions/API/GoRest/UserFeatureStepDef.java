
package step_definitions.API.GoRest;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import restutil.RestUtils;
import utilities.ConfigReader;
import static org.junit.Assert.assertEquals;

public class UserFeatureStepDef {

    public static Response response;
    public static String accessToken = ConfigReader.getValue("accessToken");
    private static String userId;
    private String generatedEmail;

    @Given("The API endpoint for GET has baseURI {string} and basePath {string}")
    public void setGetApiEndpoint(String baseURI, String basePath) {
        RestUtils.setAPIEndpoint(baseURI, basePath);
    }

    @Given("I read the user ID from Excel file")
    public void readUserIdFromExcel() {
        userId = RestUtils.readUserIdFromExcel();
    }

    @When("I send a GET request to the endpoint")
    public void sendGetRequest() {
        response = RestUtils.getResponse(ContentType.JSON, "", accessToken);
    }

    @Then("I should receive a response with status code {int}")
    public void checkStatusCode(int expectedStatusCode) {
        RestUtils.checkStatus(response, expectedStatusCode);
    }

    @Then("the JSON path {string} should have value {string}")
    public void verifyJsonPathValue(String jsonPath, String expectedValue) {
        String actualValue = String.valueOf(RestUtils.getValueFromJson(response, jsonPath));
        if (expectedValue.equals("<stored_id>")) {
            expectedValue = String.valueOf(userId);
        }
        if (jsonPath.equals("email")) {
            expectedValue = generatedEmail; // Ensure we're validating against the correct email
        }

        System.out.println("Verifying JSON path: " + jsonPath + ", Expected: " + expectedValue + ", Actual: " + actualValue);
        assertEquals("Validation failed for " + jsonPath, expectedValue, actualValue);
    }

    @Given("The API endpoint for POST has baseURI {string} and basePath {string}")
    public void setPostApiEndpoint(String baseURI, String basePath) {
        RestUtils.setAPIEndpoint(baseURI, basePath);
    }

    @When("I generate a unique email and store it as {string}")
    public void generateUniqueEmail(String emailVariable) {
        generatedEmail = RestUtils.generateDynamicEmail();
        RestUtils.storeValue(emailVariable, generatedEmail); // Store for reuse
        System.out.println("Generated Email: " + generatedEmail);
    }

    @When("I send a POST request with access token in the header and {string} {string} {string} {string} to the endpoint")
    public void sendPostRequest(String name, String email, String gender, String status) {
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("email", generatedEmail);
        payload.put("gender", gender);
        payload.put("status", status);
        response = RestUtils.postResponse(ContentType.JSON, payload.toString(), accessToken);
        RestUtils.setNewUserId(userId);  // Store User ID globally
    }

    @Given("The API endpoint for PUT has BaseURI {string}, BasePath {string}")
    public void setPutApiEndpoint(String baseURI, String basePath) {
        RestUtils.APIEndpoint(baseURI, basePath);
    }

    @When("I send a PUT request with access token in the header and {string} {string} {string} {string} to the endpoint")
    public void sendPutRequest(String name, String email, String gender, String status) {
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("email", generatedEmail);
        payload.put("gender", gender);
        payload.put("status", status);
        response = RestUtils.putResponse(ContentType.JSON, payload.toString(), accessToken);
    }

    @Given("The API endpoint for DELETE has baseURI {string} and basePath {string}")
    public void setDeleteApiEndpoint(String baseURI, String basePath) {
        RestUtils.APIEndpoint(baseURI, basePath);
    }

    @When("I send a DELETE request with access token in the header to the endpoint")
    public void sendDeleteRequest() {
        response = RestUtils.deleteResponse(ContentType.JSON, "", accessToken);
    }

    @Given("The API endpoint for NEW POST has baseURI {string} and basePath {string}")
    public void setNewPostApiEndpoint(String baseURI, String basePath) {
        RestUtils.setAPIEndpoint(baseURI, basePath);
    }

    @When("I send a CREATE POST request with access token in the header and {string} {string} to the endpoint")
    public void sendCreatePostRequest(String title, String body) {
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("body", body);
        response = RestUtils.postResponse(ContentType.JSON, payload.toString(), accessToken);
    }

    @Given("The API endpoint for NEW TODO has baseURI {string} and basePath {string}")
    public void setNewTodoApiEndpoint(String baseURI, String basePath) {
        RestUtils.setAPIEndpoint(baseURI, basePath);
    }

    @When("I send a CREATE TODO request with access token in the header and {string} {string} to the endpoint")
    public void sendCreateTodoRequest(String title, String status) {
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("status", status);
        response = RestUtils.postResponse(ContentType.JSON, payload.toString(), accessToken);
    }
}
