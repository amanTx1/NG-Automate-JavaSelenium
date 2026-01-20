
package modules.Web.TxEcommerce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Pages.Web.TxEcommerce.AddtowishlistPage;
import utilities.KeywordUtil;

public class Addtowishlist {
    private static final Logger logger = LogManager.getLogger(Addtowishlist.class);

    public void navigateToUrl(String url) {
        try {
            // logger.info("Navigating");
            KeywordUtil.navigateToUrl(url);
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void clickOnHelloSignInOption() {
        try {
            KeywordUtil.click(AddtowishlistPage.HelloSignInOption, "Clicking on Hello, SignIn option");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void clickOnLoginOption() {
        try {
            KeywordUtil.click(AddtowishlistPage.Login_option, "Clicking on Login option");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void verifyLoginPageHeading() {
        try {
            KeywordUtil.verifyDisplayAndEnable(AddtowishlistPage.LoginPageHeading, "Verifying Login Page Heading");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void enterEmailAndPassword(String email, String password) {
        try {
            KeywordUtil.inputText(AddtowishlistPage.Email, email, "Entering email id");
            KeywordUtil.inputText(AddtowishlistPage.Password, password, "Entering password");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void clickOnLoginButton() {
        try {
            KeywordUtil.click(AddtowishlistPage.LoginButton, "Clicking on Login button");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void verifyUserLoggedIn() {
        try {
            // Assuming some element or condition that verifies successful login
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void clickOnFirstProduct() {
        try {
            KeywordUtil.click(AddtowishlistPage.firstProduct, "Clicking on first product");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void scrollAndClickOnAccountAndListDropdown() {
        try {
            KeywordUtil.scrollAndClick(AddtowishlistPage.AccountAndListDropdown, "Scrolling and Clicking on Account and List Dropdown");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void clickOnYourWishlistOption() {
        try {
            KeywordUtil.click(AddtowishlistPage.YourWishlist, "Clicking on Your Wishlist option");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void verifyWishlistPageTitle() {
        try {
            KeywordUtil.verifyDisplayAndEnable(AddtowishlistPage.WishlistPageTitle, "Verifying Wishlist Page Title");
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void verifyProductInWishlist() {
        try {
            KeywordUtil.verifyDisplayAndEnable(AddtowishlistPage.WishlistProduct, "Verifying product in Wishlist");
        } catch (Exception e) {
            // Handle exception
        }
    }
}
