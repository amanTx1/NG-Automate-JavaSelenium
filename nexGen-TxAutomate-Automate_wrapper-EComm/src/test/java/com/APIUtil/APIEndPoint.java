package com.APIUtil;

public class APIEndPoint {

	public static String createIssueURI() {
		return "/rest/api/2/issue";
	}

	public static String attachImageToIssueURI(String issueId) {
		return "/rest/api/2/issue/" + issueId + "/attachments";
	}
}
