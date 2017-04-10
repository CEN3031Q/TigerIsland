Feature: Tile Placement
  Players should be able to alternate placing tiles on the board

  Scenario: First turn placement
    Given There are no other tiles besides the first tile
    And And the first player has received their tile
    When That player chooses a valid location
    Then The tile is placed onto the board

  Scenario: Second level placement
    Given There are at least two tiles on the board
    When A player chooses a spot on the second level
    And The settlement below is not of size 1
    And The hexagon below does not contain a Totoro
    And The hexagon below does not contain a Tiger
    And There is no empty space under the new tile
    And The volcano is on top of the bottom volcano
    Then The tile is placed on the second level
