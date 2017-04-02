import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;

/**
 * Created by hugh on 3/22/17.
* Acceptance tests for placing a tile onto the board
* May change if we decide to change the implementation of the hexagon system
* TODO: Add the drawing of a tile
*/

public class TilePlacementSteps {
    UserInteractor userInteractor = new UserInteractor();
    Board gameBoard = new Board();
    Player daveLarge = new Player(1, userInteractor);
    final int maxBoardDimension = 400;


    // Test for an emptpy board by checking the level of every space is 0
    @Given("^The board is empty$")
    public void theBoardIsEmpty(){
        boolean isBoardEmpty = true;
        for(int i = 0; i < maxBoardDimension; ++i){
            for(int j = 0; j < maxBoardDimension; ++j){
                if (gameBoard.getLevelAtPoint(new Point(i, j)) != 0){
                    isBoardEmpty = false;
                }
            }
        }

        Assert.assertTrue(isBoardEmpty);
    }

    // Test that a player has a tile in their hand
    @And("^The player has drawn a tile$")
    public void playerHasDrawn(){
        // Simulate drawing of a tile
        Tile playerTile = new Tile(TerrainType.VOLCANO, TerrainType.GRASSLANDS, TerrainType.JUNGLE);
        daveLarge.tileInHand = playerTile;

        Assert.assertNotNull(daveLarge.tileInHand);
    }

    // Test that a player chooses a valid location
    // Maybe rewrite this test
    @When("^The player chooses a location$")
    public void hasChosenLocation(){
        // Not sure how to get location with Player or Action classes
        int x = 200;
        int y = 200;

        Assert.assertTrue((x >= 0 && x < maxBoardDimension));
        Assert.assertTrue((y >= 0 && y < maxBoardDimension));
    }

    // Test that the tile gets placed on the board
    @Then("^The tile is placed on the board$")
    public void tilePlaced(){
        Tile playerTile = new Tile(TerrainType.VOLCANO, TerrainType.GRASSLANDS, TerrainType.JUNGLE);

        Point testCoordinate = new Point(200, 200);
        gameBoard.placeTile(playerTile, testCoordinate);

        Assert.assertEquals(1, gameBoard.getLevelAtPoint(new Point(200,200)));
        Assert.assertEquals(1, gameBoard.getLevelAtPoint(new Point(199,199)));
        Assert.assertEquals(1, gameBoard.getLevelAtPoint(new Point(201,199)));
    }
}