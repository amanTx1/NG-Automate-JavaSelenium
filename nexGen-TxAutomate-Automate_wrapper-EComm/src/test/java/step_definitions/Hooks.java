package step_definitions;

import com.Buffer.BufferUtilMethodLevel;
import com.Buffer.BufferUtilSuiteLevel;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.response.Response;
//import mantisutil.MantisReport;
//import testlink.api.java.client.TestLinkAPIResults;
import utilities.*;

public class Hooks {

	String imagePath;
	String pathForLogger;
	public static String testCaseDescription;
	public static String executingTagName;

	public static String imagePath1;
	public static String concatt = ".";

	@Before("@Webtests")
	public void beforeMethodAmazon(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();

		BufferUtilMethodLevel.testCaseName = testCaseDescription;

		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());
		LogUtil.infoLog(getClass(), "Test Started with tag : " + scenario.getSourceTagNames());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog(Hooks.class, "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());
		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(), GlobalUtil.getCommonSettings().getBrowser()));
	}

	@Before("@SFAutomation")
	public void beforeMethodSFAutomation(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());
		LogUtil.infoLog(getClass(), "Test Started with tag : " + scenario.getSourceTagNames());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog(Hooks.class, "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());
		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(), GlobalUtil.getCommonSettings().getBrowser()));
	}

	@Before("@DOPS")
	public void beforeMethodDOPS(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog(Hooks.class, "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());
		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(), GlobalUtil.getCommonSettings().getBrowser()));
	}

	@Before("@SASelf")
	public void beforeMethodSASelf(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog(Hooks.class, "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());
		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(), GlobalUtil.getCommonSettings().getBrowser()));
	}

	@Before("@SkyNews")
	public void beforeMethodSkyNews(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog(Hooks.class, "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());
		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(), GlobalUtil.getCommonSettings().getBrowser()));
	}

	@Before("@APItests")
	public void beforeAPIMethod(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();

		BufferUtilMethodLevel.testCaseName = testCaseDescription;

		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "API Test Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is started using base URL: " + GlobalUtil.getCommonSettings().getRestURL());
	}

	@Before("@Mobiletests")
	public void beforeMobileTestMethod(Scenario scenario) throws Exception {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();

		BufferUtilMethodLevel.testCaseName = testCaseDescription;

		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[0]);
		//Device details need to be given and it is hardcoded
		GlobalUtil.setAppiumDriver(DriverUtil.invokeLocalMobileApp(GlobalUtil.getCommonSettings().getExecutionEnv(),GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[0]));

	}

	@Before("@MobileIosTest")
	public void beforeMobileIOSTestMethod(Scenario scenario) throws Exception {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();

		BufferUtilMethodLevel.testCaseName = testCaseDescription;

		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS:ios" );
		String iosDeviceName= IOSDeviceUtils.getIOSDeviceName();
		System.out.println(iosDeviceName);

		String iosPlatformName=IOSDeviceUtils.getIOSPlatformName();
		System.out.println(iosPlatformName);
		GlobalUtil.setAppiumDriver(DriverUtil.invokeLocalIOSMobileApp());

	}

	@Before("@MobileTest1")
	public void beforeMobileMethod(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());
	}

	@Before("@MobileTest4")
	public void beforeMobileMethods(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());
	}

	@Before("@MobileTestAcceptance")
	public void beforeMobileMethodAcceptance(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());
		GlobalUtil.setMDriver(DriverUtil.getMobileApp(GlobalUtil.getCommonSettings().getExecutionEnv()));
	}

	@Before("@MobileTestSmoke")
	public void beforeMobileMethodSmoke(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());
	}

	// @Before
    // public void setup(Scenario scenario) {
    //     TestContext.initialize();
        
    //     if (scenario.getSourceTagNames().contains("@requiresUserId")) {
    //         fetchAndSetLatestUserId();
    //     }
    // }

	// @After
    // public void teardown() {
    //     TestContext.cleanup();
    // }
    
    // private void fetchAndSetLatestUserId() {
    //     Response response = RestAssured.given()
    //             .header("Authorization", "Bearer " + ConfigReader.getApiToken())
    //             .when()
    //             .get(ConfigReader.getBaseUri() + ConfigReader.getBasePath())
    //             .then()
    //             .extract().response();
                
    //     int userId = response.jsonPath().getInt("[0].id");
    //     TestContext.setLatestUserId(userId);
    // }

	@After("@Webtests")
	public void afterMethodSmoke(Scenario scenario) {
		if (scenario.isFailed()) {

			String ErrorMsgFinal = "";

			if (GlobalUtil.e == null) {
				ErrorMsgFinal = " " + GlobalUtil.errorMsg;
			} else if (GlobalUtil.errorMsg == null) {
				ErrorMsgFinal = " " + GlobalUtil.e;
			} else {
				ErrorMsgFinal = " " + GlobalUtil.e + " " + GlobalUtil.errorMsg;
			}

			// getting the os name to report the bug
			String osName = System.getProperty("os.name");
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
				osName = GlobalUtil.getCommonSettings().getRemoteOS();
			}

			try {
				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// Defect logging related
				if (GlobalUtil.getCommonSettings().getbugTool().contentEquals("YES")) {
					if (GlobalUtil.getCommonSettings().getBugToolName().contentEquals("JIRA")) {
						try {
							BufferUtilSuiteLevel.JIRAissueID = JiraOperations.createJiraIssueAndGetIssueID(GlobalUtil.getCommonSettings().getbugToolProjectName(),
									BufferUtilMethodLevel.testCaseName,
									"Automated on OS: " + osName + ",\n Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser() + ",\n Build Name: "
											+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + ErrorMsgFinal,
									"Bug", GlobalUtil.getCommonSettings().getAppEnviornment(), GlobalUtil.getCommonSettings().getBugLable(), "Medium");
							JiraOperations.createJiraIssueWithAttachment(BufferUtilSuiteLevel.JIRAissueID, BufferUtilSuiteLevel.screenshotFilePath);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						LogUtil.htmlInfoLog("No defect management tool is set to log defects.");
					}
				} else {
					LogUtil.htmlInfoLog("Not logging defect since flag in automation control sheet is set to \"No\".");
				}
				LogUtil.infoLog(getClass(), "Trying to get Bug ID: " + BufferUtilSuiteLevel.JIRAissueID);
			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {
			LogUtil.infoLog(Hooks.class, "Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
		}

		// close the browsers
		DriverUtil.closeAllDriver();
	}

	@After("@SFAutomation")
	public void afterMethodSFAutomation(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi
							.reporIssue(
									scenario.getName(), "Automated on OS: " + osName + ",\n Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser()
											+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
									BufferUtilSuiteLevel.screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

				LogUtil.infoLog(getClass(), "Trying to get Bug ID: " + bugID);

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		DriverUtil.closeAllDriver();
	}

	@After("@DOPS")
	public void afterMethodDOPS(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				byte[] screenshot = KeywordUtil.takeScreenshot(imagePath);
				scenario.attach(screenshot, "image/png", "Failed Screenshot");

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on OS: " + osName + ",\n Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser() + ",\n Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
							imagePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		DriverUtil.closeAllDriver();
	}

	@After("@SASelf")
	public void afterMethodSASelf(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on OS: " + osName + ",\n Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser() + ",\n Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
							imagePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		DriverUtil.closeAllDriver();
	}

	@After("@SkyNews")
	public void afterMethodSkyNews(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on OS: " + osName + ",\n Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser() + ",\n Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
							imagePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		DriverUtil.closeAllDriver();
	}

	@After("@APItests")
	public void afterAPIMethod(Scenario scenario) {
		if (scenario.isFailed()) {
			try {

				String ErrorMsgFinal = "";

				if (GlobalUtil.e == null) {
					ErrorMsgFinal = " " + GlobalUtil.errorMsg;
				} else if (GlobalUtil.errorMsg == null) {
					ErrorMsgFinal = " " + GlobalUtil.e;
				} else {
					ErrorMsgFinal = " " + GlobalUtil.e + " " + GlobalUtil.errorMsg;
				}

				// Defect logging related
				if (GlobalUtil.getCommonSettings().getbugTool().contentEquals("YES")) {
					if (GlobalUtil.getCommonSettings().getBugToolName().contentEquals("JIRA")) {
						try {
							BufferUtilSuiteLevel.JIRAissueID = JiraOperations.createJiraIssueAndGetIssueID(GlobalUtil.getCommonSettings().getbugToolProjectName(),
									BufferUtilMethodLevel.testCaseName, "Automated on OS: " + System.getProperty("os.name") + ". \n\n\n\n" + ErrorMsgFinal, "Bug",
									GlobalUtil.getCommonSettings().getAppEnviornment(), GlobalUtil.getCommonSettings().getBugLable(), "Medium");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						LogUtil.htmlInfoLog("No defect management tool is set to log defects.");
					}
				} else {
					LogUtil.htmlInfoLog("Not logging defect since flag in automation control sheet is set to \"No\".");
				}
			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "API Test has ended ");
		}
	}

	@After("@MobileTest1")
	public void afterMobileMethod(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {
				String scFileName = "ScreenShot_" + System.currentTimeMillis();
				String screenshotFilePath = ConfigReader.getValue("screenshotPath") + "\\" + scFileName + ".png";

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[1] + ",\n Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
							screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}
		GlobalUtil.getMDriver().quit();
	}

	@After("@MobileTest4")
	public void afterMobileMethods(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {
				String scFileName = "ScreenShot_" + System.currentTimeMillis();
				String screenshotFilePath = ConfigReader.getValue("screenshotPath") + "\\" + scFileName + ".png";

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[1] + ",\n Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
							screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		GlobalUtil.getMDriver().quit();
	}

	@After("@Mobiletests")
	public void afterMobileTestMethod(Scenario scenario) {
		if (scenario.isFailed()) {
			try {

				String ErrorMsgFinal = "";

				if (GlobalUtil.e == null) {
					ErrorMsgFinal = " " + GlobalUtil.errorMsg;
				} else if (GlobalUtil.errorMsg == null) {
					ErrorMsgFinal = " " + GlobalUtil.e;
				} else {
					ErrorMsgFinal = " " + GlobalUtil.e + " " + GlobalUtil.errorMsg;
				}

				// getting the os name to report the bug
				String osName = System.getProperty("os.name");
				if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
					osName = GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[0];
				}

				ExtentUtil.attachMobileScreenshotToReportOnFailure(scenario);

				// Defect logging related
				if (GlobalUtil.getCommonSettings().getbugTool().contentEquals("YES")) {
					if (GlobalUtil.getCommonSettings().getBugToolName().contentEquals("JIRA")) {
						try {
							BufferUtilSuiteLevel.JIRAissueID = JiraOperations.createJiraIssueAndGetIssueID(GlobalUtil.getCommonSettings().getbugToolProjectName(),
									BufferUtilMethodLevel.testCaseName,
									"OS: " + osName + ",\n Device Name: " + DriverUtil.deviceName + ",\n OS Version: " + DriverUtil.osVersion + ",\n Build Name: "
											+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + ErrorMsgFinal,
									"Bug", GlobalUtil.getCommonSettings().getAppEnviornment(), GlobalUtil.getCommonSettings().getBugLable(), "Medium");
							JiraOperations.createJiraIssueWithAttachment(BufferUtilSuiteLevel.JIRAissueID, BufferUtilSuiteLevel.screenshotFilePath);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						LogUtil.htmlInfoLog("No defect management tool is set to log defects.");
					}
				} else {
					LogUtil.htmlInfoLog("Not logging defect since flag in automation control sheet is set to \"No\".");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {
			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
		}
		// close the browsers
		GlobalUtil.getAppiumDriver().quit();
	}

	@After("@MobileIosTest")
	public void afterMobileIOSTestMethod(Scenario scenario) {
		if (scenario.isFailed()) {
			try {

				String ErrorMsgFinal = "";

				if (GlobalUtil.e == null) {
					ErrorMsgFinal = " " + GlobalUtil.errorMsg;
				} else if (GlobalUtil.errorMsg == null) {
					ErrorMsgFinal = " " + GlobalUtil.e;
				} else {
					ErrorMsgFinal = " " + GlobalUtil.e + " " + GlobalUtil.errorMsg;
				}

				// getting the os name to report the bug
				String osName = System.getProperty("os.name");
				if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
					osName = GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[0];
				}

				ExtentUtil.attachMobileScreenshotToReportOnFailure(scenario);

				// Defect logging related
				if (GlobalUtil.getCommonSettings().getbugTool().contentEquals("YES")) {
					if (GlobalUtil.getCommonSettings().getBugToolName().contentEquals("JIRA")) {
						try {
							BufferUtilSuiteLevel.JIRAissueID = JiraOperations.createJiraIssueAndGetIssueID(GlobalUtil.getCommonSettings().getbugToolProjectName(),
									BufferUtilMethodLevel.testCaseName,
									"OS: " + osName + ",\n Device Name: " + DriverUtil.deviceName + ",\n OS Version: " + DriverUtil.osVersion + ",\n Build Name: "
											+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + ErrorMsgFinal,
									"Bug", GlobalUtil.getCommonSettings().getAppEnviornment(), GlobalUtil.getCommonSettings().getBugLable(), "Medium");
							JiraOperations.createJiraIssueWithAttachment(BufferUtilSuiteLevel.JIRAissueID, BufferUtilSuiteLevel.screenshotFilePath);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						LogUtil.htmlInfoLog("No defect management tool is set to log defects.");
					}
				} else {
					LogUtil.htmlInfoLog("Not logging defect since flag in automation control sheet is set to \"No\".");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {
			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
		}
		// close the browsers
		GlobalUtil.getAppiumDriver().quit();
	}

	@After("@MobileTestAcceptance")
	public void afterMobileMethodAcceptance(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi
							.reporIssue(
									scenario.getName(), "Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[1]
											+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
									BufferUtilSuiteLevel.screenshotFilePath);
				}

				// updating the results in Testmangement tool

				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		GlobalUtil.getMDriver().quit();
	}

	@After("@MobileTestSmoke")
	public void afterMobileMethodSmoke(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi
							.reporIssue(
									scenario.getName(), "Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[1]
											+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
									BufferUtilSuiteLevel.screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}
	}

	@Before("@MobileTest6")
	public void beforeMobileMethodss(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());

		LogUtil.infoLog(Hooks.class, "Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());
	}

	@After("@MobileTest6")
	public void afterMobileMethodss(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi
							.reporIssue(
									scenario.getName(), "Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[1]
											+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
									BufferUtilSuiteLevel.screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		GlobalUtil.getMDriver().quit();
	}

}