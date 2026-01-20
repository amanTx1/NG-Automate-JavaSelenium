package mobileutil;

import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import step_definitions.RunCukesTest;
import utilities.KeywordUtil;

public class MultipleDeviceAmazonMobilefunc extends RunCukesTest {

	public static AndroidDriver<MobileElement> Mdriver;

	@SuppressWarnings("unchecked")
	public static <AndroidElement> void skipSignIn(AndroidDriver<MobileElement> Mdriver) throws InterruptedException {
		KeywordUtil.executionDelay(2000);
		System.out.println("In SKip Signin");
		AndroidElement searchElement = (AndroidElement) new WebDriverWait(Mdriver, 20).until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
		((RemoteWebElement) searchElement).click();
		AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(Mdriver, 20)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
		((RemoteWebElement) insertTextElement).sendKeys("BrowserStack");
		KeywordUtil.executionDelay(2000);
		java.util.List<MobileElement> allProductsName = Mdriver.findElementsByClassName("android.widget.TextView");
		assert (allProductsName.size() > 0);
	}

	public static void clickShopByCategory() {
		System.out.println("In clickShopByCategory");

	}

	public static void clickFirstCategoryExpandButton() throws InterruptedException {
		System.out.println("In clickFirstCategoryExpandButton");

	}

	public static void selectProduct() {
		System.out.println("In selectProduct");

	}

	public static void addProductToCart() throws IOException, InterruptedException {
		System.out.println("In addProductToCart");

	}

	public static void verifyItemAddedToCart() {
		System.out.println("In verifyItemAddedToCart");

	}

}
