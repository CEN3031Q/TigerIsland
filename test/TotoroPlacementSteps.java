import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;
import org.junit.Assert;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by patrickwert on 4/10/17.
 */
public class TotoroPlacementSteps {

    public Game game;

    public Player firstPlayer;
    public Player secondPlayer;

    @Given("^There is a settlement of size 5$")
    public void haveSettlementOfSizeFive(){
        Board board = new Board();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);
        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Tile secondTile = new Tile (TerrainType.GRASS, TerrainType.GRASS);
        secondTile.setOrientation(3);
        Point secondSettlementOffset = new Point(1, 0);
        board.placeTile(firstTile, Board.axialToCube(secondSettlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(1, 1))).setOccupied("1");
        

        Hexagon hexagon1 = board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0)));
        Hexagon hexagon2 = board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1)));
        Hexagon hexagon3 = board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1)));
        Hexagon hexagon4 = board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1)));
        Hexagon hexagon5 = board.hexagonAtPoint(board.boardPointForOffset(new Point(1, 1)));



        board.getSettlementManager().updateSettlements();
        int fiveSizeSettlement = 0;

        for(Settlement settlement: board.getSettlementManager().getListOfSettlements()){
            
            if(settlement.size() == 5){
                fiveSizeSettlement = 5;
            }
        }
        Assert.assertEquals(5,fiveSizeSettlement);

        Assert.assertEquals("1", hexagon1.getOccupiedID());
        Assert.assertEquals("1", hexagon2.getOccupiedID());
        Assert.assertEquals("1", hexagon3.getOccupiedID());
        Assert.assertEquals("1", hexagon4.getOccupiedID());
        Assert.assertEquals("1", hexagon5.getOccupiedID());


    }
    @When("^an eligible Totoro spot around it$")
    public void haveAdjacentNonVolcanoSpaceForTotoro() {
        Board board = new Board();

        Tile firstTile = new Tile(TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);
        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Tile secondTile = new Tile(TerrainType.GRASS, TerrainType.GRASS);
        secondTile.setOrientation(3);
        Point secondSettlementOffset = new Point(1, 0);
        board.placeTile(firstTile, Board.axialToCube(secondSettlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(1, 1))).setOccupied("1");
        board.getSettlementManager().updateSettlements();


        Settlement settlement = board.getSettlementManager().getListOfSettlements().iterator().next();

        boolean legalTotoroSpot = false;

        if (settlement.size() >= 5) {
            Set<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset((Point) settlement.getOffsets().toArray()[0]).keySet();
            for (Point offset : offsets) {
                Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(offset));
                if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO && hex.getTerrainType() != TerrainType.EMPTY) {
                    legalTotoroSpot = true;
                }
            }
        }
        Assert.assertTrue(legalTotoroSpot);
    }
    @Then("^the AI decides to place a Totoro at that spot$")
    public void verifyTotoroWasPlaced(){

        Board board = new Board();

        Tile firstTile = new Tile(TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);
        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Tile secondTile = new Tile(TerrainType.GRASS, TerrainType.GRASS);
        secondTile.setOrientation(3);
        Point secondSettlementOffset = new Point(1, 0);
        board.placeTile(secondTile, Board.axialToCube(secondSettlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(1, 1))).setOccupied("1");
        board.getSettlementManager().updateSettlements();



        Settlement settlement = board.getSettlementManager().getListOfSettlements().iterator().next();


        Hexagon hexagon = board.hexagonAtPoint(board.boardPointForOffset(new Point(2, 0)));

            if (settlement.size() >= 5) {
                Set<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset((Point)settlement.getOffsets().toArray()[0]).keySet();
                for (Point offset : offsets) {
                    Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(offset));
                    if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO && hex.getTerrainType() != TerrainType.EMPTY) {
                        hexagon.setTotoroOnTop(true);
                        //BuildAction buildAction = firstPlayer.performBuildAction();
                    }
                }

            }
        Assert.assertTrue(hexagon.isTotoroOnTop());

    }

}
