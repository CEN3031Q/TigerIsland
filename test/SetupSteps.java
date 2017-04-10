import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.awt.*;

/**
 * Created by hugh on 4/9/17.
 */
public class SetupSteps {
    @Given("^It is the start of a new game$")
    public void itIsTheStartOfANewGame() {
        Game game1 = new Game("1");
        Assert.assertNotNull(game1);
    }

    @Then("^Both of the players should be created$")
    public void bothOfThePlayersShouldBeCreated() {
        Game game1 = new Game("1");

        Player player1 = new Player("1", game1);
        Inventory player1Inventory = new Inventory(player1.getPlayerID());

        Player player2 = new Player("2", game1);
        Inventory player2Inventory = new Inventory(player2.getPlayerID());

        Assert.assertNotEquals(player1, player2);
        Assert.assertNotEquals(player1Inventory, player2Inventory);

        Assert.assertEquals(player1Inventory.getMeepleSize(), player2Inventory.getMeepleSize());
        Assert.assertEquals(player1Inventory.getTotoroSize(), player2Inventory.getTotoroSize());
        Assert.assertEquals(player1Inventory.getTigerSize(), player2Inventory.getTigerSize());
    }

    @And("^The board and the contents should be made$")
    public void theBoardAndTheContentsShouldBeMade()  {
        Board board = new Board();

        // From BoardTests
        // Places the first five-spaced tile
        Point center = new Point(0, 0);
        TerrainType actual = board.getTerrainTypeAtPoint(board.boardPointForOffset(center));
        Assert.assertEquals(TerrainType.VOLCANO, actual);

        Point tl = new Point( 0, -1);
        actual = board.getTerrainTypeAtPoint(board.boardPointForOffset(tl));
        Assert.assertEquals(TerrainType.JUNGLE, actual);

        Point tr = new Point(1, -1);
        actual = board.getTerrainTypeAtPoint(board.boardPointForOffset(tr));
        Assert.assertEquals(TerrainType.LAKE, actual);

        Point br = new Point( 0, 1);
        actual = board.getTerrainTypeAtPoint(board.boardPointForOffset(br));
        Assert.assertEquals(TerrainType.GRASS, actual);

        Point bl = new Point(-1, 1);
        actual = board.getTerrainTypeAtPoint(board.boardPointForOffset(bl));
        Assert.assertEquals(TerrainType.ROCK, actual);
    }
}
