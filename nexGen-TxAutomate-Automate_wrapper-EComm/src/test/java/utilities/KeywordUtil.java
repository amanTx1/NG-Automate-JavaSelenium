package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import mobileutil.MobileKeywords2;
import step_definitions.Hooks;
// import step_definitions.RunCukesTest;

/**
 * @author TX
 */
public class KeywordUtil extends GlobalUtil {

	public static String cucumberTagName;
	private static final long DEFAULT_WAIT_SECONDS = 30;
	protected static final int FAIL = 0;
	static WebElement webElement;
	protected static String url = "";
	private static String userDir = "user.dir";
	@SuppressWarnings("unused")
	private static String text = "";
	public static final String VALUE = "value";
	public static String lastAction = "";
	public static String renamedExtentReportName;

	static String result_FolderName = System.getProperty("user.dir") + "\\ExecutionReports\\HTMLReports";

	public static void executionDelay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
	}

	public static String currentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String dt = dtf.format(now).replace("/", "-").replace(":", "-").replace(" ", "_");

		return dt;
	}

	public static void onExecutionFinish() {

		LogUtil.infoLog(KeywordUtil.class, "Test process has ended");

		if (GlobalUtil.getCommonSettings().getEmailOutput().equalsIgnoreCase("Y")) {
			LogUtil.infoLog(KeywordUtil.class, "Email Flag Set To: " + GlobalUtil.getCommonSettings().getEmailOutput());
			try {
				sendMail.sendEmailToClient(
						"Hi All, \n\nPlease find the attached Execution Report.\n\n\nThanks & Regards\nTesting Xperts",
						true, false);
			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} else {
			LogUtil.infoLog(KeywordUtil.class, "Email Flag Set To: " + GlobalUtil.getCommonSettings().getEmailOutput());
		}

		String htmlReportFile = System.getProperty("user.dir") + "\\" + ConfigReader.getValue("HtmlReportFullPath");
		System.out.println("cucumber path is: " + htmlReportFile);
		File f = new File(htmlReportFile);
		if (f.exists()) {
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + htmlReportFile);
			} catch (IOException e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		}

		renamedExtentReportName = System.getProperty("user.dir") + "\\"
				+ ConfigReader.getValue("extentReportPathToRename").replace("%s", Hooks.executingTagName).replace("@",
						currentDateTime() + "_");
		new File(System.getProperty("user.dir") + "\\" + ConfigReader.getValue("extentReportPath"))
				.renameTo(new File(renamedExtentReportName));
		String renamedExtentReportPath = renamedExtentReportName;
		System.out.println("Extent Report File path is: " + renamedExtentReportPath);
		File extentReport = new File(renamedExtentReportPath);
		if (extentReport.exists()) {
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + renamedExtentReportPath);
			} catch (IOException e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		}
	}

	public static byte[] takeScreenshot(String screenshotFilePath) {
		try {
			byte[] screenshot = ((TakesScreenshot) GlobalUtil.getDriver()).getScreenshotAs(OutputType.BYTES);
			FileOutputStream fileOuputStream = new FileOutputStream(screenshotFilePath);
			fileOuputStream.write(screenshot);
			fileOuputStream.close();
			return screenshot;
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		return null;
	}

	public static boolean scrollingToElementofAPage(By locator, String logStep) throws InterruptedException {
		executionDelay(5000);
		WebElement element = GlobalUtil.getDriver().findElement(locator);
		((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].scrollIntoView();", element);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return true;
	}

	public static byte[] takeMobileScreenshot(String screenshotFilePath) {
		try {
			byte[] screenshot = ((TakesScreenshot) GlobalUtil.getMDriver()).getScreenshotAs(OutputType.BYTES);
			FileOutputStream fileOuputStream = new FileOutputStream(screenshotFilePath);
			fileOuputStream.write(screenshot);
			fileOuputStream.close();
			return screenshot;
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		return null;
	}

	public static String getCurrentDateTime() {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		System.out.println(strDate);
		return strDate;
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static void navigateToUrl(String url) {
		try {
			KeywordUtil.lastAction = "Navigate to: " + url;
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			getDriver().get(url);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Navigating to URL: " + url));
			String Pagetitle = getDriver().getTitle();
			ExtentUtil.logger.get().log(Status.PASS,
					HTMLReportUtil.passStringGreenColor("Page title is: " + Pagetitle));
			if (Pagetitle.contains("Robot Check")) {
				getDriver().get(url);
				ExtentUtil.logger.get().log(Status.PASS,
						HTMLReportUtil.passStringGreenColor("Successfully navigated to URL: " + url));
			}

		} catch (Exception e) {
		}
	}

	public static String getCurrentUrl() {
		String currentURL = getDriver().getCurrentUrl();
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Current URL is: " + currentURL));
		return currentURL;
	}

	// ---------------- original -------------------------

	public static WebElement waitForClickable(By locator) {
		WebElement elm = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
			wait.ignoring(WebDriverException.class);

			if (wait.until(ExpectedConditions.elementToBeClickable(locator)) != null) {
				elm = wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
						&& (System.getProperty("platform") == null)) {
					((JavascriptExecutor) GlobalUtil.getDriver()).executeScript(
							"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \"Element found and clicked\"}}");
				} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
						&& (System.getProperty("platform").equalsIgnoreCase("Remote"))) {
					((JavascriptExecutor) GlobalUtil.getDriver()).executeScript(
							"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \"Element found and clicked\"}}");
				}
			}
		} catch (Throwable e) {
			GlobalUtil.errorMsg = "Failed to find and click on element due to reason: " + e.getMessage();
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
					&& (System.getProperty("platform") == null)) {
				((JavascriptExecutor) GlobalUtil.getDriver()).executeScript(
						"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
								+ GlobalUtil.errorMsg + "\"}}");
			} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
					&& (System.getProperty("platform").equalsIgnoreCase("Remote"))) {
				((JavascriptExecutor) GlobalUtil.getDriver()).executeScript(
						"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
								+ GlobalUtil.errorMsg + "\"}}");
			}
			Assert.fail(GlobalUtil.errorMsg);
		}

		return elm;
	}

	public static boolean isElementInVisible(WebElement elm, String message) {
		boolean flag = false;
		if (elm.isDisplayed()) {
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
					&& (System.getProperty("platform") == null)) {
				((JavascriptExecutor) GlobalUtil.getDriver())
						.executeScript(
								"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
										+ message + "\"}}");
			} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
					&& (System.getProperty("platform").equalsIgnoreCase("Remote"))) {
				((JavascriptExecutor) GlobalUtil.getDriver())
						.executeScript(
								"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
										+ message + "\"}}");
			}
		} else {
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
					&& (System.getProperty("platform") == null)) {
				((JavascriptExecutor) GlobalUtil.getDriver())
						.executeScript(
								"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \""
										+ message + "\"}}");
			} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")
					&& (System.getProperty("platform").equalsIgnoreCase("Remote"))) {
				((JavascriptExecutor) GlobalUtil.getDriver())
						.executeScript(
								"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \""
										+ message + "\"}}");
			}
			flag = true;
		}
		return flag;
	}

	// Added by Anand
	public static WebElement waitForClickableMobile(By locator) {
		WebDriverWait wait = new WebDriverWait(getMDriver(), DEFAULT_WAIT_SECONDS);
		// wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(WebDriverException.class);

		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static WebElement waitForPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
		// wait.ignoring(ElementNotVisibleException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static WebElement waitForPresentLongTime(By locator, long waitTime) {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		// wait.ignoring(ElementNotVisibleException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static WebElement waitForVisible(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			return null;
		}
	}

	// Added by Anand
	public static WebElement waitForVisibleMobile(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(getMDriver(), DEFAULT_WAIT_SECONDS);
			// wait.ignoring(ElementNotVisibleException.class);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean waitForInVisibile(By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public static WebElement waitForVisibleIgnoreStaleElement(By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
		wait.ignoring(StaleElementReferenceException.class);
		// wait.ignoring(ElementNotVisibleException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * @param locator
	 * @param seconds
	 * @param poolingMil
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public static WebElement findWithFluintWait(final By locator, int seconds, int poolingMil) throws Exception {
		// Because if implicit wait is set then fluint wait will not work

		getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		WebElement element = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(seconds))
					.pollingEvery(Duration.ofMillis(poolingMil))
					.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
					.ignoring(WebDriverException.class);
			element = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});

		} catch (Exception t) {
			throw new Exception("Timeout reached when searching for element! Time: " + seconds + " seconds " + "\n"
					+ t.getMessage());
		} finally {
		}

		return element;
	}// End FindWithWait()

	/**
	 * @param locator
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public static WebElement findWithFluintWait(final By locator) throws Exception {
		getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		// Because if implict wait is set then fluint wait will not work
		KeywordUtil.lastAction = "Find Element: " + locator.toString();
		WebElement element = null;

		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
					.withTimeout(Duration.ofSeconds(DEFAULT_WAIT_SECONDS)).pollingEvery(Duration.ofMillis(100))
					.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
					.ignoring(WebDriverException.class);

			element = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});

		} catch (Exception t) {
			throw new Exception("Timeout reached when searching for element! Time: " + DEFAULT_WAIT_SECONDS
					+ " seconds " + "\n" + t.getMessage());
		} finally {
		}

		return element;
	}// End FindWithWait()

	public static MobileElement getElement(String path, String type, String logStep) {
		WebDriverWait wait = new WebDriverWait(GlobalUtil.mdriver, DEFAULT_WAIT_SECONDS);
		MobileElement element = (MobileElement) wait
				.until(ExpectedConditions.visibilityOfElementLocated(MobileKeywords2.locatortype(type, path)));
		if (logStep.length() > 0)
			ExtentUtil.logger.get().log(Status.PASS, logStep);
		return element;

	}

	public static WebElement getWebElement(By locator) throws Exception {
		KeywordUtil.lastAction = "Find Element: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		return findWithFluintWait(locator);
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/*
	 * Web driver common functions
	 * ===========================================================
	 */

	/**
	 * @param locator
	 * 
	 * @return
	 */

	// ------------------------ original code----------------------
	public static boolean click(By locator, String logStep) {

		WebElement elm = waitForClickable(locator);
		if (elm == null) {
			return false;
		} else {
			KeywordUtil.lastAction = "Click: " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			elm.click();

			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		}
	}

	public static boolean switchToFrameAndClick(By frameLocator, By buttonLocator, String logStep) {
		try {
			// Switch to frame
			WebElement frameElement = getDriver().findElement(frameLocator);
			getDriver().switchTo().frame(frameElement);
			
			// Click the button
			WebElement buttonElement = waitForClickable(buttonLocator);
			if (buttonElement == null) {
				LogUtil.infoLog(KeywordUtil.class, "Button not clickable: " + buttonLocator.toString());
				return false;
			}
			
			KeywordUtil.lastAction = "Click in frame: " + buttonLocator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			buttonElement.click();
			
			// Log the action
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
			
			// Switch back to default content
			getDriver().switchTo().defaultContent();
			
			return true;
		} catch (Exception e) {
			LogUtil.infoLog(KeywordUtil.class, "Failed to switch to frame or click button: " + e.getMessage());
			return false;
		}
	}

	// ------------------------------------------------------
	// Added by Anand
	public static boolean clickMobile(By locator, String logStep) {
		WebDriverWait wait = new WebDriverWait(getMDriver(), DEFAULT_WAIT_SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(locator)).isDisplayed();
		wait.until(ExpectedConditions.elementToBeClickable(locator)).isEnabled();

		KeywordUtil.lastAction = "Click: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForClickableMobile(locator);
		if (elm == null) {
			return false;
		} else {
			elm.click();
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		}
	}

	// ............
	public static boolean clickCart(By locator, String logStep) {

		KeywordUtil.lastAction = "Click: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForClickable(locator);
		if (elm == null) {
			return false;
		} else {

			((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].scrollIntoView();", elm);
			elm.click();
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		}
	}

	// ....

	public static boolean acceptAlert() {

		Alert alert = GlobalUtil.getDriver().switchTo().alert();
		alert.accept();
		return true;

	}

	// ......
	public static boolean switchToWindow() {

		ArrayList<String> tabs2 = new ArrayList<String>(GlobalUtil.getDriver().getWindowHandles());
		GlobalUtil.getDriver().switchTo().window(tabs2.get(1));
		return true;

	}
	// ....

	/**
	 * @param linkText
	 * 
	 * @return
	 */
	public static boolean clickLink(String linkText, String logStep) {
		KeywordUtil.lastAction = "Click Link: " + linkText;
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForClickable(By.linkText(linkText));
		if (elm == null) {
			return false;
		} else {
			elm.click();
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		}
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static String getElementText(By locator) {
		KeywordUtil.lastAction = "Get Element text: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForClickable(locator);
		return elm.getText().trim();
	}

	public static String getImageTitle(By locator) {
		WebElement elm = waitForVisible(locator);
		return elm.getAttribute("title");

	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static boolean isWebElementVisible(By locator, String logStep) {
		try {
			KeywordUtil.lastAction = "Check Element visible: " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement elm = waitForVisible(locator);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return elm.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isMobileElementVisible(By locator, String logStep) {
		try {
			KeywordUtil.lastAction = "Check Element visible: " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement elm = waitForVisibleMobile(locator);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return elm.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isWebElementEnable(By locator, String logStep) {
		KeywordUtil.lastAction = "Check Element visible: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForVisible(locator);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return elm.isEnabled();
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static List<WebElement> getListElements(By locator, String logStep) {
		KeywordUtil.lastAction = "Get List of Elements: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

		try {
			findWithFluintWait(locator, 60, 300);
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return getDriver().findElements(locator);

	}

	public static boolean isWebElementPresent(By locator, String logStep) {
		KeywordUtil.lastAction = "Check Element present: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		List<WebElement> elements = getDriver().findElements(locator);
		if (elements.isEmpty()) {
			return false;
		}
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return true;
	}

	public static boolean hoverOnElement(By by) throws InterruptedException {

		WebElement element = getDriver().findElement(by);
		Actions act = new Actions(getDriver());
		act.moveToElement(element).build().perform();

		executionDelay(5000);

		return true;

	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static boolean isWebElementNotPresent(By locator) {
		KeywordUtil.lastAction = "Check Element not present: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		List<WebElement> elements = (new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

		if (elements.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean inputText(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Input Text: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForVisible(locator);
		if (elm == null) {
			return false;
		} else {
			elm.clear();
			elm.sendKeys(data);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
			return true;
		}
	}

	public static boolean inputTextMobile(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Input Text: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForVisibleMobile(locator);
		if (elm == null) {
			return false;
		} else {
			elm.sendKeys(data);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
			return true;
		}
	}

	public static void pressTabKey(By locator) {
		WebElement elm = waitForVisible(locator);
		elm.sendKeys(Keys.TAB);
	}

	public static void pressEnter(By locator) {
		WebElement elm = waitForVisible(locator);
		elm.sendKeys(Keys.ENTER);
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean inputTextJS(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Input Text: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].value = arguments[1]", element, data);
		if (element.getText().equalsIgnoreCase(data)) {
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		} else
			return false;
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static boolean isRadioSelected(By locator, String logStep) {
		KeywordUtil.lastAction = "Is Radio Selected: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return element.isSelected();
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static boolean isRadioNotSelected(By locator, String logStep) {
		KeywordUtil.lastAction = "Is Radio Not Selected: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		boolean check = isRadioSelected(locator, logStep);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return (!check);
	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static boolean clearInput(By locator) {
		WebElement element = waitForVisible(locator);
		element.clear();
		element = waitForVisible(locator);
		return element.getAttribute(VALUE).isEmpty();
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean verifyCssProperty(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Verify CSS : " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

		String[] property = data.split(":", 2);
		String expProp = property[0];
		String expValue = property[1];
		boolean flag = false;
		String prop = (waitForPresent(locator)).getCssValue(expProp);
		if (prop.trim().equals(expValue.trim())) {
			flag = true;
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return flag;
		} else {
			return flag;
		}
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean verifyInputText(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Verify Input Expected Text: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		String actual = element.getAttribute(VALUE);
		LogUtil.infoLog(KeywordUtil.class, "Actual:" + actual);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return actual.equalsIgnoreCase(data);

	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean verifyInputTextJS(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Verify Input Expected Text: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);

		String message = String.format("Verified text expected \"%s\" actual \"%s\" ", data, element.getText());
		LogUtil.infoLog(KeywordUtil.class, message);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return data.equalsIgnoreCase(element.getText());
	}

	/**
	 * <h1>Log results</h1>
	 * <p>
	 * This function will write results to the log file.
	 * </p>
	 * 
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean verifyText(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Verify Expected Text: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		String message = String.format("Verified text expected \"%s\" actual \"%s\" ", data, element.getText());
		LogUtil.infoLog(KeywordUtil.class, message);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return element.getText().equalsIgnoreCase(data);
	}

	// Added by Anand
	public static boolean verifyTextMobile(By locator, String data, String logStep) throws Exception {
		try {
			KeywordUtil.lastAction = "Verify Expected Text: " + data + " - " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement element = waitForVisibleMobile(locator);
			String message = String.format("Verified text expected \"%s\" actual \"%s\" ", data, element.getText());
			LogUtil.infoLog(KeywordUtil.class, message);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return element.getText().equalsIgnoreCase(data);
		} catch (Throwable e) {
			return false;
		}
	}

	public static boolean verifyTextContains(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Verify Text Contains: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		String message = new String(
				String.format("Verified text expected \"%s\" actual \"%s\" ", data, element.getText()));
		LogUtil.infoLog(KeywordUtil.class, message);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return element.getText().toUpperCase().contains(data.toUpperCase());

	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public static boolean verifyDisplayAndEnable(By locator, String logStep) {
		KeywordUtil.lastAction = "Is Element Displayed and Enable : " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
		return element.isDisplayed() && element.isEnabled();
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean clickJS(By locator, String logStep) {
		KeywordUtil.lastAction = "Click : " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement element = waitForVisible(locator);
		Object obj = ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return obj == null;
	}


	public static boolean scrollAndClick(By locator, String logStep) throws InterruptedException {
		KeywordUtil.lastAction = "Scroll and Click : " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
	
		executionDelay(5000);
		WebElement element = GlobalUtil.getDriver().findElement(locator);
		// Scroll to the element
		((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].scrollIntoView();", element);
		// Wait for the element to be visible
		element = waitForVisible(locator);
		// Click the element using JavaScript

		Object obj = ((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].click();", element);

		 // Hold for 1 second after clicking to allow dropdown content to be visible
		 Thread.sleep(1000);
		// Log the step
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
	
		return obj == null;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/*
	 * Handling selects ===========================================================
	 */

	/**
	 * @param locator
	 * @param index
	 * 
	 * @return
	 */
	public static boolean selectByIndex(By locator, int index, String logStep) {
		KeywordUtil.lastAction = "Select dropdown by index : " + index + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		Select sel = new Select(getDriver().findElement(locator));
		sel.selectByIndex(index);

		// Check whether element is selected or not
		sel = new Select(getDriver().findElement(locator));
		if (sel.getFirstSelectedOption().isDisplayed()) {
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param locator
	 * @param value
	 * 
	 * @return
	 */
	public static boolean selectByValue(By locator, String value, String logStep) {
		KeywordUtil.lastAction = "Select dropdown by value : " + value + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		Select sel = new Select(getDriver().findElement(locator));
		sel.selectByValue(value);

		// Check whether element is selected or not
		sel = new Select(getDriver().findElement(locator));
		if (sel.getFirstSelectedOption().isDisplayed()) {
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param locator
	 * @param value
	 * 
	 * @return
	 */
	public static boolean selectByVisibleText(By locator, String value, String logStep) {
		try {
			KeywordUtil.lastAction = "Select dropdown by text : " + value + " - " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			Select sel = new Select(getDriver().findElement(locator));
			sel.selectByVisibleText(value);
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			return false;
		}
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws Throwable
	 */
	public static boolean verifyAllValuesOfDropDown(By locator, String data, String logStep) throws Throwable {
		KeywordUtil.lastAction = "Verify Dropdown all values: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		boolean flag = false;
		WebElement element = findWithFluintWait(locator);
		List<WebElement> options = element.findElements(By.tagName("option"));
		String[] allElements = data.split(",");
		String actual;
		for (int i = 0; i < allElements.length; i++) {
			LogUtil.infoLog(KeywordUtil.class, options.get(i).getText());
			LogUtil.infoLog(KeywordUtil.class, allElements[i].trim());

			actual = options.get(i).getText().trim();
			if (actual.equalsIgnoreCase(allElements[i].trim())) {
				ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 */
	public static boolean verifyDropdownSelectedValue(By locator, String data, String logStep) {
		KeywordUtil.lastAction = "Verify Dropdown selected option: " + data + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		Select sel = new Select(waitForVisible(locator));
		String defSelectedVal = sel.getFirstSelectedOption().getText();
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
		return defSelectedVal.trim().equals(data.trim());
	}

	/**
	 * @param locator
	 * @param size
	 * 
	 * @return
	 */
	public static boolean verifyElementSize(By locator, int size, String logStep) {
		KeywordUtil.lastAction = "Verify Element size: " + size + " - " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		List<WebElement> elements = getDriver().findElements(locator);
		if (elements.size() == size) {
			LogUtil.infoLog(KeywordUtil.class, "Element is Present " + size + "times");
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			return true;
		} else {
			LogUtil.infoLog(KeywordUtil.class, "Element is not Present with required size");
			LogUtil.infoLog(KeywordUtil.class, "Expected size:" + size + " but actual size: " + elements.size());
			return false;
		}
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 */
	public static boolean writeInInputCharByChar(By locator, String data, String logStep) throws InterruptedException {
		WebElement element = waitForVisible(locator);
		element.clear();
		String[] b = data.split("");
		for (int i = 0; i < b.length; i++) {
			element.sendKeys(b[i]);
			executionDelay(250);
		}
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return true;
	}

	// Get Tag name and locator value of Element
	public static String getElementInfo(By locator) throws Exception {
		return " Locator: " + locator.toString();
	}

	public static String getElementInfo(WebElement element) throws Exception {
		String webElementInfo = "";
		webElementInfo = webElementInfo + "Tag Name: " + element.getTagName() + ", Locator: ["
				+ element.toString().substring(element.toString().indexOf("->") + 2);
		return webElementInfo;

	}

	/**
	 * @param locator
	 * 
	 * @return
	 */
	public boolean verifyCurrentDateInput(By locator, String logStep) {
		boolean flag = false;
		WebElement element = waitForVisible(locator);
		String actual = element.getAttribute(VALUE).trim();
		DateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		dtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
		String expected = dtFormat.format(date).trim();
		if (actual.trim().contains(expected)) {
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			flag = true;

		}
		return flag;
	}

	/**
	 * @param locator
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 */
	public static boolean uploadFilesUsingSendKeys(By locator, String data, String logStep)
			throws InterruptedException {
		WebElement element = waitForVisible(locator);
		element.clear();
		element.sendKeys(System.getProperty(userDir) + "\\src\\test\\resources\\uploadFiles\\" + data);
		ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

		return true;
	}

	/**
	 * @return
	 */
	public boolean delDirectory() {
		File delDestination = new File(System.getProperty(userDir) + "\\src\\test\\resources\\downloadFile");
		if (delDestination.exists()) {
			File[] files = delDestination.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					delDirectory();
				} else {
					files[i].delete();
				}
			}
		}
		return delDestination.delete();
	}

	public static boolean doubleClick(By locator, String logStep) {
		boolean result = false;
		try {
			KeywordUtil.lastAction = "Double click: " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement element = getDriver().findElement(locator);
			Actions action = new Actions(getDriver()).doubleClick(element);
			action.build().perform();
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));

			result = true;

		} catch (StaleElementReferenceException e) {
			LogUtil.infoLog(KeywordUtil.class,
					locator.toString() + " - Element is not attached to the page document " + e.getStackTrace());
			result = false;
		} catch (NoSuchElementException e) {
			LogUtil.infoLog(KeywordUtil.class,
					locator.toString() + " - Element is not attached to the page document " + e.getStackTrace());
			result = false;
		} catch (Exception e) {
			LogUtil.infoLog(KeywordUtil.class,
					locator.toString() + " - Element is not attached to the page document " + e.getStackTrace());
			result = false;
		}
		return result;
	}

	public static boolean switchToFrame(By frameName) {

		try {
			WebElement element = getDriver().findElement(frameName);
			getDriver().switchTo().frame(element);
			return true;

		} catch (Exception e) {
			LogUtil.infoLog(KeywordUtil.class, "switchToFrame" + frameName + " TO FRAME FAILED" + e.getStackTrace());
			return false;
		}
	}

	

	public static String createZipFile() throws IOException {
		result_FolderName = result_FolderName.replace("\\", "/");
		String outputFile = result_FolderName + ".zip";
		FileOutputStream fos = new FileOutputStream(outputFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		packCurrentDirectoryContents(result_FolderName, zos);
		zos.closeEntry();
		zos.close();
		fos.close();
		return outputFile;
	}

	public static void packCurrentDirectoryContents(String directoryPath, ZipOutputStream zos) throws IOException {
		for (String dirElement : new File(directoryPath).list()) {
			String dirElementPath = directoryPath + "/" + dirElement;
			if (new File(dirElementPath).isDirectory()) {
				packCurrentDirectoryContents(dirElementPath, zos);
			} else {
				ZipEntry ze = new ZipEntry(dirElementPath.replaceAll(result_FolderName + "/", ""));
				zos.putNextEntry(ze);
				FileInputStream fis = new FileInputStream(dirElementPath);
				byte[] bytesRead = new byte[512];
				int bytesNum;
				while ((bytesNum = fis.read(bytesRead)) > 0) {
					zos.write(bytesRead, 0, bytesNum);
				}

				fis.close();
			}
		}
	}

	public static WebElement getElementByImg(String img) {
		return GlobalUtil.mdriver.findElement(MobileBy.image(DriverUtil.getImgRef(img)));
	}

	public static void catchAssertError(Throwable e) {
		GlobalUtil.e = e;
		GlobalUtil.errorMsg = e.getMessage();
		String[] msg = e.getMessage().split("expected");
		LogUtil.htmlFailLog(GlobalUtil.errorMsg);
	}


	// Added by Anand
	public static String getAttributeValueMobile(By locator, String attName) {
		WebDriverWait wait = new WebDriverWait(getMDriver(), DEFAULT_WAIT_SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(locator)).isDisplayed();
		wait.until(ExpectedConditions.elementToBeClickable(locator)).isEnabled();

		KeywordUtil.lastAction = "Click: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
		WebElement elm = waitForClickableMobile(locator);
		if (elm == null) {
			return null;
		} else {
			return elm.getAttribute(attName);
		}
	}

	public static void waitForDOMLoadToComplete() {
		new WebDriverWait(GlobalUtil.getDriver(), DEFAULT_WAIT_SECONDS).until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				driver = GlobalUtil.getDriver();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				return (Boolean) js.executeScript("return document.readyState == complete");
			}
		});
	}

	public static void clickDesiredOptionInLIst(By locatorMain, String optionMain) {
		List<WebElement> listOfWebElements = getDriver().findElements(locatorMain);
		for (int i = 0; i < listOfWebElements.size(); i++) {
			if (listOfWebElements.get(i).getText().contentEquals(optionMain)) {
				listOfWebElements.get(i).click();
				ExtentUtil.logger.get().log(Status.PASS,
						HTMLReportUtil.passStringGreenColor("Clicked on locator: " + locatorMain.toString()));
				break;
			} else {
				continue;
			}
		}
	}

}// End class

@SuppressWarnings("serial")
class TestStepFailedException extends Exception {

	TestStepFailedException(String s) {
		super(s);
	}

	public static void scrolldown(WebElement Element) {
		JavascriptExecutor js = (JavascriptExecutor) GlobalUtil.getDriver();
		js.executeScript("window.scrollBy(0,600);", Element);
	}

	public static void scrollUp(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) GlobalUtil.getDriver();
		js.executeScript("window.scrollBy(0,-600);", element);
	}

	public static void scrollAndMouseHover(By locator,String logStep) throws InterruptedException {    
		WebElement element = GlobalUtil.getDriver().findElement(locator);    
		((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].scrollIntoView();", element);    
		Thread.sleep(5000);   
		Actions actions = new Actions(GlobalUtil.getDriver());    
		Thread.sleep(3000);    
		actions.moveToElement(GlobalUtil.getDriver().findElement(locator)).perform();    
		Thread.sleep(6000);
	}

	
	

}
