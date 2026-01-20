Feature: Logout Page

 @Mobiletests
   Scenario: Successful_logout_with_valid_credentials
    Given User click on okButton
    When User enter "company" as username
    And User enter "company" as password
    And User click on "login" button
    Then User click on logout button