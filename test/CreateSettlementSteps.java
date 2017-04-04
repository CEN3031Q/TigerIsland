import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.Assert;

import java.awt.*;

/**
 * Created by hugh on 3/24/17.
 * Acceptance tests for making a new settlement
 */

public class CreateSettlementSteps {
    Board gameBoard = new Board();

    UserInteractor userInteractor = new UserInteractor();
    Player daveXtraLarge = new Player(1, userInteractor);

    final int maxBoardDimension= 400;
    Tile existingTile = new Tile(TerrainType.VOLCANO, TerrainType.GRASSLANDS, TerrainType.JUNGLE);


    // Tests for the existance of tiles
    @Given("^There is at least one tile on the board$")
    public void thereIsAtLeastOneTileOnTheBoard() {
        Point testCoordinate = new Point(200,200);
        gameBoard.placeTile(existingTile, testCoordinate);
        boolean isBoardEmpty = true;

        for(int i = 0; i < maxBoardDimension; ++i){
            for(int j = 0; j < maxBoardDimension; ++j){
                Point p = new Point(i, j);
                if (gameBoard.getLevelAtPoint(p) != 0){
                    isBoardEmpty = false;
                }
            }
        }

        Assert.assertFalse(isBoardEmpty);
    }

    // Tests that the coordinates are on the first level
    @When("^The player chooses a valid location$")
    public void playerChoosesValidLocation(){
        int x = 200;
        int y = 200;

        Point p = new Point(200, 200);
        Assert.assertEquals(1, gameBoard.getTileIDAtPoint(p));
    }

    @Then("^The location receives 1 villager$")
    public void locationReceivesVillager() {
        Point p = new Point(200, 200);
        gameBoard.setVillagersAtPoint(1, p);
    }

    @And("^The player removes 1 villager from their inventory$")
    public void playerRemovesVillagerFromInventory(){
        int numVillagersBefore = daveXtraLarge.getMeepleSize();
        daveXtraLarge.placeMeeplePiece();
        int numVillagersAfter = daveXtraLarge.getMeepleSize();
        Assert.assertNotEquals(numVillagersBefore, numVillagersAfter);
    }

}