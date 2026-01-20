package com.APIUtil;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class ResponseUtil {

	/**
	 * Gets the response body.
	 *
	 * @param response
	 *        the response
	 * 
	 * @return the response body
	 */
	public static String getResponseBody(CloseableHttpResponse response) {
		String responseBody = null;
		try {
			HttpEntity entity = response.getEntity();
			String jsonString = EntityUtils.toString(entity);
			responseBody = jsonString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	/**
	 * Gets the response code.
	 *
	 * @param response
	 *        the response
	 * 
	 * @return the response code
	 */
	public static int getResponseCode(CloseableHttpResponse response) {
		int responseCode = 0;
		try {
			responseCode = response.getCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseCode;
	}
}
