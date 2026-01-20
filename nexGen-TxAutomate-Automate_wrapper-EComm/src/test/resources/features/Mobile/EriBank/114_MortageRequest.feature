Feature: Mortage request functionality
 
 @Mobiletests
   Scenario: EB004 : Verify Mortgage request functionality
    Given User click on okButton
    When User enter "company" as username
    And User enter "company" as password
    And User click on "login" button
    Then User click on Mortgage Request button
    And User enter FirstName "Test"
    And User enter LastName "123"
    And User enter Age "25"
    And User enter Address1 "Mall Road"
    And User enter Address2 "Bengaluru"
    And User enter country "India"
    And User enter Amount "1000"
    Then User click on Next button
    And User click on loanType
    And User click on numberOfYears
    And User scroll down till end
    And User click on typeOfOccupation
    And User click on yearlyIncome
    And User click on saveButton
