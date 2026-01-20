# @EcommerceApp
# Feature: Ecommerce site tests.

#   @Webtests 
#   Scenario: Login User with correct email and password
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Signup / Login" displayed on page
#     And Enter EComm Email "dsanjay902700+1@gmail.com" and Password "Sanjay" on AI engine
#     And Click on "SignIn" displayed on page
#     Then Verify "Logged in as button" is displayed

#   @Webtests @EComm
#   Scenario: Login User with incorrect email and password
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Signup / Login" displayed on page
#     And Enter EComm Email "dsanjay902700+111@gmail.com" and Password "Sanjay123" on AI engine
#     And Click on "SignIn" displayed on page
#     Then Verify "Your email or password is incorrect!" is displayed


#   @Webtests @EComm
#   Scenario: Register User with existing email show error message
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Signup / Login" displayed on page
#     Then Verify "New User Signup!" is displayed
#     When Enter Name "Sanjay" and Email "dsanjay902700+1@gmail.com"
#     And Click on "SignUp" displayed on page
#     Then Verify "Email Address already exist!" is displayed


#   @Webtests @EComm
#   Scenario Outline:  Verify search is working on product page.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Products" displayed on page
#     Then Verify "ALL PRODUCTS" is displayed
#     When Enter product name "<ProductName>" in search input
#     And Click on "Search Button" displayed on page
#     Then Verify "SEARCHED PRODUCTS" is displayed
#     When Verify all the products are related to searched "<ProductName>" are visible
#     Examples:
#       | ProductName |
#       | Saree       |


#   @Webtests @EComm
#   Scenario Outline: Verify user able to add product in cart from search result.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Products" displayed on page
#     Then Verify "ALL PRODUCTS" is displayed
#     When Enter product name "<ProductName>" in search input
#     And Click on "Search Button" displayed on page
#     Then Verify "SEARCHED PRODUCTS" is displayed
#     When Click on "Add to cart" button for product with index "2"
#     Then Verify "Your product has been added to cart." is displayed
#     And Verify "View cart link" is displayed

#     Examples:
#       | ProductName |
#       | Saree       |


#   @Webtests @EComm
#   Scenario: Verify added product details on cart page.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Navigate to ecommerce website url
#     And Click on "Products" displayed on page
#     Then Verify "ALL PRODUCTS" is displayed
#     When Click on "View product" button for product with index "2"
#     And Get Product Name ,Price and Enter quantity "5"
#     And Click on "Add to cart on product details" displayed on page
#     And Click on "Continue Shopping" displayed on page
#     And Click on "Cart" displayed on page
#     Then Validate added product details on cart page


#   @Webtests @EComm
#   Scenario: Test to verify add product, check out product and complete payment to place order.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Signup / Login" displayed on page
#     And Enter EComm Email "dsanjay902700+1@gmail.com" and Password "Sanjay" on AI engine
#     And Click on "SignIn" displayed on page
#     Then Verify "Logged in as button" is displayed
#     And Click on "Products" displayed on page
#     Then Verify "ALL PRODUCTS" is displayed
#     When Click on "View product" button for product with index "1"
#     And Click on "Add to cart on product details" displayed on page
#     And Click on "View cart link" displayed on page
#     And Click on "Proceed to checkout" displayed on page
#     And Click on "Place Order" displayed on page
#     Then Verify "Payments page" is displayed
#     When Enter card details "Name on card" is "Sanjay"
#     And Enter card details "Card number" is "123454673312"
#     And Enter card details "CVC" is "123"
#     And Enter card details "Expiry Month" is "12"
#     And Enter card details "Expiry Year" is "2026"
#     And Click on "Pay and Confirm Order" displayed on page
#     Then Verify "Congratulations! Your order has been confirmed!" is displayed


#   @Webtests @EComm
#   Scenario: Test to verify user registration is working fine.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Click on "Signup / Login" displayed on page
#     Then Verify "New User Signup!" is displayed
#     When Enter Name "Sanjay" and Email "dsanjay902700+20@gmail.com"
#     And Click on "SignUp" displayed on page
#     And Click on "Mr check box" displayed on page
#     And Enter "Sanjay@12345" in "Password" registration page
#     And Select "Day" value "12" from dropdown
#     And Select "Month" value "March" from dropdown
#     And Select "Year" value "2008" from dropdown
#     And Enter "Sanjay" in "FirstName" registration page
#     And Enter "Sharma" in "LastName" registration page
#     And Enter "Tx" in "Company" registration page
#     And Enter "2nd Sector" in "Address1" registration page
#     And Enter "HSR" in "Address2" registration page
#     And Select "Country" value "India" from dropdown
#     And Enter "Karnataka" in "State" registration page
#     And Enter "Bangalore" in "City" registration page
#     And Enter "560068" in "Zipcode" registration page
#     And Enter "7011001100" in "Mobile" registration page
#     And Click on "CreateAccount button" displayed on page
#     Then Verify "Account Created" is displayed


#   @Webtests @EComm
#   Scenario: Verify user can remove added product from cart.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Navigate to ecommerce website url
#     And Click on "Products" displayed on page
#     Then Verify "ALL PRODUCTS" is displayed
#     When Click on "View product" button for product with index "2"
#     And Get Product Name ,Price and Enter quantity "5"
#     And Click on "Add to cart on product details" displayed on page
#     And Click on "Continue Shopping" displayed on page
#     And Click on "Cart" displayed on page
#     And Remove the added product from the cart
#     Then Verify added product is removed


#   @Webtests @EComm @Test
#   Scenario: Verify user can add review for a product.
#     Given Navigate to the AI Engine url "https://automationexercise.com/"
#     When Navigate to ecommerce website url
#     And Click on "Products" displayed on page
#     Then Verify "ALL PRODUCTS" is displayed
#     When Click on "View product" button for product with index "2"
#     Then Verify "Write Your Review" is displayed
#     When Enter name "Sanjay", Email "xyz@gmail.com" and review "Nice Product !"
#     And Click on "Submit Review" displayed on page
