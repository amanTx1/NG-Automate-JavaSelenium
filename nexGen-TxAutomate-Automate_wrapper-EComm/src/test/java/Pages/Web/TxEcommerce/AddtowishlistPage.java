
package Pages.Web.TxEcommerce;

import org.openqa.selenium.By;

public class AddtowishlistPage {
    public static By HelloSignInOption = By.xpath("//span[text()='Hello1, Sign In |']");
    public static By Login_option = By.xpath("(//span[text()='Login1'])[1]");
    public static By LoginPageHeading = By.xpath("(//span[text()='Login'])[2]");
    public static By Email = By.xpath("//*[@id='validationFormik01']");
    public static By Password = By.xpath("//*[@id='validationFormik02']");
    public static By LoginButton = By.xpath("(//span[text()='Login'])[3]");
    public static By firstProduct = By.xpath("//a[@href='/details' and img[@alt='product']]");
    public static By AccountAndListDropdown = By.xpath("//span[contains(text(),'Hello')]");
    public static By YourWishlist = By.xpath("//a[@href=\"/wishlist\"]");
    public static By WishlistPageTitle = By.xpath("//h1[@title='wishlist']");
    public static By WishlistProduct = By.xpath("//img[@alt='product']");
}
