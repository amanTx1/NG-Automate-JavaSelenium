package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import mobileutil.AmazonAppFunctions;
// import mobileutil.AndriodConstants.Amazon;
import mobileutil.MobileKeywords;
import step_definitions.Hooks;

/**
 * This DriverUtil class refer to browsers, os details, browser versions and will close all browsers
 */

public class DriverUtil {

	public static int row;
	public static String NDevice_Name = null;
	public static String ODevice_Name;
	public static String username;

	public static final String IE = "IE";
	public static final String REMOTE = "BrowserStack";
	public static final String EDGE = "edge";
	public static final String CHROME = "Chrome";
	public static final String FIREFOX = "Firefox";
	public static final String SAFARI = "Safari";
	public static final String WINDOWS = "Windows";
	public static final String MACOS = "OS X";
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();

	private static HashMap<String, String> checkLogin = new HashMap<String, String>();
	public static String appium_ip_address = MobileKeywords.GetValue("appium_ip_address");
	public static String appium_port = MobileKeywords.GetValue("appium_port");
	public static DesiredCapabilities capabilities = new DesiredCapabilities();

	public static XSSFWorkbook wb;
	public static XSSFSheet sheet1;

	public static final String USER_NAME = "testmailtestingx_94YFr3";

	public static final String ACCESS_KEY = "GA13qkaCFPPzpV9mn5gi";

	public static String deviceName = null;
	public static String osVersion = null;

	/**
	 * will use this getting browser(chrome, ie, ff)NorfsbK5jasabqG4jqR5
	 * 
	 * @param browserName
	 * 
	 * @return
	 */
	private DriverUtil() {

	}

	public static AndroidDriver<MobileElement> getMobileApp(String exeEnv) throws Exception {

		if (exeEnv.equalsIgnoreCase(REMOTE)) {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/ExcelFiles/MobileDevicesList.xlsx");
			wb = new XSSFWorkbook(fis);
			sheet1 = wb.getSheet("AndroidList");
			int rowCount = sheet1.getLastRowNum();
			for (int row = 1; row < rowCount; row++) {
				String ExecuteFlag = sheet1.getRow(row).getCell(4).getStringCellValue();
				if (ExecuteFlag.equals("Yes")) {
					ODevice_Name = sheet1.getRow(row).getCell(1).getStringCellValue();
					DataFormatter formatter = new DataFormatter();
					String Device_Version = formatter.formatCellValue(sheet1.getRow(row).getCell(2));
					System.out.println(ODevice_Name);
					System.out.println(Device_Version);
					DesiredCapabilities caps = new DesiredCapabilities();
					caps.setCapability("device", ODevice_Name);
					caps.setCapability("os_version", Device_Version);
					// caps.setCapability("autoAcceptAlerts",true);
					caps.setCapability("autoDismissAlerts", true);
					caps.setCapability("name", "Bstack-[Java]-Mobile Amazon Test");
					caps.setCapability("app", "bs://2ee16e84c872d2674fe0016525110b27543a7cd6");

					GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL("https://" + USER_NAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub"), caps);
					GlobalUtil.mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					AmazonAppFunctions.skipSignIn(ODevice_Name, NDevice_Name);
					NDevice_Name = ODevice_Name;
				}
			}

			return GlobalUtil.mdriver;
		}

		else {

			try {
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GlobalUtil.getCommonSettings().getAndroidName());
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GlobalUtil.getCommonSettings().getAndroidVersion().split("_")[1]);
				capabilities.setCapability("platformName", MobileKeywords.GetValue("platformName"));
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, MobileKeywords.GetValue("automationName"));
				capabilities.setCapability(MobileCapabilityType.UDID, GlobalUtil.getCommonSettings().getAndroidID());
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, MobileKeywords.GetValue("newCommandTimeout"));
				capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
				capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
				GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);

			} catch (Exception e) {
				GlobalUtil.errorMsg = e.getMessage();
				LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			}
		} // else close
		return GlobalUtil.mdriver;

	}

	
	public static AppiumDriver invokeLocalMobileApp(String exeEnv, String deviceDetails) {

		if(DeviceUtils.getPlatformName().equalsIgnoreCase("Android")) {
		
			System.out.println("Device name : "+DeviceUtils.getAndroidDeviceName());
			System.out.println("platform name : "+DeviceUtils.getPlatformName());
			System.out.println("udid  : "+DeviceUtils.getAndroidUDID());
			System.out.println("platform version  : "+DeviceUtils.getAndroidPlatformVersion());

			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceUtils.getAndroidDeviceName());
			capabilities.setCapability(MobileCapabilityType.UDID, DeviceUtils.getAndroidUDID());
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, DeviceUtils.getPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceUtils.getAndroidPlatformVersion());
			capabilities.setCapability("wdaLaunchTimeout",90000);
			capabilities.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/APK/eribank.apk");
				
			
		}else if(IOSDeviceUtils.getIOSPlatformName().equalsIgnoreCase("iOS")){
			//Use IOSDeviceUtils for get Deveice name,Version and Udid

			System.out.println("Device name : "+IOSDeviceUtils.getIOSDeviceName());
			System.out.println("platform name : "+IOSDeviceUtils.getIOSPlatformName());
			System.out.println("udid  : "+IOSDeviceUtils.getIOSUDID());
			System.out.println("platform version  : "+IOSDeviceUtils.getIOSPlatformVersion());

			String ipaPath = "src/test/resources/ipa/EriBank.ipa";
			File ipa = new File(ipaPath);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, IOSDeviceUtils.getIOSDeviceName());
			capabilities.setCapability(MobileCapabilityType.UDID, IOSDeviceUtils.getIOSUDID());
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, IOSDeviceUtils.getIOSPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, IOSDeviceUtils.getIOSPlatformVersion());
   			capabilities.setCapability("app", ipa.getAbsolutePath());
		}
			try {
			GlobalUtil.appiumDriver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		GlobalUtil.appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentUtil.logger.get().log(Status.INFO, "<font color=blue>Execution Done By The Device:" + deviceDetails + "</font>");
		return GlobalUtil.appiumDriver;
	}

	


	public static AppiumDriver invokeLocalIOSMobileApp() {

		//String deviceName = deviceDetails.split("_")[0];
		//String osVersion = deviceDetails.split("_")[1];
		String ipaPath = "src/test/resources/ipa/EriBank.ipa";
		File ipa = new File(ipaPath);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 12");
		capabilities.setCapability(MobileCapabilityType.UDID, "00008101-000E4080110B001E");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "17.6.1");
		//capabilities.setCapability(MobileCapabilityType.NO_RESET,true);

		capabilities.setCapability("app", ipa.getAbsolutePath());
		// ----------------------------------------------------------------------------------------
		try {
			//GlobalUtil.iosDriver = new IOSDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			GlobalUtil.appiumDriver = new AppiumDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		GlobalUtil.appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//ExtentUtil.logger.get().log(Status.INFO, "<font color=blue>Execution Done By The Device:" + deviceDetails + "</font>");
		return GlobalUtil.appiumDriver;
	}


	public static AndroidDriver<MobileElement> invokeLocalMobileApp_1(String exeEnv, String deviceDetails) {

		String deviceName = deviceDetails.split("_")[0];
		String osVersion = deviceDetails.split("_")[1];

		System.out.println(deviceName);
		System.out.println(osVersion);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobileKeywords.GetValue("platformName"));
		capabilities.setCapability("appPackage", "io.appium.android.apis");
		capabilities.setCapability("appActivity", "io.appium.android.apis.ApiDemos");
		try {
			GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723"), capabilities);

		} catch (MalformedURLException e)

		{
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		GlobalUtil.mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ExtentUtil.logger.get().log(Status.INFO, "<font color=blue>Execution Done By The Device:" + deviceDetails + "</font>");
		return GlobalUtil.mdriver;
	}

	public static AndroidDriver<MobileElement> invokeBrowserStackMobileApp(String deviceDetails) {
		deviceName = deviceDetails.split("_")[0];
		osVersion = deviceDetails.split("_")[1];
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("device", deviceName);
		caps.setCapability("os_version", osVersion);
		caps.setCapability("autoDismissAlerts", true);
		caps.setCapability("projectName", GlobalUtil.getCommonSettings().getProjectName());
		caps.setCapability("buildName", GlobalUtil.getCommonSettings().getBuildNumber());
		caps.setCapability("sessionName", Hooks.testCaseDescription);
		LogUtil.infoLog(DriverUtil.class, GlobalUtil.getCommonSettings().getAndroidCloudDeviceID());
		caps.setCapability("app", GlobalUtil.getCommonSettings().getAndroidCloudDeviceID());

		String bsUserName = GlobalUtil.getCommonSettings().getHostName();
		if (bsUserName.length() == 0)
			bsUserName = System.getenv("BS_USER");
		String bsKey = GlobalUtil.getCommonSettings().getKey();
		if (bsKey.length() == 0)
			bsKey = System.getenv("BS_KEY");

		String URLBS = "http://" + bsUserName + ":" + bsKey + "@hub-cloud.browserstack.com/wd/hub";

		try {
			GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL(URLBS), caps);
		} catch (MalformedURLException e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}

		GlobalUtil.mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return GlobalUtil.mdriver;
	}

	/**
	 * @param browserName
	 * 
	 * @return
	 */
	public static WebDriver getBrowser(String exeEnv, String browserName) {
		WebDriver browser = null;
		try {
			String URL = null;
			if (System.getProperty("platform") != null && System.getProperty("platform").length() > 0)
				exeEnv = System.getProperty("platform");

			MutableCapabilities capabilities = new MutableCapabilities();

			capabilities.setCapability("browserstack.debug", "true");
			capabilities.setCapability("build", GlobalUtil.getCommonSettings().getBuildNumber());

			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();

			browserstackOptions.put("os", GlobalUtil.getCommonSettings().getRemoteOS().split("_")[0]);
			browserstackOptions.put("osVersion", GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);
			browserstackOptions.put("browserVersion", "latest");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("projectName", GlobalUtil.getCommonSettings().getProjectName());
			browserstackOptions.put("buildName", GlobalUtil.getCommonSettings().getBuildNumber());
			browserstackOptions.put("sessionName", Hooks.testCaseDescription);

			if (exeEnv.equals("REMOTE")) {

				if (GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("WINDOWS")) {
					if (browserName.equalsIgnoreCase(CHROME)) {
						capabilities.setCapability("browserName", CHROME);
					} else if (browserName.equalsIgnoreCase(EDGE)) {
						capabilities.setCapability("browserName", EDGE);
					} else if (browserName.equalsIgnoreCase(FIREFOX)) {
						capabilities.setCapability("browserName", FIREFOX);
					} else {
						throw new Exception("Browser name provided in automation control sheet is not valid. Please change it.");
					}
				} else if (GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("OS X")) {
					if (browserName.equalsIgnoreCase(SAFARI)) {
						capabilities.setCapability("browserName", SAFARI);
					} else if (browserName.equalsIgnoreCase(CHROME)) {
						capabilities.setCapability("browserName", CHROME);
					} else if (browserName.equalsIgnoreCase(EDGE)) {
						capabilities.setCapability("browserName", EDGE);
					} else if (browserName.equalsIgnoreCase(FIREFOX)) {
						capabilities.setCapability("browserName", FIREFOX);
					} else {
						throw new Exception("Browser name provided in automation control sheet is not valid. Please change it.");
					}
				} else {
					throw new Exception("Remote OS name provided in automation control sheet is not valid. Please change it.");
				}

				try {
					capabilities.setCapability("bstack:options", browserstackOptions);

					String bsUserName = GlobalUtil.getCommonSettings().getHostName();
					if (bsUserName.length() == 0)
						bsUserName = System.getenv("BS_USER");
					String bsKey = GlobalUtil.getCommonSettings().getKey();
					if (bsKey.length() == 0)
						bsKey = System.getenv("BS_KEY");

					URL = "https://" + bsUserName + ":" + bsKey + "@hub-cloud.browserstack.com/wd/hub";

					browser = new RemoteWebDriver(new URL(URL), capabilities);
				} catch (Exception e) {
					GlobalUtil.errorMsg = e.getMessage();
					LogUtil.htmlFailLog(GlobalUtil.errorMsg);
				}
				drivers.put(browserName, browser);

			} else {
				browser = drivers.get(browserName);
				if (browserName.equalsIgnoreCase(CHROME)) {
					if (browser == null) {
						WebDriverManager.chromedriver().setup();
						ChromeOptions chromeOptions = new ChromeOptions();
						chromeOptions.addArguments("--remote-allow-origins=*");
						browser = new ChromeDriver(chromeOptions);
						drivers.put("Chrome", browser);
					}
				}

				else if (browserName.equalsIgnoreCase(IE)) {
					if (browser == null) {
						WebDriverManager.iedriver().arch32().setup();
						InternetExplorerOptions ieO = new InternetExplorerOptions();
						ieO.setCapability("ie.ensureCleanSession", true);
						ieO.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
						browser = new InternetExplorerDriver(ieO);
						drivers.put("IE", browser);
					}
				}

				else if (browserName.equalsIgnoreCase(FIREFOX)) {
					if (browser == null) {
						WebDriverManager.firefoxdriver().setup();
						browser = new FirefoxDriver();
						drivers.put("Firefox", browser);
					}
				}

				else if (browserName.equalsIgnoreCase(EDGE)) {
					if (browser == null) {
						WebDriverManager.edgedriver().arch32().setup();
						browser = new EdgeDriver();
						drivers.put("Edge", browser);
					}
				}
			}
			browser.manage().window().maximize();
			LogUtil.infoLog(DriverUtil.class, GlobalUtil.getCommonSettings().getBrowser() + " : Browser Launched and Maximized.");
		} catch (Exception e) {
			LogUtil.errorLog(DriverUtil.class, "Browser not launched. Please check the configuration ", e);
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
		}
		return browser;
	}

	/**
	 * will get browser type and version
	 * 
	 * @param browser
	 * @param cap
	 * 
	 * @return
	 */
	public static String getBrowserAndVersion(WebDriver browser, DesiredCapabilities cap) {
		String browserversion;
		String windows = "Windows";
		String browsername = cap.getBrowserName();
		// This block to find out IE Version number
		if ("IE".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) browser).executeScript("return navigator.userAgent;");
			LogUtil.infoLog(DriverUtil.class, uAgent);
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains(windows)) {
				browserversion = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf(windows) - 2);
			} else if (uAgent.contains("Trident/7.0")) {
				browserversion = "11.0";
			} else {
				browserversion = "0.0";
			}
		} else {
			// Browser version for Firefox and Chrome
			browserversion = cap.getVersion();
		}
		String browserversion1 = browserversion.substring(0, browserversion.indexOf('.'));
		return browsername + " " + browserversion1;
	}

	/**
	 * will get operating system information
	 * 
	 * @return
	 */

	/**
	 * close all browsersw
	 * 
	 * @return
	 */
	public static void closeAllDriver() {

		drivers.entrySet().forEach(key -> {
			key.getValue().quit();
			key.setValue(null);
		});

		LogUtil.infoLog(DriverUtil.class, "Closing Browsers");
	}

	public static String getImgRef(String imgFile) {
		return new DriverUtil().getRefImage(imgFile);
	}

	private String getRefImage(String imgFile) {
		String openCVImgsFolder = "OpenCVImages/";
		URL refImgUrl = getClass().getClassLoader().getResource(openCVImgsFolder + imgFile + ".png");
		File refImgFile;
		try {
			refImgFile = Paths.get(refImgUrl.toURI()).toFile();
			System.out.println("File Found : " + refImgFile.exists());
			return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
		} catch (URISyntaxException | IOException e) {
			GlobalUtil.errorMsg = e.getMessage();
			LogUtil.htmlFailLog(GlobalUtil.errorMsg);
			return "";
		}
	}

	public static HashMap<String, String> getCheckLogin() {
		return checkLogin;
	}

	public static void setCheckLogin(HashMap<String, String> checkLogin) {
		DriverUtil.checkLogin = checkLogin;
	}

	public static void main(String[]args)
	{
		DriverUtil.invokeLocalMobileApp("Android", "pixel API_35");
	}
}

// End class
