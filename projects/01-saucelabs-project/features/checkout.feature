Feature: Checkout

  @checkout
  Scenario: Complete purchase with valid data
    Given I am logged in
    And I have added "Sauce Labs Backpack" to the cart
    When I proceed to checkout and submit info "John","Doe","12345"
    Then I should see the order confirmation