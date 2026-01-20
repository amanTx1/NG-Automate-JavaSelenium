Feature: Login Page

 @Mobiletests
   Scenario: Successful_login_with_valid_credentials
    Given User click on okButton
    When User enter "company" as username
    And User enter "company" as password
    And User click on "login" button
    Then The HomePage will be opened and homelogo is visible

