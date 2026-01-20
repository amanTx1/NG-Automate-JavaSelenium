package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.entity.StringEntity;

public class TxAIService {

    private static final String PARENT_DIR_PATH = System.getProperty("user.dir")+"/src/test/resources/features/";
    private static final String[] SUBDIRECTORIES = {"Web"};
    private static final String WEB_XPATHS_FILE_PATH =  System.getProperty("user.dir")+"/src/test/java/utilities/WebXpaths.java";
    private static final String MOBILE_XPATHS_FILE_PATH =  System.getProperty("user.dir")+"/src/test/java/utilities/MobXpaths.java";
    private static final String TIMESTAMP_FILE_PATH =  System.getProperty("user.dir")+"/src/test/java/utilities/timestamps.txt";
    private static final String WEB_MAPPING_PATH =  System.getProperty("user.dir")+"/src/test/java/utilities/WebMapping.json";
    private static final String API_MAPPING_PATH = System.getProperty("user.dir")+"/src/test/java/utilities/APIMapping.json";
    private static final String MOBILE_MAPPING_PATH = System.getProperty("user.dir")+"/src/test/java/utilities/MobileMapping.json";
    private static final String OUTPUT_DIR_BASE_PATH =  System.getProperty("user.dir")+"/src/test/java/";
    
    private static final String BASE_URL = ConfigReader.getValue("BASE_URL");
    private static final String API_KEY = ConfigReader.getValue("API_KEY");
    
    private Map<String, String> webXpaths;
    private Map<String, String> mobileXpaths;
    private Map<String, Long> timestamps;
    private Map<String, List<String>> commonStepsMap;

    public TxAIService() throws IOException, Exception{
 
        try
        {
            this.webXpaths = loadWebXPaths(WEB_XPATHS_FILE_PATH);
            this.mobileXpaths = loadMobileXPaths(MOBILE_XPATHS_FILE_PATH);
            this.timestamps = loadTimestamps(TIMESTAMP_FILE_PATH);
            this.commonStepsMap = new HashMap<>();
 
        }
        catch (Exception e) {
            System.err.println("Error during TxAIService initialization: " + e.getMessage());
            throw e;
        }
    }
    
    private Map<String, String> loadWebXPaths(String filePath) throws IOException {
        Map<String, String> xpaths = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                xpaths.put(parts[0].trim(), parts[1].trim());
            }
        }
        return xpaths;
    }

    private Map<String, String> loadMobileXPaths(String filePath) throws IOException {
        Map<String, String> xpaths = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                xpaths.put(parts[0].trim(), parts[1].trim());
            }
        }
        return xpaths;
    }
   
    private Map<String, Long> loadTimestamps(String filePath) throws IOException {
        Map<String, Long> timestamps = new HashMap<>();
        if (Files.exists(Paths.get(filePath))) {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    timestamps.put(parts[0].trim(), Long.parseLong(parts[1].trim()));
                }
            }
        }
        return timestamps;
    }
 
    private void saveTimestamps(String filePath, Map<String, Long> timestamps) throws IOException {
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, Long> entry : timestamps.entrySet()) {
            content.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        Files.writeString(Paths.get(filePath), content.toString());
    }

    private void processFeatureFile(Path featureFile, String subDir, String projectName) throws IOException, Exception, InterruptedException {
        String baseFileName = featureFile.getFileName().toString().replaceAll("^\\d+_", "").replace(".feature", "");
        String featureContent = Files.readString(featureFile);
        System.out.println("\nProcessing Feature File Content:");
        System.out.println(featureContent);
        String featureFileName = featureFile.getFileName().toString();
 
        List<String> commonSteps = commonStepsMap.get(projectName);
        List<String> uniqueSteps = getUniqueSteps(featureContent, commonSteps);
 
        if (featureContent.contains("@Webtests")) {
            generateWebCode(featureContent, featureFileName, baseFileName, subDir, projectName);
        } else if (featureContent.contains("@APItests")) {
            generateAPICode(featureContent, featureFileName, baseFileName, subDir, projectName);
        }
        else if (featureContent.contains("@Mobiletests")) {
            generateMobileCode(featureContent, featureFileName, baseFileName, subDir, projectName);
        } else {
            System.out.println("No recognized tag found in feature file: " + featureFileName);
        }
 
        timestamps.put(featureFile.toString(), Files.getLastModifiedTime(featureFile).toMillis());
    }
 
    private void processCommonFeature(Path commonFeature, String subDir, String projectName) throws IOException, Exception, InterruptedException {
        System.out.println("Processing Common.feature for " + projectName);
        String featureContent = Files.readString(commonFeature);
        System.out.println("\nProcessing Common Feature File Content:");
        System.out.println(featureContent);
        List<String> commonSteps = loadCommonSteps(commonFeature);
        commonStepsMap.put(projectName, commonSteps);
 
        String baseFileName = "Common";
        String featureFileName = commonFeature.getFileName().toString();
 
        if (subDir.equals("Web")) {
            generateWebCode(featureContent, featureFileName, baseFileName, subDir, projectName);
        } 
        else if (subDir.equals("API")) {
            generateAPICode(featureContent, featureFileName, baseFileName, subDir, projectName);
        } 
        else if (subDir.equals("Mobile")) {
            generateMobileCode(featureContent, featureFileName, baseFileName, subDir, projectName);
        } 
        else {
            System.out.println("No recognized tag found in feature file: " + featureFileName);
        }
 
        timestamps.put(commonFeature.toString(), Files.getLastModifiedTime(commonFeature).toMillis());
    }
 
   
    public void processFeatureFiles() throws IOException, InterruptedException, Exception {
        for (String subDir : SUBDIRECTORIES) {
            Path subDirPath = Paths.get(PARENT_DIR_PATH, subDir);
            List<Path> projectDirs = Files.walk(subDirPath)
                .filter(Files::isDirectory)
                .filter(path -> !path.equals(subDirPath))
                .collect(Collectors.toList());
 
            for (Path projectDir : projectDirs) {
                String projectName = projectDir.getFileName().toString();
                try {
                    processProjectDirectory(projectDir, subDir, projectName);
                } catch (Exception e) {
                    System.err.println("Error processing project directory " + projectName + ": " + e.getMessage());
                    throw e;
                }
            }
        }
        saveTimestamps(TIMESTAMP_FILE_PATH, timestamps);
 
    }
 
    private void processProjectDirectory(Path projectDir, String subDir, String projectName) throws IOException, Exception, InterruptedException {
        List<Path> paths = Files.walk(projectDir)
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".feature"))
            .collect(Collectors.toList());
 
        // Process Common.feature first
        Optional<Path> commonFeatureOpt = paths.stream()
            .filter(p -> p.getFileName().toString().equals("Common.feature"))
            .findFirst();
 
        if (commonFeatureOpt.isPresent()) {
            Path commonFeature = commonFeatureOpt.get();
            processCommonFeature(commonFeature, subDir, projectName);
        } else {
            System.out.println("No Common.feature found in " + projectName);
            commonStepsMap.put(projectName, new ArrayList<>());
        }
 
        // Process other feature files
        for (Path featureFile : paths) {
            if (!featureFile.getFileName().toString().equals("Common.feature")) {
                if (hasUniqueSteps(featureFile, projectName)) {
                    processFeatureFile(featureFile, subDir, projectName);
                }
            }
        }
    }
 
    private List<String> loadCommonSteps(Path commonFeatureFile) throws IOException {
        List<String> commonSteps = new ArrayList<>();
        List<String> lines = Files.readAllLines(commonFeatureFile);
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("Given ") || line.startsWith("When ") || line.startsWith("Then ") || line.startsWith("And ")) {
                commonSteps.add(line);
 
            }
        }
        return commonSteps;
    }
 
    private boolean hasUniqueSteps(Path featureFile, String subDir) throws IOException {
        List<String> commonSteps = commonStepsMap.get(subDir);
        String featureContent = Files.readString(featureFile);
        List<String> uniqueSteps = getUniqueSteps(featureContent, commonSteps);
        return !uniqueSteps.isEmpty();
    }
 
    private List<String> getUniqueSteps(String featureContent, List<String> commonSteps) {
        List<String> uniqueSteps = new ArrayList<>();
        for (String line : featureContent.split("\n")) {
            line = line.trim();
            if ((line.startsWith("Given ") || line.startsWith("When ") || line.startsWith("Then ") || line.startsWith("And "))
                    && !commonSteps.contains(line)) {
                uniqueSteps.add(line);
            }
        }
        return uniqueSteps;
    }
   
    private Map<String, String> loadWebMapping() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(WEB_MAPPING_PATH), new TypeReference<Map<String, String>>() {
        });
    }

    private Map<String, String> loadAPIMapping() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(API_MAPPING_PATH), new TypeReference<Map<String, String>>() {
        });
    }
 
    private Map<String, String> loadMobileMapping() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(MOBILE_MAPPING_PATH), new TypeReference<Map<String, String>>() {
        });
    }

    private void generateWebCode(String featureContent, String featureFileName, String baseName, String subDir, String projectName) throws IOException, Exception, InterruptedException {
        String prompt = constructWebPrompt(featureContent, featureFileName, baseName, subDir, projectName);
        String response = callTxGPTAPI(prompt);
        processOpenAIResponse(response, baseName, "Web", projectName);
    }

    private void generateAPICode(String featureContent, String featureFileName, String baseName, String subDir, String projectName) throws IOException, Exception, InterruptedException {
        String prompt = constructAPIPrompt(featureContent, featureFileName, baseName, subDir, projectName);
        String response = callTxGPTAPI(prompt);
        processOpenAIResponse(response, baseName, "API", projectName);
 
    }
 
    private void generateMobileCode(String featureContent, String featureFileName, String baseName, String subDir, String projectName) throws IOException, Exception, InterruptedException {
        String prompt = constructMobilePrompt(featureContent, featureFileName, baseName, subDir, projectName);
        String response = callTxGPTAPI(prompt);
        processOpenAIResponse(response, baseName, "Mobile", projectName);
 
    }

    private String constructWebPrompt(String featureContent, String featureFileName, String baseName, String subDir, String projectName) throws IOException{
 
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> WebMappings = loadWebMapping();
        StringBuilder locatorsStringBuilder = new StringBuilder();
        locatorsStringBuilder.append("Here are the predefined locators (xpaths) you should use:\n");
        for (Map.Entry<String, String> entry : webXpaths.entrySet()) {
            locatorsStringBuilder.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }
 
        List<String> stepsToProcess;
        String promptContent;
        if (featureFileName.equals("Common.feature")) {
            stepsToProcess =  commonStepsMap.get(projectName);
            promptContent = "Generate step definitions for ALL steps in the Common.feature file.\n"
                    + "Here are all the steps in Common.feature:\n"
                    + String.join("\n", stepsToProcess);
        } else {
            stepsToProcess = getUniqueSteps(featureContent, commonStepsMap.get(projectName));
            promptContent = "Generate step definitions ONLY for the following unique steps:\n"
                    + String.join("\n", stepsToProcess)
                    + "\nDO NOT generate code for any other steps that might be in the feature file content.";
        }
 
        if (stepsToProcess.isEmpty()) {
            System.out.println("No steps to process for file: " + featureFileName);
        }
 
        String prompt =
                    "You are given a .feature file written in Cucumber. Your task is to generate three Java files based on the given feature file content, which implement the step definitions using Java:\n"
                        + "Please ensure the following:\n"
                        + "1. The generated Java code should be syntactically correct and free of errors.\n"
                        + "2. Import the necessary Cucumber libraries and any other libraries required for the steps.\n"
                        + "3. We are using Selenium version 4.0.0 and above, so do not include ChromeDriver or WebDriver. Import only the required libraries.\n\n"
                        + "Name the generated files according to the feature file name, but ignore any digits followed by an underscore at the beginning. For example, if the feature file is named '861_Report.feature', the generated files will be:\n"
                        + "   - ReportPage.java: Contains locators (xpaths)\n"
                        + "   - Report.java: Contains functions related to the feature file\n"
                        + "   - ReportStepDef.java: Contains step definitions of the feature file\n"
                        + "Locators:\n"
                        + "1. Create locators using the 'By' class and include all necessary Selenium imports in the locators file."
                        + "XPath Requirements:\n"
                        + "- MANDATORY: Include locators for `Email` and `Password` fields using these exact names:\n"
                        + "- Extract ALL XPaths from WebXpaths.java that correspond to elements in your feature file .\n"
                        + "- Each element mentioned in your feature file MUST have a matching locator.\n"
                        + "- Do not include unrelated XPaths from other Pages files.\n"
                        + "Functions:\n"
                        + "1. Add necessary functions with their content in function definitions for each step in the feature file.\n"
                        + "2. Ensure all functions are included according to locators and properly defined.\n"
                        + "3. In the step definitions, replace any text enclosed in double quotes (e.g., \"SignUpNow\") with `{string}` to create a parameterized step."
                        + "4. Use KeywordUtil class for 'click' and 'inputText' functions for entering credentials as email, password, and otp as 'KeywordUtil.inputText()'.\n"
                        + "5. For **verification, it must ensure to use **`KeywordUtil.verifyDisplayAndEnable()` instead of `KeywordUtil.verify()`"
                        + "6. \n\nKeywordUtil Mapping:\n" + objectMapper.writeValueAsString(WebMappings)
                        + "7. Use the KeywordUtil methods as defined in the mapping above. Apply these methods consistently based on the actions in each step of the feature file."
                        + "8. Ensure to implement function definitions in try-catch blocks, with the catch block detecting errors.\n"
                        + "9. Use KeywordUtil functionality as KeywordUtil.inputText(InvoicePage.email_input, email, \"Entering email id\"); where required.\n"
                        + "10. Ensure every line in the feature file content is reflected in the generated code.\n"
                        + "Imports Packages and Dependencies:\n"
                        + "1. Import package 'package Pages." + subDir + "." + projectName + ";" + "' in locators files (e.g., " + baseName + "Page.java).\n"
                        + "2. Import package 'package modules." + subDir + "." + projectName + ";" + "' in function definition files (e.g., " + baseName + ".java).\n"
                        + "3. Import package 'package step_definitions." + subDir + "." + projectName + ";" + "' in step definition files (e.g., " + baseName + "StepDef.java).\n"
                        + "4. In the function definition files (e.g., " + baseName + ".java), import the relevant page class as 'import Pages." + subDir + "." + projectName + "." + baseName + "Page;'.\n"
                        + "5. In the step definition files (e.g., " + baseName + "StepDef.java), import the corresponding module class as 'import modules." + subDir + "." + projectName + "." + baseName + ";'.\n"
                        + "6. Import `utilities.KeywordUtil` instead of `utils.KeywordUtil`.\n\n"
                        + "7. **Do not** create constructors for any of the classes. **Do not** use `this.driver = driver`.\n"
                        + "8. **Access functions by creating an instance of the class.** For example, use `Report report = new Report();` to access the functions defined within the `Report` class.\n"
                        + "9. \n\nImportant: **Import `io.cucumber.java.en.*` in all step definition files. This is **mandatory** and must be present in every generated step definition file.**\n\n"
                        + "\n\nImportant: Use the following base name for generating the Java files: `"
                        + "\nFeature File name is: " + featureFileName + "`\n"
                        + "Generate these exact file names and content in this order:\n"
                        + "1. " + baseName + "Page.java\n"
                        + "2. " + baseName + ".java\n"
                        + "3. " + baseName + "StepDef.java\n"
                        + "Ensure that each file's content is wrapped in ```java and ``` tags, and that the file name appears immediately before the opening ```java tag."
                        + "prompt content is: " + promptContent + "\n\n"
                        + "Locators:\n" + locatorsStringBuilder.toString() + "\n\n"
                        + "\n\nPlease ensure that you use only the provided locators (xpaths) strictly according to the feature file content and do not generate any new xpaths. They must follow the order according to the feature file.\n\n"
                        + "Ensure that any step do not miss from feature file content.\n\n"
                        + "Do not add any steps that are not explicitly mentioned in the provided feature file content.\n\n"
                        + "\nPlease ensure that you generate code ONLY for the steps provided in the prompt content above, and do not add any additional steps.\n\n"
                        + "Feature File Content (for reference only, do not use steps from here unless they match the ones in the prompt content):\n"
                        + "\nFeature File Content:\n" + featureContent
                        + "Important: **Do not skip any steps** from the feature file. All steps must be reflected in the generated code.\n";
        return prompt;
    }

    private String constructAPIPrompt(String featureContent, String featureFileName, String baseName, String subDir, String projectName) throws IOException{
 
        List<String> stepsToProcess;
        String promptContent;
        if (featureFileName.equals("Common.feature")) {
            stepsToProcess =  commonStepsMap.get(projectName);
            promptContent = """
                            Generate step definitions for ALL steps in the Common.feature file.
                            Here are all the steps in Common.feature:
                            """
                    + String.join("\n", stepsToProcess);
        } else {
            stepsToProcess = getUniqueSteps(featureContent, commonStepsMap.get(projectName));
            promptContent = "Generate step definitions ONLY for the following unique steps:\n"
                    + String.join("\n", stepsToProcess)
                    + "\nDO NOT generate code for any other steps that might be in the feature file content.";
        }
 
        if (stepsToProcess.isEmpty()) {
            System.out.println("No steps to process for file: " + featureFileName);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> APIMappings = loadAPIMapping();
   
        String prompt =
            """
            You are given a .feature file written in Cucumber. Your task is to generate one Java file based on the given feature file content, which implements the step definitions using Java:
            Please ensure the following:
            1. The generated Java code should be syntactically correct and free of errors.
            2. Import the necessary Cucumber libraries and any other libraries required for the steps.
            3. We are using Selenium version 4.0.0 and above, so do not include ChromeDriver or WebDriver. Import only the required libraries.
            4. Manage authentication by adding access tokens as headers in HTTP requests where required. Ensure the handling of headers is secure and efficient.
            Name the generated files according to the feature file name, but ignore any digits followed by an underscore at the beginning. For example, if the feature file is named '861_Report.feature', the generated files will be:
            - ReportStepDef.java: Contains step definitions of the feature file
            Functions:
            1. Add necessary functions with their content in function definitions for each step in the feature file.
            2. Ensure all functions are included and properly defined.
            3. Include HTTP header management to attach access tokens where necessary.
            4. Instruction for Handling Duplicate Steps: For steps that are functionally identical but differ only in specific values (e.g., different base URIs or access tokens), **implement these steps using a single function**. This function should accept parameters as needed to accommodate variations between steps.
            5. For all StepDef classes except CommonStepDef, strictly declare the accessToken variable as follows:
            `public static String accessToken = ConfigReader.getValue("accessToken");`
            `private static String userId;`
            `private String generatedEmail;`
            6. **JSON Response Handling:**
                When extracting values from JSON responses:
                - Declare the variable as a String
                - Cast the returned value to String
                - Use this format:
                ```
                   String actualValue = String.valueOf(RestUtils.getValueFromJson(response, jsonPath));
                    if (expectedValue.equals("<stored_id>")) {
                        expectedValue = String.valueOf(userId);
                    }
                    if (jsonPath.equals("email")) {
                        expectedValue = generatedEmail; // Ensure we're validating against the correct email
                    }
 
                    System.out.println("Verifying JSON path: " + jsonPath + ", Expected: " + expectedValue + ", Actual: " + actualValue);
                    assertEquals("Validation failed for " + jsonPath, expectedValue, actualValue);  
                ```      
            7. **Use the `generatedEmail` in payload while sending 'POST' and 'PUT' request.
            8. **After calling the `generateDynamicEmail()`, call this `RestUtils.storeValue(emailVariable, generatedEmail); // Store for reuse` for futher use and print it.
            9. **After sending the respone to `POST request`, extract the User ID from response  and after this set it as: `RestUtils.setNewUserId(userId);  // Store User ID globally`
            10. **For `GET` and `DELETE` take `payload as empty string ""`.
            Important instructions:
            1. Use ONLY ConfigReader.getValue("accessToken") to set the value.
            2. DO NOT use any hardcoded values or values from feature files.
            3. Ensure this exact declaration is present in each StepDef class.
            4. DO NOT include this declaration in CommonStepDef.
            5. DO NOT modify this declaration in any way.
            6. **Strictly Ensure to use `ContentType.JSON` from `io.restassured.http.ContentType` instead of `application/json` in RestUtils predefined functions and access Token is mandatory.
            7. **Additional Instruction:** Ensure that similar steps such as checking for a status code (e.g., `Then I will receive response having status code 201`) are implemented **as a single reusable function** across all scenarios, rather than separate functions for each occurrence.
            8. For steps that involve verifying JSON path values in the response:
                1. Create a function annotated with @Then that takes two String parameters: one for the JSON path and one for the expected value.
                2. Inside the function, use the getValueFromJson method to extract the actual value from the response.
                3. Compare the extracted value with the expected value using an appropriate assertion method.
                4. Use the static 'response' variable that's already declared in the class.
                6. Ensure this function is reusable for all JSON path verification steps across different scenarios and it's datatype is strictly Object.
                7. Import necessary assertion and logging classes.
                8. Do not hardcode any values; use the parameters passed to the function.
            Mappings: """ + objectMapper.writeValueAsString(APIMappings) + "\n"
            + "1. Use the Mappings methods as defined in the mapping above. Apply these methods consistently based on the actions in each step of the feature file.\n"
            + "2. Ensure every line in the feature file content is reflected in the generated code.\n"
            + "3. For any instance variable declarations of type 'Response', ensure they are declared as 'public static'. This should apply universally across all generated Java files.\n"
            + "4. Any method calls that result in a 'Response' should be made as static method invocations without storing the result in a variable. Ensure this practice is applied to all method calls involving 'Response' in the generated code, making them direct method calls.\n"
            + "5. For every Step Definition file create a `Response response` variable that is declared as `public static` from library `io.restassured.response.Response`\n"
            + "6. Don't use logstep or description if not defined in functions in `Mappings`."
            + "Imports Packages and Dependencies:\n"
            + "1. Import package 'package step_definitions." + subDir + "." + projectName + ";" + "' in step definition files (e.g., " + baseName + "StepDef.java).\n"
            + "2. Import `restutil.RestUtils` instead of `utilities.KeywordUtil`.\n"
            + "3. Import **utilities.ConfigReader; in all step definition files except `CommonStepDef`.\n"
            + "3. Import **`import io.restassured.http.ContentType;`and **`import static org.junit.Assert.assertEquals;` in all step definition files except `CommonStepDef`."
            + "5. In the step definition files (e.g., ReportStepDef.java), import the corresponding functions defined in restutil.RestUtils.java\n"
            + "6. **Do not** create constructors for any of the classes. **Do not** use `this.driver = driver`.\n"
            + "7. **Access functions by creating an instance of the class.** For example, use `Report report = new Report();` to access the functions defined within the `Report` class.\n"
            + "8. Import **`io.cucumber.java.en.*` in all step definition files.\n"
            + "9. Import **`org.json.JSONObject` for parsing and formatting JSON strings except `CommonStepDef`.\n"
            + "10. Import io.cucumber.java.en.* in all step definition files.\n"
            + "11. Implement secure handling of access tokens within the HTTP header for steps that require API interaction.\n"
            + "In your implementation, modify specific sections:\n"
            + "- Use `dataTable.asMaps().forEach(row -> payload.put(row.get(\"key\"), row.get(\"value\").replace(\"\\\"\", \"\")));` to correctly handle string values by removing any encapsulated double quotes.\n"
            + "\n\nImportant: Use the following base name for generating the Java files: `" + featureFileName + "`\n"
            + "Generate these exact file names and content in this order:\n"
            + "1. " + baseName + "StepDef.java\n"
            + "Ensure that each file's content is wrapped in ```java and ``` tags, and that the file name appears immediately before the opening ```java tag.\n\n"
            + "prompt content is: " + promptContent + "\n\n"
            + "Instructions for using Mapping functions:\n"
            + "1. Use the exact functions from the Mappings for all HTTP methods (GET, POST, PUT, DELETE, PATCH).\n"
            + "2. Store all API call results in the `response` variable.\n"
            + "3. When a step mentions an access token, use the function version with the token parameter. Example:\n"
            + "   response = RestUtils.PostResponse(payload, \"ACTUAL_ACCESS_TOKEN\");\n"
            + "4. Replace \"ACTUAL_ACCESS_TOKEN\" with the token value from the feature file step.\n"
            + "5. For steps without an access token, use the function version without the token parameter.\n"
            + "6. Pass payload as a string (use payload.toString() for JSONObjects).\n"
            + "7. Always include a descriptive string as the last parameter.\n"
            + "8. Do not add or modify any parameters in the Mapping functions.\n"
            + "9. Implement similar steps (e.g., status code checks) using a single reusable function.\n"
            + "10. The access token should always be a separate parameter, not part of the payload.\n"    
            + "\n\nFeature File name is: " + featureFileName + "\n\n"
            + "Ensure that any step do not miss from feature file content.\n\n"
            + "Do not add any steps that are not explicitly mentioned in the provided feature file content.\n\n"
            + "\n\nFeature File Content:\n" + featureContent + "\n\n"
            + "\n\nPlease ensure that you generate code ONLY for the steps provided in the prompt content above, and do not add any additional steps.\n\n"
            + "Feature File Content (for reference only, do not use steps from here unless they match the ones in the prompt content):\n"
            + featureContent;
 
        return prompt;
    }
 
    private String constructMobilePrompt(String featureContent, String featureFileName, String baseName, String subDir, String projectName) throws IOException{
 
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> MobileMappings = loadMobileMapping();
        StringBuilder locatorsStringBuilder = new StringBuilder();
        locatorsStringBuilder.append("Here are the predefined locators (xpaths) you should use:\n");
        for (Map.Entry<String, String> entry : mobileXpaths.entrySet()) {
            locatorsStringBuilder.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }
 
        List<String> stepsToProcess;
        String promptContent;
        if (featureFileName.equals("Common.feature")) {
            stepsToProcess =  commonStepsMap.get(projectName);
            promptContent = "Generate step definitions for ALL steps in the Common.feature file.\n"
                    + "Here are all the steps in Common.feature:\n"
                    + String.join("\n", stepsToProcess);
        } else {
            stepsToProcess = getUniqueSteps(featureContent, commonStepsMap.get(projectName));
            promptContent = "Generate step definitions ONLY for the following unique steps:\n"
                    + String.join("\n", stepsToProcess)
                    + "\nDO NOT generate code for any other steps that might be in the feature file content.";
        }
 
        if (stepsToProcess.isEmpty()) {
            System.out.println("No steps to process for file: " + featureFileName);
        }
 
        String prompt =
                    "You are given a .feature file written in Cucumber. Your task is to generate three Java files based on the given feature file content, which implement the step definitions using Java:\n"
                        + "Please ensure the following:\n"
                        + "1. The generated Java code should be syntactically correct and free of errors.\n"
                        + "2. Import the necessary Cucumber libraries and any other libraries required for the steps.\n"
                        + "3. We are using Selenium version 4.0.0 and above, so do not include ChromeDriver or WebDriver. Import only the required libraries.\n\n"
                        + "Name the generated files according to the feature file name, but ignore any digits followed by an underscore at the beginning. For example, if the feature file is named '861_Report.feature', the generated files will be:\n"
                        + "   - ReportPage.java: Contains locators (xpaths)\n"
                        + "   - Report.java: Contains functions related to the feature file\n"
                        + "   - ReportStepDef.java: Contains step definitions of the feature file\n"
                        + "Locators:\n"
                        + "1. Use **only the xpaths required for the feature file**. Ensure these xpaths are strictly pulled from 'MobXpaths.java' and avoid loading any unrelated xpaths from other Pages files.\n"
                        + "2. Ensure **all** locators mentioned in the feature file are included.\n\n"
                        + "Functions:\n"
                        + "1. Add necessary functions with their content in function definitions for each step in the feature file.\n"
                        + "2. Ensure all functions are included according to locators and properly defined.\n"
                        + "3. In the step definitions, replace any text enclosed in double quotes (e.g., \"SignUpNow\") with `{string}` to create a parameterized step."
                        + "5. \n\nMobileMapping:\n" + objectMapper.writeValueAsString(MobileMappings)
                        + "6. Use the `MobileKeywords2` methods as defined in the mapping above. Apply these methods consistently based on the actions in each step of the feature file."
                        + "7. **Use `MobileKeywords2.writeInInput()`instead of `MobileKeywords2.input()`.\n"
                        + "8  Ensure to implement function definitions in try-catch blocks, with the catch block detecting errors.\n"
                        + "9. Ensure every line in the feature file content is reflected in the generated code.\n"
                        + "Imports Packages and Dependencies:\n"
                        + "1. Import package 'package Pages." + subDir + "." + projectName + ";" + "' in locators files (e.g., " + baseName + "Page.java).\n"
                        + "2. Import package 'package modules." + subDir + "." + projectName + ";" + "' in function definition files (e.g., " + baseName + ".java).\n"
                        + "3. Import package 'package step_definitions." + subDir + "." + projectName + ";" + "' in step definition files (e.g., " + baseName + "StepDef.java).\n"
                        + "4. In the function definition files (e.g., " + baseName + ".java), import the relevant page class as 'import Pages." + subDir + "." + projectName + "." + baseName + "Page;'.\n"
                        + "5. In the step definition files (e.g., " + baseName + "StepDef.java), import the corresponding module class as 'import modules." + subDir + "." + projectName + "." + baseName + ";'.\n"
                        + "6. It is mandatory to include the following import statements wherever necessary:\n"
                        + "   - 'import utilities.DriverUtil;'\n"
                        + "   - 'import mobileutil.MobileKeywords2;'\n"
                        + "7. Specifically, **DriverUtil must be imported in any file that includes app launch functionality.\n"
                        + "8. **Do not** create constructors for any of the classes. **Do not** use `this.driver = driver`.\n"
                        + "9. **Access functions by creating an instance of the class.** For example, use `Report report = new Report();` to access the functions defined within the `Report` class.\n"
                        + "10. Import io.cucumber.java.en.* in all step definition files.\n\n"
                        + "\n\nImportant: Use the following base name for generating the Java files: `"
                        + "\nFeature File name is: " + featureFileName + "`\n"
                        + "Generate these exact file names and content in this order:\n"
                        + "1. " + baseName + "Page.java\n"
                        + "2. " + baseName + ".java\n"
                        + "3. " + baseName + "StepDef.java\n"
                        + "Ensure that each file's content is wrapped in ```java and ``` tags, and that the file name appears immediately before the opening ```java tag."
                        + "prompt content is: " + promptContent + "\n\n"
                        + "Locators:\n" + locatorsStringBuilder.toString() + "\n\n"
                        + "\n\nPlease ensure that you use only the provided locators (xpaths) strictly according to the feature file content and do not generate any new xpaths. They must follow the order according to the feature file.\n\n"
                        + "Ensure that any step do not miss from feature file content.\n\n"
                        + "Do not add any steps that are not explicitly mentioned in the provided feature file content.\n\n"
                        + "\nPlease ensure that you generate code ONLY for the steps provided in the prompt content above, and do not add any additional steps.\n\n"
                        + "Feature File Content (for reference only, do not use steps from here unless they match the ones in the prompt content):\n"
                        + "\nFeature File Content:\n" + featureContent
                        + "Important: **Do not skip any steps** from the feature file. All steps must be reflected in the generated code.\n";
        return prompt;
    }

    public String callTxGPTAPI(String prompt) throws Exception {
 
        if (prompt == null || prompt.length() < 10) {
            throw new IllegalArgumentException("Prompt must be at least 10 characters long.");
        }
        if (BASE_URL == null || BASE_URL.isEmpty()) {
            throw new IllegalArgumentException("Base URL cannot be null or empty.");
        }
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty.");
        }
   
        String apiEndpoint = BASE_URL;
        if (!apiEndpoint.endsWith("/chat/completions")) {
            if (!apiEndpoint.endsWith("/")) {
                apiEndpoint += "/";
            }
            apiEndpoint += "chat/completions";
        }
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(apiEndpoint);
        request.setHeader("Authorization", "Bearer " + API_KEY);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("api-key", API_KEY);
        
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o");
        JSONArray messagesArray = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant that generates Java code for Selenium automation testing.");
        messagesArray.put(systemMessage);
        
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messagesArray.put(userMessage);
        
        requestBody.put("messages", messagesArray);
        requestBody.put("max_tokens", 8192);
        requestBody.put("temperature", 0.7);
        requestBody.put("stream", false);
        requestBody.put("modelType", "openai");
        
        StringEntity entity = new StringEntity(requestBody.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        
        System.out.println("DEBUG - Sending request...");
        HttpResponse response = httpClient.execute(request);
   
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        // System.out.println("DEBUG - Response Status Code: " + statusCode);
   
        switch (statusCode) {
            case 200:
                JSONObject jsonResponse = new JSONObject(responseBody);     
                if (jsonResponse.has("choices") && jsonResponse.getJSONArray("choices").length() > 0) {
                    JSONObject choice = jsonResponse.getJSONArray("choices").getJSONObject(0);
                    String content = choice.getJSONObject("message").getString("content");
                    // System.out.println("====================================================");
                    System.out.println("The content is:");
                    System.out.println(content);
                
                    return content;
                }
                
            case 400:
                System.err.println("ERROR - Bad Request: " + responseBody);
                throw new RuntimeException("Bad Request: " + responseBody);
                
            case 401:
                System.err.println("ERROR - Unauthorized: " + responseBody);
                throw new RuntimeException("Unauthorized: " + responseBody);
                
            case 404:
                System.err.println("ERROR - Endpoint Not Found (404): The API endpoint at " + apiEndpoint + " was not found.");
                System.err.println("Try these troubleshooting steps:");
                System.err.println("1. Check if the BASE_URL in your configuration is correct: " + BASE_URL);
                System.err.println("2. Try with endpoint '/completions' instead of '/chat/completions'");
                throw new RuntimeException("Endpoint Not Found: Please check if " + apiEndpoint + " is the correct endpoint.");
                
            case 422:
                System.err.println("ERROR - Unprocessable Entity (422): The request format is incorrect: " + responseBody);
                System.err.println("Try these troubleshooting steps:");
                System.err.println("1. Check that your request payload matches the API specification");
                System.err.println("2. Verify that you're providing all required fields");
                System.err.println("3. Check the authentication method (Bearer token vs API key)");
                throw new RuntimeException("Unprocessable Entity: " + responseBody);
                
            case 500:
                System.err.println("ERROR - Internal Server Error: " + responseBody);
                throw new RuntimeException("Internal Server Error: " + responseBody);
                
            default:
                System.err.println("ERROR - Unexpected status code: " + statusCode);
                throw new RuntimeException("Unexpected status code: " + statusCode + ", Response: " + responseBody);
        }
    }
    private void processOpenAIResponse(String content, String baseName, String subDir, String projectName) throws IOException {
        content = content.replaceAll("```java", "```");
        String[] javaCodes = content.split("```");
       
        if (javaCodes.length < 2) {
            System.out.println("Error: Response format is incorrect. Not enough code blocks.");
            return;
        }
       
        if (subDir.equals("Web") || subDir.equals("Mobile")) {
            if (javaCodes.length < 6) {
                System.out.println("Error: Response format is incorrect for Web. Expected at least 3 code blocks.");
                return;
            }
            writeJavaCodeToFile(baseName + "Page.java", javaCodes[1], "Pages", subDir + "/" + projectName);
            writeJavaCodeToFile(baseName + ".java", javaCodes[3], "modules", subDir + "/" + projectName);
            writeJavaCodeToFile(baseName + "StepDef.java", javaCodes[5], "step_definitions", subDir + "/" + projectName);
        } else if (subDir.equals("API")) {
            writeJavaCodeToFile(baseName + "StepDef.java", javaCodes[1], "step_definitions", subDir + "/" + projectName);
        } else {
            System.out.println("Error: Unknown subDir: " + subDir);
        }
    }
 
 
    private void writeJavaCodeToFile(String fileName, String javaCode, String folder, String subFolder) throws IOException {
        Path outputPath = Paths.get(OUTPUT_DIR_BASE_PATH, folder, subFolder, fileName);
        Files.createDirectories(outputPath.getParent());
        Files.writeString(outputPath, javaCode);
        System.out.println("Java code written to " + outputPath);
    }
 
    public static void main(String[] args) {
        try {
            TxAIService obj = new TxAIService();
            obj.processFeatureFiles();
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
   
 
 
 
}
