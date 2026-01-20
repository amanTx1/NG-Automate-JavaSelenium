package utilities;

import org.openqa.selenium.By;

public class WebXpaths {

    // TxEcommerce xpaths
   public static By HelloSignInOption = By.xpath("//span[text()='Hello, Sign In |']");
   public static By AccountAndListDropdown = By.xpath("//span[contains(text(),'Hello')]");
   public static By HomeLogo = By.xpath("//img[@class='navbar-brand']");
   public static By AllDropdown = By.xpath("//button[@type='button']//strong");
   public static By Login_option = By.xpath("(//span[text()='Login'])[1]");
   public static By LogoutOption = By.xpath("//a[text()='Logout']");
   public static By LoginPageHeading = By.xpath("(//span[text()='Login'])[2]");
   public static By SignUpNowLink = By.xpath("//span[text()='Sign up now']");
   public static By SignUpPageHeading = By.xpath("//*[@id='contained-modal-title-vcenter']");
   public static By UserName = By.xpath("//*[@id='validationFormik00']");

   public static By Email = By.xpath("//*[@id='validati/onFormik01']");
   public static By Password = By.xpath("//*[@id='validationFormik02']");
   public static By SignUpButton = By.xpath("//button[text()='Signup']");
   public static By LoginButton = By.xpath("(//span[text()='Login'])[3]");
   public static By ItemAddedToCart = By.xpath("//*[@id=\"hdItemCart\"]");
   public static By product_info = By.xpath("//img[contains(@src, 'Jager-Smith.png')]");
   public static By moduleLogin_button = By.xpath("(//span[text()='Login'])[3]");
   public static By AddToCart = By.xpath("//button[text()='Add To Cart']");
   public static By GoToCart = By.xpath("//div[@id='modal']//a[@href=\"/cart\"]");
   public static By YourCart = By.xpath("//a[@href='/cart']");
   public static By ClearCart = By.xpath("//button[text()='Clear Cart']");
   public static By YourOrders = By.xpath("//a[@href='/orders']");
   public static By YourWishlist = By.xpath("//a[@href=\"/wishlist\"]");
   public static By ProceedToCheckout = By.xpath("//*[@class='proceedCheckoutClearCartButton proceedCheckoutClearCartButton1 mb-3']");
   public static By FrameLocator = By.xpath("//iframe[@title='PlacedOrderIframe']");
   public static By PlaceOrder= By.xpath("//button[text()='Place Order']");
   public static By firstProduct = By.xpath("//a[@href='/details' and img[@alt='product']]");
   public static By PageTitle = By.xpath("//h1[@class='text-title']");
   public static By WishlistPageTitle = By.xpath("//h1[@title='wishlist']");
   public static By WishlistProduct = By.xpath("//img[@alt='product']");
   public static By paginationFunctionality = By.xpath("(//button[@class='pagination-button'])[2]");
   public static By searchProductField = By.xpath("//input[@class=\"searchbar-input\"]");
   public static By searchProductFieldValue = By.xpath("//input[@class='searchbar-input']/@value");


   //Reach
//    public static By SignInPageHeading = By.xpath("//h4[text()='Sign In']");
//    public static By AccountID = By.xpath("//input[@id='accountId']");
//    public static By SignInButton = By.xpath("//button[text()='Sign In']");
//    public static By Email = By.xpath("//input[@id='emailId']");
//    public static By Password = By.xpath("//input[@id='password']");
//    public static By WelcomeText = By.xpath("//div[contains(text(),'Welcome, ')]");
//    public static By ProfileIcon= By.xpath("//span[@class='reach-cs-arrow-down']");
//    public static By FlightMobile= By.xpath("//div[text()='Flight Mobile']");
//    public static By Dashboards= By.xpath("//h3[text()='Dashboards']");
//    public static By CustomerSuccessDashboard=By.xpath("//h3[text()='Customer Success Dashboard']");
//    public static By OperationsDashboard=By.xpath("//h3[text()='Operations Dashboard']");

    //EComm
    //HomePage
//     public static By navBarButton(String buttonName) {
//         return By.xpath("//a[contains(text(),'" + buttonName + "')]");
//     }

//     public static By signUpLoginBtn = By.xpath("//a[contains(text(),' Signup / Login')]");
//     public static By productsBtn = By.xpath("//a[contains(text(),' Products')]");

//     //Login
//     public static By loginEmailTxtBox = By.xpath("//input[@data-qa='login-email']");
//     public static By loginPasswordTxtBox = By.xpath("//input[@data-qa='login-password']");
//     public static By loginBtn = By.xpath("//button[@data-qa='login-button']");
//     public static By loginAsBtn = By.xpath("//a[contains(text(),'Logged in as ')]");
//     public static By logoutBtn = By.xpath("//a[text()=' Logout']");

//     public static By deleteAccountBtn = By.xpath("//a[text()=' Delete Account']");
//     public static By invalidCredText = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

//     public static By errorMessage(String message) {
//         return By.xpath("//p[contains(text(),'" + message + "')]");
//     }

//     public static By textElement(String text) {
//         return By.xpath("//*[contains(text(),'" + text + "')]");
//     }

//     public static By nameSignUpInputBox = By.xpath("//input[@data-qa='signup-name']");
//     public static By emailSignUpInputBox = By.xpath("//input[@data-qa='signup-email']");
//     public static By signUpBtn = By.xpath("//button[@data-qa='signup-button']");

//     //ProductPage
//     public static By productNameOnCard(int index) {
//         return By.xpath("(//div[@class='single-products']/div/p)[" + index + "]");
//     }

//     public static By searchBox = By.xpath("//input[@id='search_product']");
//     public static By searchBtn = By.xpath("//button[@id='submit_search']");

//     public static By allProductsText = By.xpath("//h2[contains(@class,'title') and text()='All Products']");
//     public static By searchedProductsText = By.xpath("//h2[contains(@class,'title') and text()='Searched Products']");

//     public static By productsCards = By.xpath("//div[@class='single-products']");

//     public static By addToCartBtn(int index) {
//         return By.xpath("(//div[contains(@class,'productinfo ')]/a[contains(@class,'add-to-cart')])[" + index + "]");
//     }


//     // Add to cart confirmation popup locators
//     public static By confirmationMsgForProductAdded = By.xpath("//p[text()='Your product has been added to cart.']");
//     public static By viewCartLink = By.xpath("//a/u[text()='View Cart']");
//     public static By continueShoppingBtn = By.xpath("//button[text()='Continue Shopping']");
// // product details

//     public static By viewProductBtn(int index) {
//         return By.xpath("(//a[text()='View Product'])[" + index + "]");
//     }

//     public static By addToCartBtnProductDetails = By.xpath("//button[contains(@class,'cart')]");
//     public static By productTitle = By.xpath("//div[@class='product-information']/h2");
//     public static By proceedToCheckoutBtn = By.xpath("//a[text()='Proceed To Checkout']");
//     public static By placeOrderBtn = By.xpath("//a[text()='Place Order']");


//     //payment
//     public static By paymentPageTitle = By.xpath("//h2[text()='Payment' and @class='heading']");
//     public static By nameOnCardTextBox = By.xpath("//input[@data-qa='name-on-card']");
//     public static By cardNumberTxtBox = By.xpath("//input[@data-qa='card-number']");
//     public static By cvcTxtBox = By.xpath("//input[@data-qa='cvc']");
//     public static By expiryMonthTxtBox = By.xpath("//input[@data-qa='expiry-month']");
//     public static By expiryYearTxtBox = By.xpath("//input[@data-qa='expiry-year']");
//     public static By payBtn = By.xpath("//button[@data-qa='pay-button']");
//     public static By orderPlacedText = By.xpath("//h2[@data-qa='order-placed']/following-sibling::p");

//     //cart page
//     public static By productsInCart = By.xpath("//tr/td[@class='cart_description']");

//     public static By cartDescription(int index) {
//         return By.xpath("(//td[@class='cart_description']/h4/a)[" + index + "]");
//     }

//     public static By cartPrice(int index) {
//         return By.xpath("(//td[@class='cart_price']/p)[" + index + "]");
//     }

//     public static By cartQuantity(int index) {
//         return By.xpath("(//td[@class='cart_quantity']/button)[" + index + "]");
//     }

//     public static By cartDeleteBtn(int index) {
//         return By.xpath("(//td[@class='cart_delete']/a)[" + index + "]");
//     }


//     // product details
//     public static By quantityBox = By.xpath("//input[@id='quantity']");
//     public static By productNameProductDetails = By.xpath("//div[@class='product-information']/h2");
//     public static By productPriceProductDetails = By.xpath("//div[@class='product-information']/span/span[contains(text(),'Rs.')]");

//     public static By cartBtnNavBar = By.xpath("//a[text()=' Cart']");
//     public static By conitnueShoppingBtn = By.xpath("//button[text()='Continue Shopping']");

//     //Register Page
//     public static By mrCheckBox = By.xpath("//input[contains(@id,'id_gender') and @value='Mr']");
//     public static By mrsCheckBox = By.xpath("//input[contains(@id,'id_gender') and @value='Mrs']");
//     public static By passwordRegisterTxtBox = By.xpath("//input[@id='password']");
//     public static By dobDayDD = By.xpath("//select[@id='days']");
//     public static By dobMonthDD = By.xpath("//select[@id='months']");
//     public static By dobYearDD = By.xpath("//select[@id='years']");

//     public static By firstNameInputBox = By.xpath("//input[@id='first_name']");
//     public static By lastNameInputBox = By.xpath("//input[@id='last_name']");
//     public static By companyInputBox = By.xpath("//input[@id='company']");
//     public static By address1InputBox = By.xpath("//input[@id='address1']");
//     public static By address2InputBox = By.xpath("//input[@id='address2']");
//     public static By countryDD = By.xpath("//select[@id='country']");
//     public static By stateInputBox = By.xpath("//input[@id='state']");
//     public static By cityInputBox = By.xpath("//input[@id='city']");
//     public static By zipcodeInputBox = By.xpath("//input[@id='zipcode']");
//     public static By createAccountBtn = By.xpath("//button[@data-qa='create-account']");
//     public static By mobileInputBox = By.xpath("//input[@id='mobile_number']");
//     public static By emptyCartText = By.xpath("//b[text()='Cart is empty!']");

//     public static By writeReviewText = By.xpath("//a[text()='Write Your Review']");
//     public static By yourNameTxtBox = By.xpath("//input[@placeholder='Your Name']");
//     public static By emailTxtBoxReview = By.xpath("//input[@placeholder='Email Address']");
//     public static By reviewInputBox = By.xpath("//textarea[@id='review']");

//     public static By submitReview=By.xpath("//button[@id='button-review']");
//     public static By confirmationForReview = By.xpath("//*[contains(text(),'Thank you for your review')]");
//     public static By accountCreatedText = By.xpath("//h2/b[text()='Account Created!']");
}
