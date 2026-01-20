Feature: Verify Add to Wishlist functionality

@Webtests 
  Scenario: Test to check if user is able to create a wishlist from the products detail page
    Given Navigate to the AI Engine url "http://3.94.149.173:9090/"
    And Click on Hello,SignIn option
    Then Click on "Login" option
    And Verify "Login" Page Heading is displayed
    And Enter Email "jane@testingxperts.com" and password "123456" on AI Engine
    And Click on "Login" button
    Then Verify user is successfully logged in
    And Click on first product
    Then Scroll And Click on the Account and List Dropdown
    And Click on "Your Wishlist" option from Account and list dropdown
    Then verify "page title" is displayed on "Your WishList" page
    Then Verify product should be in wishlist



