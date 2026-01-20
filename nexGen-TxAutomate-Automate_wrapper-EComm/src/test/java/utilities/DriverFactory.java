package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
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

import com.google.common.base.Function;

/**
 * A factory for creating Driver objects.
 */
public class DriverFactory {

	/** The element. */
	static WebElement element = null;

	/** The all elms. */
	static List<WebElement> allElms = null;

	/** The Dummy string. */
	static String DummyString;

	/**
	 * Go to URL.
	 *
	 * @param url
	 *        the url
	 */
	public static void goToURL(String url) {
		try {
			if (GlobalUtil.getDriver() == null) {
				throw new Exception("Driver is null");
			}
			GlobalUtil.getDriver().manage().window().maximize();
			GlobalUtil.getDriver().manage().deleteAllCookies();
			GlobalUtil.getDriver().get(url);
			LogUtil.infoLog(DriverFactory.class, "Navigation to URL: " + url);
			LogUtil.htmlPassLog("Navigation to URL: " + url);
		} catch (Throwable ex) {
			LogUtil.htmlFailLog("Navigation to URL failed: " + url);
			LogUtil.errorLog(DriverFactory.class, "Navigation to URL failed: " + url, ex);
		}
	}

	/**
	 * Gets the current URL.
	 *
	 * @return the current URL
	 */
	public static String getCurrentURL() {
		String currentURL = null;
		try {
			if (GlobalUtil.getDriver() == null) {
				throw new Exception("Driver is null");
			} else {
				currentURL = GlobalUtil.getDriver().getCurrentUrl();
				LogUtil.htmlPassLog("Current URL is: " + currentURL);
				LogUtil.infoLog(DriverFactory.class, "Current URL is: " + currentURL);
			}
		} catch (Throwable e) {
			LogUtil.errorLog(DriverFactory.class, "Failed to get current URL", e);
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		return currentURL;
	}

	/**
	 * Find element by xpath.
	 *
	 * @param xpathExpression
	 *        the xpath expression
	 * 
	 * @return the web element
	 */
	public static WebElement FindElementByXpath(String xpathExpression) {
		try {
			WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
			element = wait.until(ExpectedConditions.visibilityOf(GlobalUtil.getDriver().findElement(By.xpath(xpathExpression))));
		} catch (Throwable e) {
			e.getMessage();
			Assert.fail(e.getMessage());
		}
		return element;
	}

	/**
	 * Find elements by xpath.
	 *
	 * @param xpathExpression
	 *        the xpath expression
	 * 
	 * @return the list
	 */
	public static List<WebElement> FindElementsByXpath(String xpathExpression) {

		try {
			WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
			allElms = wait.until(ExpectedConditions.visibilityOfAllElements(GlobalUtil.getDriver().findElements(By.xpath(xpathExpression))));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}

		return allElms;
	}

	/**
	 * Quit driver.
	 */
	public static void quitDriver() {
		try {
			if (GlobalUtil.getDriver() == null) {
				throw new Exception("Driver is already closed.");
			} else {
				GlobalUtil.getDriver().quit();
			}
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
	}

	/**
	 * Test fail take screenshot.
	 *
	 * @param imagePath
	 *        the image path
	 * 
	 * @return the string
	 * 
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 */
	public static String testFailTakeScreenshot(String imagePath) throws IOException {

		File src = ((TakesScreenshot) GlobalUtil.getDriver()).getScreenshotAs(OutputType.FILE);
		File des = new File(imagePath);
		FileUtils.copyFile(src, des);

		DummyString = des.getAbsolutePath();
		String path = DummyString;
		String base = File.separator + CommonConstants.baseFolderName + File.separator + CommonConstants.screenShotFolderName + File.separator;
		String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

		return relative;
	}

	/**
	 * Take screenshot.
	 *
	 * @param screenshotFilePath
	 *        the screenshot file path
	 * 
	 * @return the byte[]
	 */
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

	public static String testFailTakeScreenshotBase64(String imagePath) throws IOException {

		String src = ((TakesScreenshot) GlobalUtil.getDriver()).getScreenshotAs(OutputType.BASE64);
		File des = new File(imagePath);
		FileUtils.copyFile(new File(src), des);
//		System.out.println(des);
//		DummyString = des.getAbsolutePath();
//		String path = DummyString;
//		String base = "TXAutomate/ExecutionReports/FailedScreenshots/";
//		String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

		return src;
	}

	/**
	 * Check default execution variables.
	 *
	 * @param executionVariableName
	 *        the execution variable name
	 * @param defaultValue
	 *        the default value
	 */
	public static void checkDefaultExecutionVariables(String executionVariableName, String defaultValue, String variableName) {
		if (executionVariableName == null || executionVariableName.isEmpty()) {
			executionVariableName = defaultValue;
		}
		LogUtil.infoLog(DriverFactory.class, "Setting " + variableName + " as:  " + executionVariableName);
	}

	/**
	 * Wait for clickable web element.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the web element
	 */
	public static WebElement waitForClickable(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
			wait.ignoring(Exception.class);
			wait.ignoring(WebDriverException.class);
			return wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Throwable e) {
			GlobalUtil.e = e;
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			return null;
		}
	}

	/**
	 * Wait for visible web element.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return web element
	 */
	public static WebElement waitForVisible(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
			wait.ignoring(Exception.class);
			wait.ignoring(WebDriverException.class);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Throwable e) {
			GlobalUtil.e = e;
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			return null;
		}
	}

	/**
	 * Click void.
	 *
	 * @param locator
	 *        the locator
	 */
	public static void click(By locator, String logStep) {
		try {
			element = waitForClickable(locator);
			if (element == null) {
				String exceptionLog = "Element is not either not clickable or found.";
				LogUtil.errorLog(DriverFactory.class, exceptionLog);
				throw new NoSuchElementException(exceptionLog);
			} else {
				element.click();
				LogUtil.infoLog(CommonConstants.class, logStep + " locator: " + locator.toString());
				LogUtil.htmlPassLog(logStep);
			}
		} catch (Exception e) {
			GlobalUtil.e = e;
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
	}

	/**
	 * Input text void.
	 *
	 * @param locator
	 *        the locator
	 * @param data
	 *        the data
	 */
	public static void inputText(By locator, String data, String logStep) {
		try {
			element = waitForVisible(locator);
			if (element == null) {
				String exceptionLog = "Element not found.";
				LogUtil.errorLog(DriverFactory.class, exceptionLog);
				throw new NoSuchElementException(exceptionLog);
			} else {
				element.clear();
				element.sendKeys(data);
				LogUtil.infoLog(DriverFactory.class, logStep + " value " + data + " in " + locator.toString());
				LogUtil.htmlPassLog(logStep);
			}
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
	}

	/**
	 * Gets element text.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return element text
	 */
	public static String getElementText(By locator, String logStep) {
		String elementText = null;
		try {
			element = waitForVisible(locator);
			if (element == null) {
				String exceptionLog = "Element not found.";
				LogUtil.errorLog(DriverFactory.class, exceptionLog);
				throw new NoSuchElementException(exceptionLog);
			} else {
				elementText = element.getText().trim();
				LogUtil.infoLog(DriverFactory.class, logStep + " value " + elementText + " in " + locator.toString());
				LogUtil.htmlPassLog(logStep);
			}
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		return elementText;
	}

	/**
	 * Compare text.
	 *
	 * @param locator
	 *        the locator
	 * @param expectedText
	 *        the expected text
	 * 
	 * @return true, if successful
	 */
	public static boolean compareText(By locator, String expectedText, String logStep) {
		boolean flag = false;
		try {
			String actualText = getElementText(locator, logStep);
			if (actualText.contentEquals(expectedText)) {
				LogUtil.htmlPassLog("Expected " + expectedText + " And Actual " + actualText);
				flag = true;
			} else {
				LogUtil.htmlFailLog("Expected " + expectedText + " And Actual " + actualText);
			}
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		return flag;
	}

	/**
	 * Wait for invisible.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return true, if successful
	 */
	public static boolean waitForInvisible(By locator) {
		WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * Wait for present.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the web element
	 */
	public static WebElement waitForPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
		wait.ignoring(ElementNotInteractableException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Wait for visible ignore stale element.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the web element
	 */
	public static WebElement waitForVisibleIgnoreStaleElement(By locator) {
		WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(ElementNotInteractableException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Find with fluent wait.
	 *
	 * @param locator
	 *        the locator
	 * @param secondsTimeout
	 *        the seconds timeout
	 * @param pollingMil
	 *        the polling mil
	 * 
	 * @return the web element
	 * 
	 * @throws Exception
	 *         the exception
	 */
	public static WebElement findWithFluentWait(final By locator, int secondsTimeout, int pollingMil) throws Exception {
		GlobalUtil.getDriver().manage().timeouts().implicitlyWait(CommonConstants.DEFAULT_WAIT_TIME_SMALL, TimeUnit.SECONDS);// Because if implicit wait is
		// set then fluent wait will
		// not work
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(GlobalUtil.getDriver()).withTimeout(Duration.ofSeconds(secondsTimeout)).pollingEvery(Duration.ofMillis(pollingMil))
					.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class).ignoring(ElementNotInteractableException.class)
					.ignoring(WebDriverException.class);
			element = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});
		} catch (Exception t) {
			throw new Exception("Timeout reached when searching for element! Time: " + secondsTimeout + " seconds " + "\n" + t.getMessage());
		} finally {
			GlobalUtil.getDriver().manage().timeouts().implicitlyWait(CommonConstants.DEFAULT_WAIT_TIME_SMALL, TimeUnit.SECONDS);
		}

		return element;
	}

	/**
	 * Find with fluent wait.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the web element
	 * 
	 * @throws Exception
	 *         the exception
	 */
	@SuppressWarnings("deprecation")
	public static WebElement findWithFluentWait(final By locator) throws Exception {
		GlobalUtil.getDriver().manage().timeouts().implicitlyWait(CommonConstants.DEFAULT_WAIT_TIME_SMALL, TimeUnit.SECONDS); // Because if implicit wait
		// is set then fluent wait
		// will not work

		try {
			Wait<WebDriver> wait = new FluentWait<>(GlobalUtil.getDriver()).withTimeout(CommonConstants.DEFAULT_WAIT_TIME_SMALL, TimeUnit.SECONDS)
					.pollingEvery(Duration.ofMillis(200)).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
					.ignoring(ElementNotInteractableException.class);

			element = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});
		} catch (Exception t) {
			throw new Exception("Timeout reached when searching for element! Time: " + CommonConstants.DEFAULT_WAIT_TIME_SMALL + " seconds " + "\n" + t.getMessage());
		} finally {
			GlobalUtil.getDriver().manage().timeouts().implicitlyWait(CommonConstants.DEFAULT_WAIT_TIME_SMALL, TimeUnit.SECONDS);
		}

		return element;
	}

	/**
	 * Gets the web element.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the web element
	 * 
	 * @throws Exception
	 *         the exception
	 */
	public static WebElement getWebElement(By locator) throws Exception {
		return findWithFluentWait(locator);
	}

	/**
	 * Click on web element.
	 *
	 * @param webElement
	 *        the web element
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean clickOnWebElement(WebElement webElement, String logStep) {

		WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL);
		wait.until(ExpectedConditions.elementToBeClickable(webElement)).isDisplayed();
		if (webElement == null) {
			return false;
		} else {
			webElement.click();
			LogUtil.htmlPassLog(logStep);
			return true;
		}
	}

	/**
	 * Click on element position.
	 *
	 * @param locator
	 *        the locator
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean clickOnElementPosition(By locator, String logStep) {
		allElms = GlobalUtil.getDriver().findElements(locator);
		if (allElms.isEmpty()) {
			return false;
		}

		Actions ac = new Actions(GlobalUtil.getDriver());
		ac.moveToElement(GlobalUtil.getDriver().findElement(locator)).click();

		LogUtil.htmlPassLog(logStep);
		return true;
	}

	/**
	 * Click JS.
	 *
	 * @param webElement
	 *        the web element
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean clickJS(WebElement webElement, String logStep) {
		if (webElement == null) {
			return false;
		} else {
			((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("return arguments[0].click();", webElement);
			LogUtil.htmlPassLog(logStep);

			return true;
		}
	}

	/**
	 * Accept alert.
	 *
	 * @return true, if successful
	 */
	public static boolean acceptAlert() {
		Alert alert = GlobalUtil.getDriver().switchTo().alert();
		alert.accept();
		return true;
	}

	/**
	 * Switch to window.
	 *
	 * @return true, if successful
	 */
	public static boolean switchToWindow() {
		ArrayList<String> tabs2 = new ArrayList<String>(GlobalUtil.getDriver().getWindowHandles());
		GlobalUtil.getDriver().switchTo().window(tabs2.get(1));
		return true;
	}

	/**
	 * Gets the image title.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the image title
	 */
	public static String getImageTitle(By locator) {
		element = waitForVisible(locator);
		return element.getAttribute("title");

	}

	/**
	 * Gets the list elements.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return the list elements
	 */
	public static List<WebElement> getListElements(By locator) {
		try {
			findWithFluentWait(locator, 60, 300);
		} catch (Exception e) {
			return null;
		}

		return GlobalUtil.getDriver().findElements(locator);
	}

	/**
	 * Gets the list elements.
	 *
	 * @param locator
	 *        the locator
	 * @param logStep
	 *        the log step
	 * 
	 * @return the list elements
	 */
	public static List<WebElement> getListElements(By locator, String logStep) {
		try {
			findWithFluentWait(locator, 60, 300);
		} catch (Exception e) {
			return null;
		}
		LogUtil.htmlPassLog(logStep);

		return GlobalUtil.getDriver().findElements(locator);
	}

	/**
	 * Gets the list elements.
	 *
	 * @param locator
	 *        the locator
	 * @param logStep
	 *        the log step
	 * @param waitTimeInSec
	 *        the wait time in seconds
	 * @param poolingtimeinMilisec
	 *        the pooling time in mili seconds
	 * 
	 * @return the list elements
	 */
	public static List<WebElement> getListElements(By locator, String logStep, int waitTimeInSec, int poolingtimeinMilisec) {
		try {
			findWithFluentWait(locator, waitTimeInSec, poolingtimeinMilisec);
		} catch (Exception e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		LogUtil.htmlPassLog(logStep);

		return GlobalUtil.getDriver().findElements(locator);

	}

	/**
	 * Checks if is web element not present.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return true, if is web element not present
	 */
	public static boolean isWebElementNotPresent(By locator) {
		Boolean flag = false;
		try {
			GlobalUtil.getDriver().findElement(locator);
		} catch (Exception e) {
			flag = true;
		}

		return flag;
	}

	/**
	 * Press enter.
	 *
	 * @param locator
	 *        the locator
	 */
	public static void pressEnter(By locator) {
		element = waitForVisible(locator);
		element.sendKeys(Keys.ENTER);
	}

	/**
	 * Input text JS.
	 *
	 * @param locator
	 *        the locator
	 * @param data
	 *        the data
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean inputTextJS(By locator, String data, String logStep) {
		boolean flag = false;
		try {
			element = waitForVisible(locator);
			((JavascriptExecutor) GlobalUtil.getDriver()).executeScript("arguments[0].value = arguments[1]", element, data);
			if (element.getText().equalsIgnoreCase(data)) {
				LogUtil.htmlPassLog(logStep + " - Input Text: \"" + data + "\"");
				flag = true;
			}
		} catch (Exception e) {
			GlobalUtil.e = e;
		}
		return flag;
	}

	/**
	 * Clear input.
	 *
	 * @param locator
	 *        the locator
	 */
	public static void clearInput(By locator) {
		element = waitForVisible(locator);
		element.clear();
	}

	/**
	 * Select by index.
	 *
	 * @param locator
	 *        the locator
	 * @param index
	 *        the index
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean selectByIndex(By locator, int index, String logStep) {
		boolean flag = false;
		try {
			Select sel = new Select(GlobalUtil.getDriver().findElement(locator));
			sel.selectByIndex(index);

			sel = new Select(GlobalUtil.getDriver().findElement(locator));
			if (sel.getFirstSelectedOption().isDisplayed()) {
				LogUtil.htmlPassLog(logStep);
				flag = true;
			}
		} catch (Exception e) {
			GlobalUtil.e = e;
		}
		return flag;
	}

	/**
	 * Select by value.
	 *
	 * @param locator
	 *        the locator
	 * @param value
	 *        the value
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean selectByValue(By locator, String value, String logStep) {
		boolean flag = false;
		try {
			Select sel = new Select(GlobalUtil.getDriver().findElement(locator));
			sel.selectByValue(value);

			sel = new Select(GlobalUtil.getDriver().findElement(locator));
			if (sel.getFirstSelectedOption().isDisplayed()) {
				LogUtil.htmlPassLog(logStep);
				flag = true;
			}
		} catch (Exception e) {
			GlobalUtil.e = e;
		}
		return flag;
	}

	/**
	 * Select by visible text.
	 *
	 * @param locator
	 *        the locator
	 * @param value
	 *        the value
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean selectByVisibleText(By locator, String value, String logStep) {
		try {
			Select sel = new Select(GlobalUtil.getDriver().findElement(locator));
			sel.selectByVisibleText(value);
			LogUtil.htmlPassLog(logStep);

			return true;
		} catch (Exception e) {
			GlobalUtil.e = e;
			return false;
		}
	}

	/**
	 * Verify dropdown selected value.
	 *
	 * @param locator
	 *        the locator
	 * @param data
	 *        the data
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean verifyDropdownSelectedValue(By locator, String data, String logStep) {
		String defSelectedVal = null;
		try {
			Select sel = new Select(waitForVisible(locator));
			defSelectedVal = sel.getFirstSelectedOption().getText();
			LogUtil.htmlPassLog(logStep);
		} catch (Exception e) {
			GlobalUtil.e = e;
		}
		return defSelectedVal.trim().equals(data.trim());
	}

	/**
	 * Double click.
	 *
	 * @param locator
	 *        the locator
	 * @param logStep
	 *        the log step
	 * 
	 * @return true, if successful
	 */
	public static boolean doubleClick(By locator, String logStep) {
		boolean result = false;
		try {
			element = GlobalUtil.getDriver().findElement(locator);
			Actions action = new Actions(GlobalUtil.getDriver()).doubleClick(element);
			action.build().perform();
			LogUtil.htmlPassLog(logStep);

			result = true;

		} catch (StaleElementReferenceException e) {
			GlobalUtil.e = e;
			LogUtil.errorLog(DriverFactory.class, locator.toString() + " - Element is not attached to the page document ", e);
			result = false;
		} catch (NoSuchElementException e) {
			GlobalUtil.e = e;
			LogUtil.errorLog(DriverFactory.class, locator.toString() + " - Element is not attached to the page document ", e);
			result = false;
		} catch (Exception e) {
			GlobalUtil.e = e;
			LogUtil.errorLog(DriverFactory.class, locator.toString() + " - Element is not attached to the page document ", e);
			result = false;
		}
		return result;
	}

	/**
	 * Switch to frame.
	 *
	 * @param frameName
	 *        the frame name
	 * 
	 * @return true, if successful
	 */
	public static boolean switchToFrame(String frameName) {
		try {
			GlobalUtil.getDriver().switchTo().frame(frameName);
			return true;
		} catch (Exception e) {
			GlobalUtil.e = e;
			LogUtil.errorLog(DriverFactory.class, frameName + " TO FRAME FAILED", e);
			return false;
		}
	}

	/**
	 * Switch to frame by web element.
	 *
	 * @param locator
	 *        the locator
	 * 
	 * @return true, if successful
	 */
	public static boolean switchToFrameByWebElement(By locator) {
		try {
			element = GlobalUtil.getDriver().findElement(locator);
			GlobalUtil.getDriver().switchTo().frame(element);
			return true;
		} catch (Exception e) {
			GlobalUtil.e = e;
			LogUtil.errorLog(DriverFactory.class, "TO FRAME FAILED", e);
			return false;
		}
	}

	/**
	 * Gets the element property.
	 *
	 * @param locator
	 *        the locator
	 * @param attributeName
	 *        the attribute name
	 * 
	 * @return the element property
	 */
	public static String getElementProperty(By locator, String attributeName) {
		String attribute = null;
		try {
			element = GlobalUtil.getDriver().findElement(locator);
			attribute = element.getAttribute(attributeName);
		} catch (Exception e) {
			GlobalUtil.e = e;
		}
		return attribute;
	}

	/**
	 * Refresh page.
	 */
	public static void refreshPage() {
		try {
			GlobalUtil.getDriver().navigate().refresh();
		} catch (Exception e) {
			GlobalUtil.e = e;
		}
	}

	/**
	 * Wait for Ajax calls to complete.
	 */
	public static void waitForAjax() {
		new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL).until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				driver = GlobalUtil.getDriver();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				return (Boolean) js.executeScript("return jQuery.active == 0");
			}
		});
	}

	/**
	 * Wait for DOM load to complete.
	 */
	public static void waitForDOMLoadToComplete() {
		new WebDriverWait(GlobalUtil.getDriver(), CommonConstants.DEFAULT_WAIT_TIME_SMALL).until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				driver = GlobalUtil.getDriver();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				return (Boolean) js.executeScript("return document.readyState == complete");
			}
		});
	}
}