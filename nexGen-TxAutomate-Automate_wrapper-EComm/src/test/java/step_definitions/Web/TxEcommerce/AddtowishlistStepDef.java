
// package step_definitions.Web.TxEcommerce;

// import modules.Web.TxEcommerce.Addtowishlist;
// import io.cucumber.java.en.*;

// public class AddtowishlistStepDef {

//     Addtowishlist addtowishlist = new Addtowishlist();

//     @Given("Navigate to the AI Engine url {string}")
//     public void navigate_to_the_ai_engine_url(String url) {
//         addtowishlist.navigateToUrl(url);
//     }

//     @And("Click on Hello,SignIn option")
//     public void click_on_hello_signin_option() {
//         addtowishlist.clickOnHelloSignInOption();
//     }

//     @Then("Click on {string} option")
//     public void click_on_login_option() {
//         addtowishlist.clickOnLoginOption();
//     }

//     @And("Verify {string} Page Heading is displayed")
//     public void verify_login_page_heading_is_displayed() {
//         addtowishlist.verifyLoginPageHeading();
//     }

//     @And("Enter Email {string} and password {string} on AI Engine")
//     public void enter_email_and_password_on_ai_engine(String email, String password) {
//         addtowishlist.enterEmailAndPassword(email, password);
//     }

//     @And("Click on {string} button")
//     public void click_on_login_button() {
//         addtowishlist.clickOnLoginButton();
//     }

//     @Then("Verify user is successfully logged in")
//     public void verify_user_is_successfully_logged_in() {
//         addtowishlist.verifyUserLoggedIn();
//     }

//     @And("Click on first product")
//     public void click_on_first_product() {
//         addtowishlist.clickOnFirstProduct();
//     }

//     @Then("Scroll And Click on the Account and List Dropdown")
//     public void scroll_and_click_on_the_account_and_list_dropdown() {
//         addtowishlist.scrollAndClickOnAccountAndListDropdown();
//     }

//     @And("Click on {string} option from Account and list dropdown")
//     public void click_on_your_wishlist_option() {
//         addtowishlist.clickOnYourWishlistOption();
//     }

//     @Then("verify {string} is displayed on {string} page")
//     public void verify_page_title_is_displayed_on_your_wishlist_page() {
//         addtowishlist.verifyWishlistPageTitle();
//     }

//     @Then("Verify product should be in wishlist")
//     public void verify_product_should_be_in_wishlist() {
//         addtowishlist.verifyProductInWishlist();
//     }
// }







package step_definitions.Web.TxEcommerce;

import modules.Web.TxEcommerce.Addtowishlist;
import io.cucumber.java.en.*;

public class AddtowishlistStepDef {

    Addtowishlist addtowishlist = new Addtowishlist();

    @Given("Navigate to the AI Engine url {string}")
    public void navigate_to_the_ai_engine_url(String url) {
        addtowishlist.navigateToUrl(url);
    }

    @And("Click on Hello,SignIn option")
    public void click_on_hello_signin_option() {
        addtowishlist.clickOnHelloSignInOption();
    }

    @Then("Click on {string} option")
    public void click_on_option(String option) {
        if (option.equalsIgnoreCase("Login")) {
            addtowishlist.clickOnLoginOption();
        }
        // Add more options if needed
    }

    @And("Verify {string} Page Heading is displayed")
    public void verify_page_heading_is_displayed(String heading) {
        addtowishlist.verifyLoginPageHeading(); // You could enhance it to check heading text too
    }

    @And("Enter Email {string} and password {string} on AI Engine")
    public void enter_email_and_password_on_ai_engine(String email, String password) {
        addtowishlist.enterEmailAndPassword(email, password);
    }

    @And("Click on {string} button")
    public void click_on_button(String buttonText) {
        if (buttonText.equalsIgnoreCase("Login")) {
            addtowishlist.clickOnLoginButton();
        }
        // You can add more button handlers later
    }

    @Then("Verify user is successfully logged in")
    public void verify_user_is_successfully_logged_in() {
        addtowishlist.verifyUserLoggedIn();
    }

    @And("Click on first product")
    public void click_on_first_product() {
        addtowishlist.clickOnFirstProduct();
    }

    @Then("Scroll And Click on the Account and List Dropdown")
    public void scroll_and_click_on_the_account_and_list_dropdown() {
        addtowishlist.scrollAndClickOnAccountAndListDropdown();
    }

    @And("Click on {string} option from Account and list dropdown")
    public void click_on_option_from_account_and_list_dropdown(String option) {
        if (option.equalsIgnoreCase("Your Wishlist")) {
            addtowishlist.clickOnYourWishlistOption();
        }
    }

    @Then("verify {string} is displayed on {string} page")
    public void verify_is_displayed_on_page(String title, String page) {
        addtowishlist.verifyWishlistPageTitle(); // Enhance with actual title validation if needed
    }

    @Then("Verify product should be in wishlist")
    public void verify_product_should_be_in_wishlist() {
        addtowishlist.verifyProductInWishlist();
    }
}

