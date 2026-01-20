package restutil;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	// Global Setup Variables
	public static String path;
	public static String jsonPathTerm;
	static RequestSpecification rs;

	// Sets Base URI
	public static void setBaseURI(String baseURI) {
		RestAssured.baseURI = baseURI;
	}

	// Sets base path
	public static void setBasePath(String basePathTerm) {
		RestAssured.basePath = basePathTerm;
	}

	// Reset Base URI (after test)
	public static void resetBaseURI() {
		RestAssured.baseURI = null;
	}

	// Reset base path
	public static void resetBasePath() {
		RestAssured.basePath = null;
	}

	// Sets ContentType
	public static void setContentType(ContentType Type) {
		rs.given().contentType(Type);
	}

	// Sets Json path term
	public static void setJsonPathTerm(String jsonPath) {
		jsonPathTerm = jsonPath;
	}

	// Created search query path
	public static void createSearchQueryPath(String searchTerm, String param, String paramValue) {
		path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
	}

	// Returns response
	public static Response getResponse() {
		return rs.get(path);

	}

	// Returns JsonPath object
	public static JsonPath getJsonPath(Response res) {
		String json = res.asString();
		return new JsonPath(json);
	}

	// Verify the http response status returned. Check Status Code is 200?
	public static void checkStatusIs200(Response res) {
		Assert.assertEquals(res.getStatusCode(), 200, "Status Check Failed!");
	}
}