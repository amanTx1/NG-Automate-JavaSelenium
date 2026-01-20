package mobileutil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utilities.ExtentUtil;
import utilities.GlobalUtil;
import utilities.HTMLReportUtil;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class AmazonAppFunctions extends MobileKeywords2 {

	@SuppressWarnings("unchecked")
	public static AndroidDriver<MobileElement> driver1 = (AndroidDriver<MobileElement>) GlobalUtil.getMDriver();

	public static void skipSignIn(String ODevice_Name, String NDevice_Name) {
		if (ODevice_Name != NDevice_Name) {
			KeywordUtil.executionDelay(12000);
			click(AndriodConstants.Amazon.skip_sign_in_btn, AndriodConstants.Common.type_id, "Click on Skip Sign In Button");
		} else {
			KeywordUtil.executionDelay(5000);
			click(AndriodConstants.Amazon.skip_sign_in_btn, AndriodConstants.Common.type_id, "Click on Skip Sign In Button");
		}
	}

	public static void skipSignIn() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.skip_sign_in_btn, AndriodConstants.Common.type_id, "Click on Skip Sign In Button");

	}

	public static void clickShopByCategory(String ODevice_Name, String NDevice_Name) {
		if (ODevice_Name != NDevice_Name) {
			click(AndriodConstants.Amazon.shop_by_category_btn, AndriodConstants.Common.type_id, "Click on Shop By Category Button");
		} else {
			click(AndriodConstants.Amazon.shop_by_category_btn, AndriodConstants.Common.type_id, "Click on Shop By Category Button");
		}
	}

	public static void clickShopByCategory() {

		KeywordUtil.executionDelay(6000);

		click(AndriodConstants.Amazon.shop_by_category_btn, AndriodConstants.Common.type_id, "Click on Shop By Category Button");

	}

	public static void clickFirstCategoryExpandButton(String ODevice_Name, String NDevice_Name) {
		if (ODevice_Name != NDevice_Name) {
			KeywordUtil.executionDelay(12000);
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.view.View"));
			for (int i = 1; i <= ListElemnts.size(); i++) {
				if (ListElemnts.get(i).getText().contains("Echo & Alexa")) {
					System.out.println("Gootcha-Echo & Alexa");
					ListElemnts.get(i).click();
					ExtentUtil.logger.get().log(Status.PASS, "Click on the product to be selected" + "<-ExecutedInDevice:->" + ODevice_Name);
					break;
				}
			}
		} else {
			KeywordUtil.executionDelay(12000);
			click(AndriodConstants.Amazon.first_category_expand_btn, AndriodConstants.Common.type_xpath, "Click on the product to be selected");
		}
	}

	public static void clickFirstCategoryExpandButton() {
		KeywordUtil.executionDelay(12000);
		try {
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.view.View"));

			for (int i = 1; i <= ListElemnts.size(); i++) {
				System.out.println("Element=" + i + "=" + ListElemnts.get(i).getText());
				if (ListElemnts.get(i).getText().contains("Echo & Alexa")) {
					System.out.println("Gootcha-Echo & Alexa");
					ListElemnts.get(i).click();
					ExtentUtil.logger.get().log(Status.PASS, "First Category option selected");
					break;
				}
			}
		} catch (Exception e) {
			AmazonAppFunctions.click(AndriodConstants.Amazon.first_category_expand_btn, AndriodConstants.Common.type_xpath, "First Category option selected");
		}
	}

	public static void clickSecondCategoryExpandButton() {
		KeywordUtil.executionDelay(12000);
		try {
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.view.View"));

			for (int i = 1; i <= ListElemnts.size(); i++) {
				System.out.println("Element=" + i + "=" + ListElemnts.get(i).getText());
				if (ListElemnts.get(i).getText().contains("Fire TV")) {
					System.out.println("Gootcha-Fire TV");
					ListElemnts.get(i).click();
					ExtentUtil.logger.get().log(Status.PASS, "Second Category option selected");
					break;
				}
			}
		} catch (Exception e) {
			AmazonAppFunctions.click(AndriodConstants.Amazon.second_category_expand_btn, AndriodConstants.Common.type_xpath, "Second Category option selected");
		}
	}

	public static void selectProduct(String ODevice_Name, String NDevice_Name) throws IOException, InterruptedException, TimeoutException {
		if (ODevice_Name != NDevice_Name) {
			KeywordUtil.executionDelay(1000);
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.view.View"));
			for (int i = 1; i <= ListElemnts.size(); i++) {
				if (ListElemnts.get(i).getText().contains("Echo Plus")) {
					System.out.println("Gootcha-Echo Plus");
					ListElemnts.get(i).click();
					ExtentUtil.logger.get().log(Status.PASS, "Click on the product to be selected" + "<-ExecutedInDevice:->" + ODevice_Name);
					break;
				}
			}
			KeywordUtil.executionDelay(5000);
			MobileKeywords.scrollDown();

		} else {
			KeywordUtil.executionDelay(1000);
			click(AndriodConstants.Amazon.select_product_btn, AndriodConstants.Common.type_xpath, "Click on the product to be selected");
			KeywordUtil.executionDelay(5000);
			MobileKeywords.scrollDown();
		}
	}

	public static void selectProduct() {
		KeywordUtil.executionDelay(12000);
		try {
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.view.View"));
			for (int i = 1; i <= ListElemnts.size(); i++) {
				System.out.println("Element=" + i + "=" + ListElemnts.get(i).getText());
				if (ListElemnts.get(i).getText().contains("Echo Studio")) {
					System.out.println("Gootcha-Echo Studio");
					ListElemnts.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			click(AndriodConstants.Amazon.select_product_btn, AndriodConstants.Common.type_xpath, "Click on the product to be selected");
		}
		ExtentUtil.logger.get().log(Status.PASS, "Click on the product to be selected");
	}

	public static void selectsecondProduct() {
		KeywordUtil.executionDelay(12000);
		try {
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.view.View"));
			for (int i = 1; i <= ListElemnts.size(); i++) {
				System.out.println("Element=" + i + "=" + ListElemnts.get(i).getText());
				if (ListElemnts.get(i).getText().contains("All-new Fire TV Stick (3rd Gen)")) {
					System.out.println("Gootch-All-new Fire TV Stick (3rd Gen)");
					ListElemnts.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			click(AndriodConstants.Amazon.select_product_btn, AndriodConstants.Common.type_xpath, "Click on the product to be selected");
		}
		ExtentUtil.logger.get().log(Status.PASS, "Click on the product to be selected");
	}

	public static void addProductToCart(String ODevice_Name, String NDevice_Name) throws IOException, InterruptedException {

		if (ODevice_Name != NDevice_Name) {
			KeywordUtil.executionDelay(3000);
			click(AndriodConstants.Amazon.add_to_cart_btn, AndriodConstants.Common.type_xpath, "Click on Add to Cart button" + "<-ExecutedInDevice:->" + ODevice_Name);
		} else {
			KeywordUtil.executionDelay(3000);
			click(AndriodConstants.Amazon.add_to_cart_btn, AndriodConstants.Common.type_xpath, "Click on Add to Cart button");
		}
	}

	public static void ads() {
		WebElement a = GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/deny_access_button"));

		if (a.isDisplayed()) {
			a.click();
		}
	}

	public static void addProductToCart() throws TimeoutException, InterruptedException, IOException {
		KeywordUtil.executionDelay(2000);
		scrollAndClick2("Add to Cart");
	}

	public static void verifyItemAddedToCart(String ODevice_Name, String NDevice_Name) {
		if (ODevice_Name != NDevice_Name) {
			KeywordUtil.executionDelay(1000);
			click(AndriodConstants.Amazon.checkout_cart_img, AndriodConstants.Common.type_id, "Verify Product is added to Cart" + "<-ExecutedInDevice:->" + ODevice_Name);

		} else {
			KeywordUtil.executionDelay(3000);
			click(AndriodConstants.Amazon.checkout_cart_img, AndriodConstants.Common.type_id, "Verify Product is added to Cart");
		}
		GlobalUtil.mdriver.quit();
	}

	public static void verifyItemAddedToCart() {
		try {
			KeywordUtil.executionDelay(6000);// Waiting for item to be added to the cart
			WebElement Cart = GlobalUtil.mdriver.findElementByXPath("//android.widget.ImageView[@content-desc='Cart']");
			Cart.click();

			String SuccessMsg = "Verified Product is Added to the cart";

			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor(SuccessMsg));

			JavascriptExecutor jse = (JavascriptExecutor) GlobalUtil.mdriver;
			String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \"" + SuccessMsg + "\"}}";

			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote") && System.getProperty("platform") == null) {
				jse.executeScript(script);
			} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote") && (System.getProperty("platform").equalsIgnoreCase("remote"))) {
				jse.executeScript(script);
			}
		} catch (Throwable e) {
			GlobalUtil.errorMsg = "Failed to find and click on cart button due to reason: " + e.getLocalizedMessage().split("Command duration or timeout:")[0];
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote") && System.getProperty("platform") == null) {
				JavascriptExecutor jse = (JavascriptExecutor) GlobalUtil.mdriver;
				String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
						+ "Failed to find and click on cart button" + "\"}}";
				jse.executeScript(script);
			} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote") && (System.getProperty("platform").equalsIgnoreCase("remote"))) {
				JavascriptExecutor jse = (JavascriptExecutor) GlobalUtil.mdriver;
				String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
						+ "Failed to find and click on cart button" + "\"}}";
				jse.executeScript(script);
			}
			Assert.fail(GlobalUtil.errorMsg);
		}
	}

	public static void verifyItemNotAddedToCart() {
		try {
			KeywordUtil.executionDelay(6000); // Waiting for item to be added to the cart
			WebElement Cart = GlobalUtil.mdriver.findElementByXPath("//android.widget.ImageView[@content-desc='Test']");
			Cart.click();
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Verified Product is Added to the cart"));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = "Failed to find and click on cart button due to reason: " + e.getLocalizedMessage().split("Command duration or timeout:")[0];
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote") && System.getProperty("platform") == null) {
				JavascriptExecutor jse = (JavascriptExecutor) GlobalUtil.mdriver;
				String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
						+ "Failed to find and click on cart button" + "\"}}";
				jse.executeScript(script);
			} else if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote") && (System.getProperty("platform").equalsIgnoreCase("remote"))) {
				JavascriptExecutor jse = (JavascriptExecutor) GlobalUtil.mdriver;
				String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
						+ "Failed to find and click on cart button" + "\"}}";
				jse.executeScript(script);
			}
			Assert.fail(GlobalUtil.errorMsg);
		}
	}

	public static void verifyItemincart() {
		KeywordUtil.executionDelay(3000);
		WebElement Hindi_Cart = GlobalUtil.mdriver.findElementByXPath("//android.widget.ImageView[@content-desc='कार्ट']");
		KeywordUtil.executionDelay(3000);
		Hindi_Cart.click();
	}

	public static void loginToAmazonApp(String username, String ODevice_Name, String NDevice_Name) {
		if (ODevice_Name != NDevice_Name) {
			GlobalUtil.mdriver.closeApp();
			KeywordUtil.executionDelay(1000);
			GlobalUtil.mdriver.launchApp();
			KeywordUtil.executionDelay(1000);
			List<MobileElement> ListElemnts = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.widget.Button"));
			for (int i = 0; i <= ListElemnts.size(); i++) {
				System.out.println("Element=" + i + "=" + ListElemnts.get(i).getText());
				if (ListElemnts.get(i).getText().contains("Sign in")) {
					System.out.println("Gootcha-Sign In");
					ListElemnts.get(i).click();
					ExtentUtil.logger.get().log(Status.PASS, "Click on Sign In Button" + "<-ExecutedInDevice:->" + ODevice_Name);
					break;
				}
			}

			KeywordUtil.executionDelay(1000);
			try {
				MobileElement element = (MobileElement) GlobalUtil.mdriver.findElements(By.className("android.widget.Button")).get(0);
				element.sendKeys("WrongUsername");
				KeywordUtil.executionDelay(1000);
			} catch (Exception e) {

				List<MobileElement> ListElemnts1 = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.widget.EditText"));
				for (int i = 0; i <= ListElemnts1.size(); i++) {
					System.out.println("Element=" + i + "=" + ListElemnts1.get(i).getText());
					if (ListElemnts1.get(i).getText().contains("ap_email_login")) {
						System.out.println("Gootcha-ap_email_login");
						ListElemnts1.get(i).sendKeys("Wrong UserName");
						ExtentUtil.logger.get().log(Status.PASS, "Enter UserName" + "<-ExecutedInDevice:->" + ODevice_Name);
						break;
					}
				}

			}
			KeywordUtil.executionDelay(5000);
			List<MobileElement> ListElemnts11 = GlobalUtil.mdriver.findElements(MobileBy.xpath("//android.widget.Button"));
			for (int i = 0; i <= ListElemnts11.size(); i++) {
				System.out.println("Element=" + i + "=" + ListElemnts11.get(i).getText());
				if (ListElemnts11.get(i).getText().contains("Continue")) {
					System.out.println("Gootcha-Continue");
					ListElemnts11.get(i).click();
					ExtentUtil.logger.get().log(Status.PASS, "Click Continue Button" + "<-ExecutedInDevice:->" + ODevice_Name);
					break;
				}
			}

		} else {
			KeywordUtil.executionDelay(1000);
			click(AndriodConstants.Amazon.sign_In_btn, AndriodConstants.Common.type_id, "Click on Sign In Button");
			KeywordUtil.executionDelay(1000);
			writeInInput(AndriodConstants.Amazon.email_Login_txtbx, AndriodConstants.Common.type_id, username, "Enter Invalid Username");
			KeywordUtil.executionDelay(5000);
			click(AndriodConstants.Amazon.continue_btn, AndriodConstants.Common.type_id, "Click on Continue Button");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void Searchingproduct() throws InterruptedException {
		KeywordUtil.executionDelay(5000);
		GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_search_src_text")).click();
		KeywordUtil.executionDelay(5000);
		GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_search_src_text")).sendKeys("Cello Pinpoint Ball Pen (Pack of 10 pens - Blue)" + "\n");
		System.out.println("Searching the given product");
		((AndroidDriver) GlobalUtil.mdriver).pressKey(new KeyEvent(AndroidKey.ENTER));
		KeywordUtil.executionDelay(5000);
		GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_results_image")).click();

	}

	@SuppressWarnings("rawtypes")
	public static void Searchproduct() throws InterruptedException {
		KeywordUtil.executionDelay(5000);
		GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_search_src_text")).click();

		KeywordUtil.executionDelay(5000);
		GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_search_src_text")).sendKeys("Cello Pinpoint Ball Pen (Pack of 10 pens - Blue)" + "\n");
		System.out.println("Searching the given product");
		((AndroidDriver) GlobalUtil.mdriver).pressKey(new KeyEvent(AndroidKey.ENTER));

		GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_results_image")).click();

		if (GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/loc_ux_gps_enter_pincode")).isDisplayed()) {
			GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/loc_ux_gps_enter_pincode")).click();
			WebElement pincode = GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/loc_ux_pin_code_text_pt1"));
			pincode.sendKeys("500025");
			System.out.println("Entered Pincode");
			WebElement apply2 = GlobalUtil.mdriver.findElementByXPath("//android.widget.Button[@text='Apply']");
			apply2.click();
			System.out.println("After Entering Pincode, Tap on Apply button");
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static void SearchproductLatest(String productName) {
		try {
			KeywordUtil.executionDelay(5000);
			GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_search_src_text")).click();
			KeywordUtil.executionDelay(5000);
			GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_search_src_text")).sendKeys(productName + "\n");
			LogUtil.infoLog(AmazonAppFunctions.class, "Searching the given product");
			((AndroidDriver) GlobalUtil.mdriver).pressKey(new KeyEvent(AndroidKey.ENTER));
			GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/rs_results_image")).click();
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Searching the Product: " + productName));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = "Failed to find and enter in search field due to reason: " + e.getLocalizedMessage().split("Command duration or timeout:")[0];
			if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
				JavascriptExecutor jse = (JavascriptExecutor) GlobalUtil.mdriver;
				String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
						+ "Failed to find and enter in search field" + "\"}}";
				jse.executeScript(script);
			}
			Assert.fail(GlobalUtil.errorMsg);
		}
	}

	public static void Enterpincode() {
		KeywordUtil.executionDelay(5000);
		WebElement pincode = GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/loc_ux_gps_enter_pincode"));
		pincode.click();
		KeywordUtil.executionDelay(5000);
		WebElement Enterpincode = GlobalUtil.mdriver.findElement(By.id("in.amazon.mShop.android.shopping:id/loc_ux_pin_code_text_pt1"));
		Enterpincode.sendKeys("500025");
		System.out.println("Entered Pincode");
		KeywordUtil.executionDelay(5000);
		WebElement apply3 = GlobalUtil.mdriver.findElementByXPath("//android.widget.Button[@text='लागू करें']");
		KeywordUtil.executionDelay(5000);
		apply3.click();
	}

	public static void clickPreference() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.preference, AndriodConstants.Common.type_xpath, "Click on Prefernce Button");

	}

	public static void clickPreferenceDependency() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.preferenceDependency, AndriodConstants.Common.type_xpath, "Click on Prefernce Dependency Button");

	}

	public static void enableWifiCheckbox() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.wifiCheckbox, AndriodConstants.Common.type_id, "Click on Wifi Checkbox");
		KeywordUtil.executionDelay(2000);
		click(AndriodConstants.Amazon.wifiSettings, AndriodConstants.Common.type_xpath, "Click on Wifi Settings");
		KeywordUtil.executionDelay(2000);
		writeInInput(AndriodConstants.Amazon.wifiSettingsInput, AndriodConstants.Common.type_id, "Wifi switched on", "Wifi settings entered");
		KeywordUtil.executionDelay(2000);
		click(AndriodConstants.Amazon.okButton, AndriodConstants.Common.type_id, "Click on Ok button");
	}

	public static void verify_wifi_settings() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.wifiSettings, AndriodConstants.Common.type_xpath, "Click on Wifi Settings");
		KeywordUtil.executionDelay(2000);
		Assert.assertEquals(GetTextOfElement(By.id("android:id/edit")), "Wifi switched on");
		ExtentUtil.logger.get().log(Status.PASS, "Verify wifi settings is saved correctly");

	}

	public static void Breadcrumb() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.BreadcrumbIcon, AndriodConstants.Common.type_xpath, "Click on Breadcrumb icon");
	}

	public static void changeLanguage() {
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.Language, AndriodConstants.Common.type_xpath, "Click on Language option");
		KeywordUtil.executionDelay(6000);
		click(AndriodConstants.Amazon.HindiRadiobutton, AndriodConstants.Common.type_xpath, "Click on Hindi radio button");
		KeywordUtil.executionDelay(5000);
		click(AndriodConstants.Amazon.HindiButton, AndriodConstants.Common.type_xpath, "Click on Hindi button");

	}

}
