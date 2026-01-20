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
public class NewKeyWordUtil extends GlobalUtil {
    	private static final long DEFAULT_WAIT_SECONDS = 30;
    public static WebElement waitForVisible(By locator) {
	try {
		KeywordUtil.lastAction = "Waiting for visibility of element: " + locator.toString();
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

		WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		LogUtil.infoLog(KeywordUtil.class, "Element is visible: " + locator.toString());
		return element;
	} catch (Exception e) {
		LogUtil.errorLog(KeywordUtil.class, "Element not visible: " + locator.toString());
		System.out.println("Error ❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌");
		e.printStackTrace();
		return null;
	}
}
public static void executionDelay(long time) {
	try {
		KeywordUtil.lastAction = "Delaying execution for " + time + " milliseconds";
		LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

		Thread.sleep(time);
	} catch (InterruptedException e) {
		GlobalUtil.errorMsg = e.getMessage();
		LogUtil.errorLog(KeywordUtil.class, "Execution delay interrupted: " + GlobalUtil.errorMsg);
		LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		Thread.currentThread().interrupt(); // restore the interrupted status
	}
}


    public static void navigateToUrl(String url) {
    try {
        KeywordUtil.lastAction = "Navigate to: " + url;
        LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
        getDriver().get(url);

        ExtentUtil.logger.get().log(Status.PASS,
                HTMLReportUtil.passStringGreenColor("Navigating to URL: " + url));

        String pageTitle = getDriver().getTitle();
        ExtentUtil.logger.get().log(Status.PASS,
                HTMLReportUtil.passStringGreenColor("Page title is: " + pageTitle));

        if (pageTitle.contains("Robot Check")) {
            getDriver().get(url);
            ExtentUtil.logger.get().log(Status.PASS,
                    HTMLReportUtil.passStringGreenColor("Successfully navigated to URL after Robot Check: " + url));
        }

    } catch (Exception e) {
        String errorMsg = "❌ Failed to navigate to URL: " + url + " | Reason: " + e.getMessage();
        GlobalUtil.errorMsg = errorMsg;
        LogUtil.errorLog(KeywordUtil.class, errorMsg, e);

        ExtentUtil.logger.get().log(Status.FAIL,
                HTMLReportUtil.failStringRedColor(errorMsg));

        Assert.fail(errorMsg);
    }
}
public static WebElement waitForClickable(By locator) {
    WebElement elm = null;
    try {
		System.out.println("Entered in wait for element clickable❌❌❌❌");
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_SECONDS);
        wait.ignoring(WebDriverException.class);

        elm = wait.until(ExpectedConditions.elementToBeClickable(locator));

        // Mark session passed on BrowserStack if remote
        if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
            String platform = System.getProperty("platform");
            if (platform == null || platform.equalsIgnoreCase("Remote")) {
                ((JavascriptExecutor) GlobalUtil.getDriver()).executeScript(
                    "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \"Element found and clickable: " 
                    + locator.toString() + "\"}}");
            }
        }
    } catch (Throwable e) {
		System.out.println("Entered in wait for element clickable catch❌❌❌❌");
        GlobalUtil.errorMsg = "Failed to find or click on element [" + locator.toString() + "] due to: " + e.getMessage();

        // Log error
        LogUtil.errorLog(KeywordUtil.class, GlobalUtil.errorMsg, e);

        // Mark session failed on BrowserStack if remote
        if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
            String platform = System.getProperty("platform");
            if (platform == null || platform.equalsIgnoreCase("Remote")) {
                ((JavascriptExecutor) GlobalUtil.getDriver()).executeScript(
                    "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"" 
                    + GlobalUtil.errorMsg + "\"}}");
            }
        }

        // Assert failure
        Assert.fail(GlobalUtil.errorMsg);
    }

    return elm;
}
public static boolean click(By locator, String logStep) {
	try {
		System.out.println("Checking Click❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌");
		WebElement elm = waitForClickable(locator);
		if (elm == null) {
			String msg = "Element not clickable or not found: " + locator.toString();
			LogUtil.errorLog(KeywordUtil.class, msg);
			LogUtil.htmlFailLog(logStep + " - FAILED (Element not clickable): " + locator.toString());
			return false;
		} else {
			KeywordUtil.lastAction = "Click: " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			elm.click();
			LogUtil.htmlPassLog(logStep + " - PASSED");
			return true;
		}
	} catch (Exception e) {
			System.out.println("In Error❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌");
			System.out.flush();
		String msg = "Exception occurred while clicking on element: " + locator.toString();
		LogUtil.errorLog(KeywordUtil.class, msg, e);
		LogUtil.htmlFailLog(logStep + " - FAILED due to exception on locator: " + locator.toString());
		return false;
	}
}
public static boolean verifyDisplayAndEnable(By locator, String logStep) {
    try {
        KeywordUtil.lastAction = "Is Element Displayed and Enabled: " + locator.toString();
        LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

        WebElement element = waitForVisible(locator);

        boolean isDisplayedAndEnabled = element.isDisplayed() && element.isEnabled();

        if (isDisplayedAndEnabled) {
            ExtentUtil.logger.get().log(Status.PASS,
                    HTMLReportUtil.passStringGreenColor(logStep));
        } else {
            String failMsg = "❌ Element is not both displayed and enabled: " + locator.toString();
            ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(failMsg));
            GlobalUtil.errorMsg = failMsg;
            LogUtil.errorLog(KeywordUtil.class, failMsg);
            Assert.fail(failMsg);
        }

        return isDisplayedAndEnabled;

    } catch (Exception e) {
        String errorMsg = "❌ Exception while verifying element display and enable for: " + locator.toString()
                + " | Reason: " + e.getMessage();
        GlobalUtil.errorMsg = errorMsg;
        LogUtil.errorLog(KeywordUtil.class, errorMsg, e);
        ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(errorMsg));
        Assert.fail(errorMsg);
        return false;
    }
}
public static boolean inputText(By locator, String data, String logStep) {
    try {
        KeywordUtil.lastAction = "Input Text: \"" + data + "\" into " + locator.toString();
        LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

        WebElement elm = waitForVisible(locator);
        if (elm == null) {
            String errorMsg = "❌ Element not visible for input: " + locator.toString();
            GlobalUtil.errorMsg = errorMsg;
            LogUtil.errorLog(KeywordUtil.class, errorMsg);
            ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(errorMsg));
            Assert.fail(errorMsg);
            return false;
        }

        elm.clear();
        elm.sendKeys(data);
        ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
        return true;

    } catch (Exception e) {
        String errorMsg = "❌ Exception during inputText for locator: " + locator.toString()
                        + " | Reason: " + e.getMessage();
        GlobalUtil.errorMsg = errorMsg;
        LogUtil.errorLog(KeywordUtil.class, errorMsg, e);
        ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(errorMsg));
        Assert.fail(errorMsg);
        return false;
    }
}
public static boolean scrollAndClick(By locator, String logStep) throws InterruptedException {
    try {
        KeywordUtil.lastAction = "Scroll and Click: " + locator.toString();
        LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);

        executionDelay(5000);  // Consider replacing with smart wait in future
        WebElement element = GlobalUtil.getDriver().findElement(locator);

        // Scroll to the element
        ((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].scrollIntoView();", element);

        // Wait for visibility
        element = waitForVisible(locator);
        if (element == null) {
            String errorMsg = "❌ Element not visible after scroll: " + locator.toString();
            GlobalUtil.errorMsg = errorMsg;
            LogUtil.errorLog(KeywordUtil.class, errorMsg);
            ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(errorMsg));
            Assert.fail(errorMsg);
            return false;
        }

        // Click using JavaScript
        Object obj = ((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].click();", element);

        // Optional delay for dropdowns or animations
        Thread.sleep(1000);

        ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(logStep));
        return obj == null;

    } catch (Exception e) {
        String errorMsg = "❌ Exception during scrollAndClick on: " + locator.toString()
                        + " | Reason: " + e.getMessage();
        GlobalUtil.errorMsg = errorMsg;
        LogUtil.errorLog(KeywordUtil.class, errorMsg, e);
        ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor(errorMsg));
        Assert.fail(errorMsg);
        return false;
    }
}


    
}
