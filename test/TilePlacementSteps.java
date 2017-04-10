import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.geometry.Point3D;
import org.junit.Assert;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by hugh on 4/9/17.
 */
public class TilePlacementSteps {
    @Given("^There are no other tiles besides the first tile$")
    public void thereAreNoOtherTiles() {
        Board board = new Board();
        boolean foundOtherTile = false;
        for(int x = 0; x < 200; x++){
            for(int y = 0; y < 200; y++){
                if(board.hexagonAtPoint(new Point(x, y)).getTileID() != 0 && board.hexagonAtPoint(new Point(x, y)).getTileID() != 1){
                    foundOtherTile = true;
                }
            }
        }
        Assert.assertFalse(foundOtherTile);
    }

    @And("^And the first player has received their tile$")
    public void andTheFirstPlayerHasReceivedTheirTile() throws Throwable {
        Player player1 = new Player("1", new Game("1"));
        Tile tile1 = new Tile(TerrainType.GRASS, TerrainType.JUNGLE);

        Assert.assertNotNull(tile1);
    }   

    @When("^That player chooses a valid location$")
    public void thatPlayerChoosesAValidLocation() {
        Board board = new Board();
        Tile tile = new Tile(TerrainType.GRASS, TerrainType.JUNGLE);
        Point3D playerLocationToPlace = new Point3D(1, 1, -2);

        boolean canPlace = board.canPlaceTileAtEdgeOffset(tile, Board.cubeToAxial(playerLocationToPlace));
        Assert.assertTrue(canPlace);
    }

    @Then("^The tile is placed onto the board$")
    public void theTileIsPlacedOntoTheBoard() {
        Board board = new Board();
        Tile tile = new Tile(TerrainType.GRASS, TerrainType.JUNGLE);
        Point3D playerLocationToPlace = new Point3D(1, 1, -2);

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tile.getOrientation());
        HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

        Point points[] = new Point[3];
        points[0] = board.boardPointForOffset(Board.cubeToAxial(playerLocationToPlace));
        points[1] = Board.pointTranslatedByPoint(points[0], abOffsets.get(HexagonPosition.A));
        points[2] = Board.pointTranslatedByPoint(points[0], abOffsets.get(HexagonPosition.B));

        board.placeTile(tile, playerLocationToPlace);

        Assert.assertEquals(TerrainType.VOLCANO, board.getTerrainTypeAtPoint(points[0]));
        Assert.assertEquals(TerrainType.GRASS, board.getTerrainTypeAtPoint(points[1]));
        Assert.assertEquals(TerrainType.JUNGLE, board.getTerrainTypeAtPoint(points[2]));
    }


    @Given("^There are at least two tiles on the board$")
    public void thereAreAtLeastTwoTilesOnTheBoard() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^A player chooses a spot on the second level$")
    public void aPlayerChoosesASpotOnTheSecondLevel() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The settlement below is not of size (\\d+)$")
    public void theSettlementBelowIsNotOfSize(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The hexagon below does not contain a Totoro$")
    public void theHexagonBelowDoesNotContainATotoro() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The hexagon below does not contain a Tiger$")
    public void theHexagonBelowDoesNotContainATiger() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^There is no empty space under the new tile$")
    public void thereIsNoEmptySpaceUnderTheNewTile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The volcano is on top of the bottom volcano$")
    public void theVolcanoIsOnTopOfTheBottomVolcano() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The tile is placed on the second level$")
    public void theTileIsPlacedOnTheSecondLevel(){

    }
}
