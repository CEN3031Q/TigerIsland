import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class BoardTests {
    @Test
    public void testBoardCopyAndEquality() {
        Board board = new Board();

        Tile tile1 = new Tile(TerrainType.JUNGLE,  TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        board.placeTile(tile1, new Point(200,200));

        Board copy = new Board(board);

        Assert.assertEquals(board, copy);
        Assert.assertFalse(board == copy);

        Tile tile2 = new Tile(TerrainType.VOLCANO,  TerrainType.JUNGLE, TerrainType.LAKE);
        board.placeTile(tile1, new Point(199,199));

        Assert.assertNotEquals(copy, board);
        Assert.assertFalse(board == copy);
    }

    @Test
    public void testBoardAllowsFirstPlaceInMiddle() {
        Board board = new Board();
        Tile tile = new Tile(TerrainType.JUNGLE,  TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        board.placeTile(tile, new Point(200, 200));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoardThrowsForFirstTurnNotInMiddle() {
        Board board = new Board();
        Tile tile = new Tile(TerrainType.JUNGLE,  TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        board.placeTile(tile, new Point(201, 201));
    }

    /*@Test
    public void createBoardAndPlaceTile() {
        Board gameBoard = new Board();

        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;
        Tile tile1 = new Tile(jungle, grasslands, volcano);
        Point testPoint = new Point(200,200);

        gameBoard.placeTile(tile1, testPoint);

        Assert.assertEquals(jungle, gameBoard.getTerrainTypeAtPosition(200,200));
        Assert.assertEquals(1, gameBoard.getLevelAtPosition(200,200));
        Assert.assertEquals(1, gameBoard.getTileIDAtPosition(200,200));
        Assert.assertEquals(grasslands, gameBoard.getTerrainTypeAtPosition(202,200));
        Assert.assertEquals(1, gameBoard.getLevelAtPosition(202,200));
        Assert.assertEquals(1, gameBoard.getTileIDAtPosition(202,200));
        Assert.assertEquals(volcano, gameBoard.getTerrainTypeAtPosition(201,201));
        Assert.assertEquals(1, gameBoard.getLevelAtPosition(201,201));
        Assert.assertEquals(1, gameBoard.getTileIDAtPosition(201,201));

//        gameBoard.printBoard();
    }

    @Test
    public void createBoardAndPlaceSeparateTiles() {
        Board gameBoard = new Board();

        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType lake = TerrainType.LAKE;
        TerrainType jungle = TerrainType.JUNGLE;
        Tile tile1 = new Tile(volcano, grasslands, lake);
        Tile tile2 = new Tile(grasslands, volcano, jungle);
        Point testPoint = new Point(200,200);
        Point secondTestPoint = new Point (204,200);

        gameBoard.placeTile(tile1, testPoint);
        gameBoard.placeTile(tile2, secondTestPoint);

        Assert.assertEquals(grasslands, gameBoard.getTerrainTypeAtPosition(204,200));
        Assert.assertEquals(1, gameBoard.getLevelAtPosition(204,200));
        Assert.assertEquals(2, gameBoard.getTileIDAtPosition(204,200));
        Assert.assertEquals(volcano, gameBoard.getTerrainTypeAtPosition(206,200));
        Assert.assertEquals(1, gameBoard.getLevelAtPosition(206,200));
        Assert.assertEquals(2, gameBoard.getTileIDAtPosition(206,200));
        Assert.assertEquals(jungle, gameBoard.getTerrainTypeAtPosition(205,201));
        Assert.assertEquals(1, gameBoard.getLevelAtPosition(205,201));
        Assert.assertEquals(2, gameBoard.getTileIDAtPosition(205,201));

        //gameBoard.printBoard();
    }

    @Test
    public void createBoardAndStackTiles() {
        Board gameBoard = new Board();

        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);
        Point testPoint = new Point(200,200);

        gameBoard.placeTile(tile1, testPoint);
        gameBoard.placeTile(tile2, testPoint);

        Assert.assertEquals(grasslands, gameBoard.getTerrainTypeAtPosition(200,200));
        Assert.assertEquals(2, gameBoard.getLevelAtPosition(200,200));
        Assert.assertEquals(2, gameBoard.getTileIDAtPosition(200,200));
        Assert.assertEquals(volcano, gameBoard.getTerrainTypeAtPosition(202,200));
        Assert.assertEquals(2, gameBoard.getLevelAtPosition(202,200));
        Assert.assertEquals(2, gameBoard.getTileIDAtPosition(202,200));
        Assert.assertEquals(rocky, gameBoard.getTerrainTypeAtPosition(201,201));
        Assert.assertEquals(2, gameBoard.getLevelAtPosition(201,201));
        Assert.assertEquals(2, gameBoard.getTileIDAtPosition(201,201));

        //gameBoard.printBoard();

    }
    */
    //TODO: Taylor fix this shit
}

