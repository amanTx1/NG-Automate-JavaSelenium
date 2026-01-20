Feature: Make Payment functionality

 @Mobiletests
   Scenario: EB003 : Verify make payment functionality
    Given User click on okButton
    When User enter "company" as username
    And User enter "company" as password
    And User click on "login" button
    And Fetch the Account balance
    Then User click on MakePayment
    And User enter Phone Number as "9999988888", Name as "Test", Amount as "10" and Country as "India"
    Then User click on SendPayment
    And User click on YesButton
    And verify account balance after payment of 10.0
    And Fetch the Account balance
    Then validate amount after payment

