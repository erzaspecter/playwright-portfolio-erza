Feature: Login Functionality
  As a user of the SauceLabs demo site
  I want to be able to log in
  So that I can access the inventory and make purchases

  Background:
    Given I am on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When I enter username "standard_user" and password "secret_sauce"