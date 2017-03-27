import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;


public class TilePlacementTests {
    @Test
    public void testFirstTileHasOneValidCoordinate() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);

        //Gets valid tile position of tile1 (first tile)
        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        Assert.assertEquals(1, returnedValidCoordinates.size());
    }

    @Test
    public void testSecondTileValidCoordinatesTopHeavyWithTopHeavyMiddleAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidCoordinates.get(0));

        tile2.changeAnchorPosition(HexagonPosition.MIDDLE); //Make anchor position middle

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Coordinate> returnedValidCoordinates2 = gameBoard.determineValidPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidCoordinates[][] = { {199, 199}, {201, 199}, {203, 199}, {198, 200}, {204, 200},
                                             {198, 202}, {204, 202}, {199, 203}, {201, 203}, {203, 203} };

        for (int ii = 0; ii < returnedValidCoordinates2.size(); ii++) {
            Assert.assertEquals(expectedValidCoordinates[ii][0], returnedValidCoordinates2.get(ii).getX());
            Assert.assertEquals(expectedValidCoordinates[ii][1], returnedValidCoordinates2.get(ii).getY());
        }
    }

    @Test
    public void testSecondTileValidCoordinatesTopHeavyWithTopHeavyRightAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidCoordinates.get(0));

        tile2.changeAnchorPosition(HexagonPosition.RIGHT); //Make anchor position right

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Coordinate> returnedValidCoordinates2 = gameBoard.determineValidPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidCoordinates[][] = { {200, 198}, {202, 198}, {204, 198}, {199, 199}, {205, 199},
                                             {199, 201}, {205, 201}, {200, 202}, {202, 202}, {204, 202} };

        for (int ii = 0; ii < returnedValidCoordinates2.size(); ii++) {
            Assert.assertEquals(expectedValidCoordinates[ii][0], returnedValidCoordinates2.get(ii).getX());
            Assert.assertEquals(expectedValidCoordinates[ii][1], returnedValidCoordinates2.get(ii).getY());
        }
    }

    @Test
    public void testSecondTileValidCoordinatesTopHeavyWithTopHeavyLeftAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidCoordinates.get(0));

        tile2.changeAnchorPosition(HexagonPosition.LEFT); //Make anchor position left

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Coordinate> returnedValidCoordinates2 = gameBoard.determineValidPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidCoordinates[][] = { {198, 198}, {200, 198}, {202, 198}, {197, 199}, {203, 199},
                                             {197, 201}, {203, 201}, {198, 202}, {200, 202}, {202, 202} };

        for (int ii = 0; ii < returnedValidCoordinates2.size(); ii++) {
            Assert.assertEquals(expectedValidCoordinates[ii][0], returnedValidCoordinates2.get(ii).getX());
            Assert.assertEquals(expectedValidCoordinates[ii][1], returnedValidCoordinates2.get(ii).getY());
        }
    }

    @Test
    public void testSecondTileValidCoordinatesBottomHeavyWithTopHeavyMiddleAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidCoordinates.get(0));

        tile2.changeOrientation();  //Make tile2 bottomheavy
        tile2.changeAnchorPosition(HexagonPosition.MIDDLE); //Make anchor position middle

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Coordinate> returnedValidCoordinates2 = gameBoard.determineValidPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidCoordinates[][] = { {198, 198}, {200, 198}, {202, 198}, {204, 198},
                                             {198, 200}, {204, 200}, {200, 202}, {202, 202} };

        for (int ii = 0; ii < returnedValidCoordinates2.size(); ii++) {
            Assert.assertEquals(expectedValidCoordinates[ii][0], returnedValidCoordinates2.get(ii).getX());
            Assert.assertEquals(expectedValidCoordinates[ii][1], returnedValidCoordinates2.get(ii).getY());
        }
    }

    @Test
    public void testSecondTileValidCoordinatesBottomHeavyWithTopHeavyRightAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidCoordinates.get(0));

        tile2.changeOrientation();  //Make tile2 bottomheavy
        tile2.changeAnchorPosition(HexagonPosition.RIGHT); //Make anchor position right

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Coordinate> returnedValidCoordinates2 = gameBoard.determineValidPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidCoordinates[][] = { {199, 199}, {201, 199}, {203, 199}, {205, 199},
                                             {199, 201}, {205, 201}, {201, 203}, {203, 203} };

        for (int ii = 0; ii < returnedValidCoordinates2.size(); ii++) {
            Assert.assertEquals(expectedValidCoordinates[ii][0], returnedValidCoordinates2.get(ii).getX());
            Assert.assertEquals(expectedValidCoordinates[ii][1], returnedValidCoordinates2.get(ii).getY());
        }
    }

    @Test
    public void testSecondTileValidCoordinatesBottomHeavyWithTopHeavyLeftAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Coordinate> returnedValidCoordinates = gameBoard.determineValidPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidCoordinates.get(0));

        tile2.changeOrientation();  //Make tile2 bottomheavy
        tile2.changeAnchorPosition(HexagonPosition.LEFT); //Make anchor position left

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Coordinate> returnedValidCoordinates2 = gameBoard.determineValidPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidCoordinates[][] = { {197, 199}, {199, 199}, {201, 199}, {203, 199},
                                             {197, 201}, {203, 201}, {199, 203}, {201, 203} };

        for (int ii = 0; ii < returnedValidCoordinates2.size(); ii++) {
            Assert.assertEquals(expectedValidCoordinates[ii][0], returnedValidCoordinates2.get(ii).getX());
            Assert.assertEquals(expectedValidCoordinates[ii][1], returnedValidCoordinates2.get(ii).getY());
        }
    }
}
