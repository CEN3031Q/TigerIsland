import org.junit.Assert;
import org.junit.Test;

public class BoardTests {
    @Test
    public void createBoardAndPlaceTile() {
        Board gameBoard = new Board();

        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;
        Tile tile1 = new Tile(jungle, grasslands, volcano);
        Coordinate testCoordinate = new Coordinate(200,200);

        gameBoard.placeTile(tile1, testCoordinate);

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
        Coordinate testCoordinate = new Coordinate(200,200);
        Coordinate secondTestCoordinate = new Coordinate (204,200);

        gameBoard.placeTile(tile1, testCoordinate);
        gameBoard.placeTile(tile2, secondTestCoordinate);

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
        Coordinate testCoordinate = new Coordinate(200,200);

        gameBoard.placeTile(tile1, testCoordinate);
        gameBoard.placeTile(tile2, testCoordinate);

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
}
