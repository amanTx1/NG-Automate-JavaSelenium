package com.APIUtil;

import java.io.File;
import java.util.Base64;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import utilities.GlobalUtil;
import utilities.LogUtil;

/**
 * The Class RequestUtil.
 */
public class RequestUtil {

	/**
	 * Post request.
	 *
	 * @param url
	 *        the url
	 * @param params
	 *        the params
	 * 
	 * @return the closeable http response
	 * 
	 * @throws Exception
	 *         the exception
	 */
	@SuppressWarnings("deprecation")
	public static CloseableHttpResponse postRequest(String url, StringEntity params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost postRequest = new HttpPost(url);
		postRequest.addHeader("content-type", "application/json");

		String encoding = Base64.getEncoder()
				.encodeToString((GlobalUtil.getCommonSettings().getbugToolUserName() + ":" + GlobalUtil.getCommonSettings().getbugToolPassword()).getBytes());
		postRequest.setHeader("Authorization", "Basic " + encoding);
		postRequest.setEntity(params);

		CloseableHttpResponse response = httpclient.execute(postRequest);

		return response;
	}

	/**
	 * Post request to attach image with defect created.
	 *
	 * @param url
	 *        the url
	 * @param issueId
	 *        the issue id
	 * @param filePath
	 *        the file path
	 * 
	 * @throws Exception
	 *         the exception
	 */
	@SuppressWarnings("deprecation")
	public static void postRequestToAttachImageWithDefectCreated(String url, String issueId, String filePath) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost postRequest = new HttpPost(url);
		String encoding = Base64.getEncoder()
				.encodeToString((GlobalUtil.getCommonSettings().getbugToolUserName() + ":" + GlobalUtil.getCommonSettings().getbugToolPassword()).getBytes());
		postRequest.setHeader("Authorization", "Basic " + encoding);
		postRequest.setHeader("X-Atlassian-Token", "nocheck");

		MultipartEntityBuilder entity = MultipartEntityBuilder.create();
		entity.addPart("file", new FileBody(new File(filePath)));
		postRequest.setEntity(entity.build());
		CloseableHttpResponse response = httpclient.execute(postRequest);

		if (response.getCode() == 200) {
			LogUtil.infoLog(RequestUtil.class, "Attachment uploaded");
		} else {
			LogUtil.errorLog(RequestUtil.class, "Attachment not uploaded, API response code is: " + response.getCode());
		}
	}
}
