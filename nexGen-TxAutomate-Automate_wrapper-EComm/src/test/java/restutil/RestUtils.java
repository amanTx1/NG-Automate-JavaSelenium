package restutil;

import org.testng.Assert;

import com.aventstack.extentreports.Status;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ExtentUtil;
import utilities.GlobalUtil;
import utilities.HTMLReportUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.Instant;
import org.apache.poi.ss.usermodel.*;

public class RestUtils {

	// Global Setup Variables
	public static String path;
	public static String jsonPathTerm;
	static RequestSpecification rs;
	public static String accessToken;
	public static Response response;
	public static String existingUserId; // Store the fetched user ID globally
	public static String userId;


	// Sets Base URI
	public static void setBaseURI(String baseURI) {
		RestAssured.baseURI = baseURI;
		ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Setting the baseURI as " + baseURI ));
	}


	// Sets base path
	public static void setBasePath(String basePathTerm) {
		RestAssured.basePath = basePathTerm;
		ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Setting the basePath as " + basePathTerm ));
	}

	// Reset Base URI (after test)
	public static void resetBaseURI(String logStep) {
		RestAssured.baseURI = null;
	}

	// Reset base path
	public static void resetBasePath(String logStep) {
		RestAssured.basePath = null;
	}

	private static Map<String, String> storedValues = new HashMap<>(); // Store dynamic values
    public static String newUserId; // Store the latest created User ID

    public static void storeValue(String key, String value) {
        storedValues.put(key, value);
    }

    public static String getStoredValue(String key) {
        return storedValues.getOrDefault(key, "");
    }

    public static void setNewUserId(String userId) {
        newUserId = userId;
    }

    public static String getNewUserId() {
        return newUserId;
    }

	public static void fetchExistingUser(String baseURI, String basePath) {
		if (basePath.contains("{id}")) {
			String latestId = fetchUserId(baseURI); // Fetch user ID dynamically
			
			if (latestId != null && !latestId.isEmpty()) {
				basePath = basePath.replace("{id}", latestId.trim());
				existingUserId = latestId.trim(); // Store for validation
				System.out.println("Fetched latest user ID from GET: " + existingUserId);
			} else {
				throw new RuntimeException("Failed to fetch a valid user ID from GET!");
			}
		}
	
		RestUtils.setBaseURI(baseURI);
		RestUtils.setBasePath(basePath);
		System.out.println("Final API Endpoint (Existing User): " + baseURI + basePath);
	}

	public static void setAPIEndpoint(String baseURI, String basePath) {
		// Check if the path contains {id} placeholder
		if (basePath.contains("{id}")) {
			String latestId = fetchUserId(baseURI);
			System.out.println("The latest ID is: " + latestId);
			
			// Ensure latestId is a valid numeric value
			if (latestId != null && !latestId.isEmpty()) {
				basePath = basePath.replace("{id}", latestId.trim());  // Ensure proper replacement
			} else {
				throw new RuntimeException("Failed to fetch a valid user ID");
			}
		}
		
		// Set API base URI and base path
		RestUtils.setBaseURI(baseURI);
		RestUtils.setBasePath(basePath);
		
		// Debug: Print the final API endpoint
		System.out.println("Final API Endpoint: " + baseURI + basePath);
	}

	public static void setApiEndpoint(String baseURI, String basePath) {
        if (basePath.contains("{id}")) {
            basePath = basePath.replace("{id}", userId);
        }
        RestUtils.setBaseURI(baseURI);
        RestUtils.setBasePath(basePath);
        System.out.println("Final API Endpoint: " + baseURI + basePath);
    }
	

// code for testing purpose only
	public static void APIEndpoint(String baseURI, String basePath) {
		// Check if the path contains {id} placeholder
		if (basePath.contains("{id}")) {
			// Read user ID from Excel instead of fetching from API
			String userId = readUserIdFromExcel();
			System.out.println("User ID from Excel: " + userId);
			
			// Ensure userId is a valid numeric value
			if (userId != null && !userId.isEmpty()) {
				basePath = basePath.replace("{id}", userId.trim());  // Ensure proper replacement
			} else {
				throw new RuntimeException("Failed to get a valid user ID from Excel");
			}
		}
		
		// Set API base URI and base path
		RestUtils.setBaseURI(baseURI);
		RestUtils.setBasePath(basePath);
		
		// Debug: Print the final API endpoint
		System.out.println("Final API Endpoint: " + baseURI + basePath);
	}

// till here

	public static void updateExcelWithUserId(String userId, String excelPath, int columnToUpdate, int rowToUpdate) {
    try {
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);  // Gets first sheet
        
        Row row = sheet.getRow(rowToUpdate);
        if (row == null) {
            row = sheet.createRow(rowToUpdate);
        }
        
        Cell nameCell = row.getCell(0);
        if (nameCell == null) {
            nameCell = row.createCell(0);
        }
        nameCell.setCellValue("fetch userid");
        
        // Create cell in Column B (index 1) for user ID
        Cell valueCell = row.getCell(1);
        if (valueCell == null) {
            valueCell = row.createCell(1);
        }
        valueCell.setCellValue(userId);
        
        fis.close();
        
        FileOutputStream fos = new FileOutputStream(excelPath);
        workbook.write(fos);
        workbook.close();
        fos.close();
        
        System.out.println("Excel updated successfully with User ID: " + userId);
    } catch (IOException e) {
        throw new RuntimeException("Error updating Excel file", e);
    }
}

	public static String fetchUserId(String baseURI) {
        try {
            URL url = new URL(baseURI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			System.out.println("connecting...");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/html");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
                
                String userId = extractUserId(response.toString());
				System.out.println("The current one user ID is:" + userId);
				
				if (userId != null) {
					updateExcelWithUserId(userId, "src\\test\\resources\\ExcelFiles\\AutomationControlSheet.xlsx", 1, 54);
					
				}
				
				conn.disconnect();
				return userId;
            } else {
                conn.disconnect();
                throw new RuntimeException("Failed to get response from server.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user ID", e);
        }
    }

    private static String extractUserId(String html) {
        Pattern pattern = Pattern.compile("GET /public/v2/users/(\\d+)");
        Matcher matcher = pattern.matcher(html);
        return matcher.find() ? matcher.group(1) : null;
    }



	public static String readUserIdFromExcel() {
        String userId = null;
        try {
            FileInputStream fis = new FileInputStream("src\\test\\resources\\ExcelFiles\\AutomationControlSheet.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(54);  // Assuming ID is still in row 54
            
            if (row != null) {
                Cell cell = row.getCell(1);  // Column B (index 1)
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            userId = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            userId = String.valueOf((long) cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            throw new RuntimeException("Cell is empty");
                        default:
                            throw new RuntimeException("Unexpected cell type");
                    }
                }
            }
            
            workbook.close();
            fis.close();
            
            if (userId == null) {
                throw new RuntimeException("User ID not found in Excel file");
            }
            
            System.out.println("Read user ID from Excel: " + userId);
            return userId;
            
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file", e);
        }
    }


	public static String generateDynamicEmail() {
        // Generate a random string of 8 characters
        String randomString = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        long timestamp = Instant.now().getEpochSecond();
        String formattedEmail = String.format("%s_%d@example.com", randomString, timestamp);
		System.out.println("Generated Unique Email: " + formattedEmail);
		return formattedEmail;
    }

	
	public static Response getResponse(ContentType type, String payload) {
		try {
			rs = RestAssured.given();
			rs.contentType(type);
			rs.body(payload);
			Response response = rs.get();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}

	}

	public static Response getResponse(ContentType type, String paylaod ,String accessToken) {
		try {
			rs = RestAssured.given()
				.contentType(type)
				.header("Authorization", "Bearer " + accessToken)
				.body(paylaod)
				.log().all();
			response = rs.get();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response postResponse(ContentType type, String payload) {
		try {
			rs = RestAssured.given();
			rs.body(payload);
			rs.contentType(type);
			Response response = rs.post();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response postResponse(ContentType type, String payload ,String accessToken) {
		try {
			rs = RestAssured.given()
				.contentType(type)
				.header("Authorization", "Bearer " + accessToken)
				.body(payload)
				.log().all();
			response = rs.post();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response putResponse(ContentType type, String payload) {
		try {
			rs = RestAssured.given();
			rs.body(payload);
			rs.contentType(type);
			Response response = rs.put();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response putResponse(ContentType type, String payload ,String accessToken) {
		try {
			rs = RestAssured.given()
				.contentType(type)
				.header("Authorization", "Bearer " + accessToken)
				.body(payload)
				.log().all();
			response = rs.put();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response deleteResponse(ContentType type, String payload) {
		try {
			rs = RestAssured.given();
			rs.body(payload);
			rs.contentType(type);
			Response response = rs.delete();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("Response >> "+GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response deleteResponse(ContentType type, String payload ,String accessToken) {
		try {
			rs = RestAssured.given()
				.contentType(type)
				.header("Authorization", "Bearer " + accessToken)
				.body(payload)
				.log().all();
			response = rs.delete();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response patchResponse(ContentType type, String payload) {
		try {
			rs = RestAssured.given();
			rs.body(payload);
			rs.contentType(type);
			Response response = rs.patch();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}

	public static Response patchResponse(ContentType type, String payload ,String accessToken) {
		try {
			rs = RestAssured.given()
				.contentType(type)
				.header("Authorization", "Bearer " + accessToken)
				.body(payload)
				.log().all();
			response = rs.patch();
			System.out.println(response.prettyPrint());
			ExtentUtil.logger.get().log(Status.INFO, HTMLReportUtil.infoStringBlueColor("Response >> "+response.prettyPrint()));
			return response;
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
			return null;
		}
	}


	// Returns JsonPath object
	public static JsonPath getJsonPath(Response res) {
		String json = res.asString();
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Verifying JSON path"));
		return new JsonPath(json);
	}

	public static Object getValueFromJson(Response res, String path) {
		String json = res.asString();
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Verifying JSON path value"));
		return new JsonPath(json).getString(path);

	}

	public static void checkStatus(Response res, int expectedStatusCode) {
		try {
			Assert.assertEquals(res.getStatusCode(), expectedStatusCode, "Status Check Failed! Actual = " + res.getStatusCode() + " Expected = " + expectedStatusCode);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Actual = " + res.getStatusCode() + " Expected = " + expectedStatusCode));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			System.out.println(e.getMessage());
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Unexpected error occurred: " + e.getMessage());
		}
	}

	public static void validateValues(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected, "Validation Failed >> Expected value: " + expected + " but got: " + actual);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Validation Passed >> Expected value: " + expected + " Actual: " + actual));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			System.out.println(e.getMessage());
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(GlobalUtil.errorMsg));
			Assert.fail("Expected value: " + expected + " but got: " + actual);
		}
	}
 
}


