Feature: AI Places Totoros

  Scenario: AI decides to place a Totoro
    Given There is a settlement of size 5
    And an eligible Totoro spot around it
    Then the AI decides to place a Totoro at that spot