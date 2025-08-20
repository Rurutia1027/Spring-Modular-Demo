Feature: Short Link management

  Scenario: Greet a user by name to Hello endpoint
    Given the name "Alice"
    When I call the hello endpoint
    Then the response should be "Hello Alice"