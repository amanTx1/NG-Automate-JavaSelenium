package com.APIUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utilities.CommonConstants;

/**
 * This Class is use to process Payload of API.
 */
public class PayloadUtil {

	/** The datamap. */
	public static HashMap<String, String> datamap = null;

	/**
	 * Convert java object to Json String.
	 *
	 * @param obj
	 *        the obj
	 * 
	 * @return the string
	 */
	public static String convertJavaObjectToJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * Convert Json file to String.
	 *
	 * @param jsonFileName
	 *        the json file name
	 * 
	 * @return the string
	 * 
	 * @throws JsonGenerationException
	 *         the json generation exception
	 * @throws JsonMappingException
	 *         the json mapping exception
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 */
	public static String convertFileToJsonString(String jsonFileName) throws JsonGenerationException, JsonMappingException, IOException {
		String jsonFilePath = CommonConstants.jsonFilePath + File.separator + jsonFileName;

		return FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);

	}

	/**
	 * Creates the issue payload.
	 *
	 * @param jsonFileName
	 *        the json file name
	 * @param projectName
	 *        the project name
	 * @param defectSummary
	 *        the defect summary
	 * @param issueDescription
	 *        the issue description
	 * @param issueType
	 *        the issue type
	 * @param label
	 *        the label
	 * @param defectEnvironment
	 *        the defect environment
	 * @param defectPriority
	 *        the defect priority
	 * 
	 * @return the string
	 * 
	 * @throws JSONException
	 *         the JSON exception
	 * @throws JsonGenerationException
	 *         the json generation exception
	 * @throws JsonMappingException
	 *         the json mapping exception
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 */
	public static String createIssuePayload(String jsonFileName, String projectName, String defectSummary, String issueDescription, String issueType, String label,
			String defectEnvironment, String defectPriority) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		JSONObject jObject = new JSONObject(convertFileToJsonString(jsonFileName));
		JSONObject fields = jObject.getJSONObject("fields");
		JSONObject project = fields.getJSONObject("project");

		project.put("key", projectName);

		fields.put("summary", defectSummary);

		fields.put("description", issueDescription);

		JSONObject issuetype = fields.getJSONObject("issuetype");
		issuetype.put("name", issueType);

		JSONArray labels = fields.getJSONArray("labels");
		labels.put(label);

		fields.put("environment", defectEnvironment);

		JSONObject priority = fields.getJSONObject("priority");
		priority.put("name", defectPriority);

		return jObject.toString();
	}

}
