Gherkin
Feature: ParaBank Login Functionality

  Scenario: Successful login with valid credentials
    Given the user is on the ParaBank login page
    When the user enters a valid username and password
    And clicks on the login button
    Then the user should be redirected to the account overview page