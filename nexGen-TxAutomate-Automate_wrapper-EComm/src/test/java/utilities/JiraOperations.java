package utilities;

import java.io.IOException;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.APIUtil.APIEndPoint;
import com.APIUtil.JSONUtil;
import com.APIUtil.PayloadUtil;
import com.APIUtil.RequestUtil;
import com.APIUtil.ResponseUtil;

public class JiraOperations {

	/**
	 * Creates the jira issue.
	 *
	 * @param ProjectName
	 *        the project name
	 * @param issueSummary
	 *        the issue summary
	 * @param issueDescription
	 *        the issue description
	 * @param issueType
	 *        the issue type
	 * @param environment
	 *        the environment
	 * @param label
	 *        the label
	 * @param priority
	 *        the priority
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *         the exception
	 */
	public static String createJiraIssueAndGetIssueID(String ProjectName, String issueSummary, String issueDescription, String issueType, String environment, String label,
			String priority) throws Exception {

		String issueId = null;
		try {
			StringEntity params = new StringEntity(
					PayloadUtil.createIssuePayload("createIssue.json", ProjectName, issueSummary, issueDescription, issueType, label, environment, priority));
			String url = GlobalUtil.getCommonSettings().getbugToolHostName() + APIEndPoint.createIssueURI();
			String jsonString = ResponseUtil.getResponseBody(RequestUtil.postRequest(url, params));
			issueId = JSONUtil.getKeyValueFromJSON(jsonString, "key");
		} catch (Exception e) {
			e.printStackTrace();
		}

		LogUtil.htmlFailLog("Issue ID is: " + issueId);
		return issueId;

	}

	/**
	 * Creates the jira issue with attachment.
	 *
	 * @param issueId
	 *        the issue id
	 * @param filePath
	 *        the file path
	 * 
	 * @throws ClientProtocolException
	 *         the client protocol exception
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 */
	public static void createJiraIssueWithAttachment(String issueId, String filePath) throws ClientProtocolException, IOException {
		String url = GlobalUtil.getCommonSettings().getbugToolHostName() + APIEndPoint.attachImageToIssueURI(issueId);
		try {
			RequestUtil.postRequestToAttachImageWithDefectCreated(url, issueId, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
