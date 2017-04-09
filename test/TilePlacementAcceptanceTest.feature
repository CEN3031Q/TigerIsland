Feature: Tile Placement
  Players should be able to alternate placing tiles on the board

  Scenario: First turn placement
    Given It is the first turn of the game
    And And the first player has received their tile
    When That player chooses a valid location
    Then The tile is placed onto the board

  Scenario: Second turn placement
    Given It is the second turn of the game
    And The first player has placed their tile
    When The second player chooses a new valid location
    Then The tile is placed onto the board

  Scenario: Second level tile placement
    Given There are tiles on the first level
    When A player chooses a valid space on the second level
    Then The tile is placed on top of the existing hexagons
