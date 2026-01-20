package utilities;

public class MobXpaths {

    public static String AppNamePath = "//android.widget.TextView[@content-desc='Predicted app: EriBank']";
    public static String UserName = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/usernameTextField\"] | //XCUIElementTypeTextField[@name=\"usernameTextField\"]";
    public static String Password = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/passwordTextField\"] | //XCUIElementTypeSecureTextField[@name=\"passwordTextField\"]";
    public static String LoginButton = "//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/loginButton\"] | //XCUIElementTypeButton[@name=\"loginButton\"]";
    public static String MakePaymentButton = "//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/makePaymentButton\"] | //XCUIElementTypeButton[@name=\"makePaymentButton\"]";
    public static String MortgageRequestButton = "//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/mortageRequestButton\"] | //XCUIElementTypeButton[@name=\"Mortgage Request\"]";
    public static String LogoutButton = "//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/logoutButton\"] | //XCUIElementTypeButton[@name=\"logoutButton\"]";
    public static String PhoneTextField = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/phoneTextField\"] | //XCUIElementTypeTextField[@name=\"phoneTextField\"]";
    public static String NameTextField = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/nameTextField\"] | //XCUIElementTypeTextField[@name=\"nameTextField\"]";
    public static String AmountTextField = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/amountTextField\"] | //XCUIElementTypeTextField[@name=\"amountTextField\"]";
    public static String CountryTextField = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/countryTextField\"] | //XCUIElementTypeTextField[@name=\"countryTextField\"]";
    public static String SendPaymentButton = "//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/sendPaymentButton\"] | //XCUIElementTypeButton[@name=\"sendPaymentButton\"]";
    public static String FirstName = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/nameTextField\"] | //XCUIElementTypeTextField[@name=\"firstNameTextField\"]";
    public static String LastName = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/lastNameTextField\"] | //XCUIElementTypeTextField[@name=\"lastNameTextField\"]";
    public static String Age = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/ageTextField\"] | //XCUIElementTypeTextField[@name=\"ageTextField\"]";
    public static String Address1TextField = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/addressOneTextField\"] | //XCUIElementTypeTextField[@name=\"addressOneTextField\"]";
    public static String Address2TextField = "//android.widget.EditText[@resource-id=\"com.experitest.ExperiBank:id/addressTwoTextField\"] | //XCUIElementTypeTextField[@name=\"addressTwoTextField\"]";
    public static String NextButton = "//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/nextButton\"] | //XCUIElementTypeButton[@name=\"nextButton\"]";
    public static String okButton="//android.widget.Button[@resource-id=\"android:id/button1\"]";
    public static String HomeLogo="//android.widget.ImageView | //XCUIElementTypeImage[@name=\"logo.png\"]";
    public static String yesButton="//android.widget.Button[@resource-id=\"android:id/button1\"] | //XCUIElementTypeButton[@name=\"Yes\"]";

    public static String accountBalance="//android.widget.TextView[@text=\"Your balance is: 100.00$\"] | //android.widget.TextView[@text=\"Your balance is: 90.00$\"] | //XCUIElementTypeStaticText[@name=\"100.00$\"] | //XCUIElementTypeStaticText[@name=\"90.00$\"]";
    public static String loanType="//android.widget.CheckedTextView[@resource-id=\"com.experitest.ExperiBank:id/rowTextView\" and @text=\"Car\"] | //XCUIElementTypeStaticText[@name=\"Car\"]";
    public static String numberOfYears="//android.widget.CheckedTextView[@resource-id=\"com.experitest.ExperiBank:id/rowTextView\" and @text=\"5\"] | //XCUIElementTypeStaticText[@name=\"5\"]";
    public static String typeOfOccupation="//android.widget.CheckedTextView[@resource-id=\"com.experitest.ExperiBank:id/rowTextView\" and @text=\"Private Job\"] | //XCUIElementTypeStaticText[@name=\"Private Job\"]";
    public static String yearlyIncome="//android.widget.CheckedTextView[@resource-id=\"com.experitest.ExperiBank:id/rowTextView\" and @text=\"1,000,000\"] | //XCUIElementTypeStaticText[@name=\"2,500,000\"]";
    public static String saveButton="//android.widget.Button[@resource-id=\"com.experitest.ExperiBank:id/saveButton\"] | //XCUIElementTypeButton[@name=\"saveButton\"]";


}
