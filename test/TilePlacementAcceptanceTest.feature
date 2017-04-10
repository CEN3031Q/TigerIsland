Feature: Tile Placement
  Players should be able to alternate placing tiles on the board

  Scenario: First turn placement
    Given There are no other tiles besides the first tile
    And And the first player has received their tile
    When That player chooses a valid location
    Then The tile is placed onto the board