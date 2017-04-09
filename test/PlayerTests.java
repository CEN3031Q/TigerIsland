/**
 * Created by user on 3/27/2017.
 */
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

public class PlayerTests {
    public Game game;

    public Player firstPlayer;
    public Player secondPlayer;

    @Before
    public void setUp() {
        game = new Game("1");
        firstPlayer = new Player("1", game);
        secondPlayer = new Player("2", game);
    }

    @Test
    public void testSuccessiveTilePlacements() {
        Deck deck = new Deck();
        Board board = game.getGameBoard();

        for (int i = 0; i < 24; i++) {
            for (Player player : new Player[]{ firstPlayer, secondPlayer }) {
                Tile tile = deck.drawTile();

                TileAction tileAction = player.performTileAction(tile);

                HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tile.getOrientation());
                HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

                Point offsetA = Board.pointTranslatedByPoint(tileAction.getOffset(), abOffsets.get(HexagonPosition.A));
                Point offsetB = Board.pointTranslatedByPoint(tileAction.getOffset(), abOffsets.get(HexagonPosition.B));

                Assert.assertEquals(TerrainType.VOLCANO, board.hexagonAtPoint(board.boardPointForOffset(tileAction.getOffset())).getTerrainType());
                Assert.assertEquals(tile.getTerrainTypeForPosition(HexagonPosition.A), board.getTerrainTypeAtPoint(board.boardPointForOffset(offsetA)));
                Assert.assertEquals(tile.getTerrainTypeForPosition(HexagonPosition.B), board.getTerrainTypeAtPoint(board.boardPointForOffset(offsetB)));
            }
        }
    }

    @Test(timeout=1500)
    public void testAllTurnsInUnderOneAndAHalfSeconds() {
        Deck deck = new Deck();
        for (int i = 0; i < 24; i++) {
            for (Player player : new Player[]{ firstPlayer, secondPlayer }) {
                Tile tile = deck.drawTile();
                player.performTileAction(tile);
                player.performBuildAction();
            }
        }
    }

    @Test
    public void testPlaceTotoroIfWeCan(){
        Board board = game.getGameBoard();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);
        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Tile secondTile = new Tile (TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        secondTile.setOrientation(5);
        Point secondSettlementOffset = new Point(2, 0);
        board.placeTile(firstTile, Board.axialToCube(secondSettlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(1, 1))).setOccupied("1");


        board.getSettlementManager().updateSettlements();

        BuildAction buildAction = firstPlayer.performBuildAction();

        Hexagon totoroHex = board.hexagonAtPoint(board.boardPointForOffset(new Point(1,0)));


        Assert.assertTrue(totoroHex.isTotoroOnTop());
    }
    @Test
    public void testPlaceTotoroIfWeCanThatBrokeEarlier(){
        Board board = game.getGameBoard();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);
        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Tile secondTile = new Tile (TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        secondTile.setOrientation(3);
        Point secondSettlementOffset = new Point(1, 0);
        board.placeTile(secondTile, Board.axialToCube(secondSettlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1))).setOccupied("1");
        board.hexagonAtPoint(board.boardPointForOffset(new Point(1, 1))).setOccupied("1");


        board.getSettlementManager().updateSettlements();

        BuildAction buildAction = firstPlayer.performBuildAction();

        Hexagon totoroHex = board.hexagonAtPoint(board.boardPointForOffset(new Point(2,0)));


        Assert.assertTrue(totoroHex.isTotoroOnTop());
    }
  
    @Test
    public void testAIChooseTigerPlacement(){
        Board board = game.getGameBoard();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);
        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Tile secondTile = new Tile (TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        secondTile.setOrientation(2);
        Point secondSettlementOffset = new Point(-2, -1);
        board.placeTile(secondTile, Board.axialToCube(secondSettlementOffset));

        Tile thirdTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        thirdTile.setOrientation(3);
        Point thirdSettlementOffset = new Point(-2, 2);
        board.placeTile(thirdTile, Board.axialToCube(thirdSettlementOffset));

        Tile fourthTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        fourthTile.setOrientation(3);
        Point fourthSettlementOffset = new Point(-2, 2);
        board.placeTile(fourthTile, Board.axialToCube(fourthSettlementOffset));

        Tile fifthTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        fifthTile.setOrientation(5);
        Point fifthSettlementOffset = new Point(0, 2);
        board.placeTile(fifthTile, Board.axialToCube(fifthSettlementOffset));

        Tile sixthTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        sixthTile.setOrientation(3);
        Point sixthSettlementOffset = new Point(-2, 2);
        board.placeTile(sixthTile, Board.axialToCube(sixthSettlementOffset));

        Tile seventhTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        seventhTile.setOrientation(1);
        Point seventhSettlementOffset = new Point(-3, 2);
        board.placeTile(seventhTile, Board.axialToCube(seventhSettlementOffset));

        Tile eighthTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        eighthTile.setOrientation(6);
        Point eighthSettlementOffset = new Point(-2, 2);
        board.placeTile(eighthTile, Board.axialToCube(eighthSettlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied("1");

        board.getSettlementManager().updateSettlements();

        BuildAction buildAction = firstPlayer.performBuildAction();

        Hexagon tigerHex = board.hexagonAtPoint(board.boardPointForOffset(new Point(-2,1)));


        Assert.assertTrue(tigerHex.isTigerOnTop());
    }
}