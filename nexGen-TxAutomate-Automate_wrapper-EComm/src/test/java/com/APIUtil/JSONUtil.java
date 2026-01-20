package com.APIUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {

	/**
	 * Gets the key value from JSON.
	 *
	 * @param jsonString
	 *        the json string
	 * @param keyName
	 *        the key name
	 * 
	 * @return the key value from JSON
	 */
	public static String getKeyValueFromJSON(String jsonString, String keyName) {
		String keyValue = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(jsonString);
			keyValue = (String) json.get(keyName);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return keyValue;
	}
}
