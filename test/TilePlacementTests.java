import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.awt.*;


public class TilePlacementTests {
    @Test
    public void testFirstTileHasOneValidPoint() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);

        HashMap<Point, Boolean> returnedValidPoints = gameBoard.validPositionsForNewTile(tile1);
        Assert.assertEquals(1, returnedValidPoints.size());
        Assert.assertEquals(new Point(200, 200), returnedValidPoints.keySet().toArray()[0]);
    }

    @Test
    public void testSecondTileValidPointsTopHeavyWithTopHeavyMiddleAnchor() {
        Board gameBoard = new Board();

        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        HashMap<Point, Boolean>returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints2[] = {new Point(199, 199),
                                        new Point(199, 201),
                                        new Point(199, 203),
                                        new Point(200, 198),
                                        new Point(200, 204),
                                        new Point(202, 198),
                                        new Point(202, 204),
                                        new Point(203, 199),
                                        new Point(203, 201),
                                        new Point(203, 203)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point validPoint : points) {
            System.out.println(validPoint);
        }

        for (Point expected : expectedValidPoints2) {
            Assert.assertTrue(points.contains(expected));
        }
    }

    /*
    @Test
    public void testSecondTileValidPointsTopHeavyWithTopHeavyRightAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Point> returnedValidPoints = gameBoard.validPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidPoints.get(0));

        tile2.changeAnchorPosition(HexagonPosition.RIGHT); //Make anchor position right

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Point> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidPoints[][] = { {200, 198}, {202, 198}, {204, 198}, {199, 199}, {205, 199},
                                             {199, 201}, {205, 201}, {200, 202}, {202, 202}, {204, 202} };

        for (int ii = 0; ii < returnedValidPoints2.size(); ii++) {
            Assert.assertEquals(expectedValidPoints[ii][0], returnedValidPoints2.get(ii).getX(), 0);
            Assert.assertEquals(expectedValidPoints[ii][1], returnedValidPoints2.get(ii).getY(), 0);
        }
    }

    @Test
    public void testSecondTileValidPointsTopHeavyWithTopHeavyLeftAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Point> returnedValidPoints = gameBoard.validPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidPoints.get(0));

        tile2.changeAnchorPosition(HexagonPosition.LEFT); //Make anchor position left

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Point> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidPoints[][] = { {198, 198}, {200, 198}, {202, 198}, {197, 199}, {203, 199},
                                             {197, 201}, {203, 201}, {198, 202}, {200, 202}, {202, 202} };

        for (int ii = 0; ii < returnedValidPoints2.size(); ii++) {
            Assert.assertEquals(expectedValidPoints[ii][0], returnedValidPoints2.get(ii).getX(), 0);
            Assert.assertEquals(expectedValidPoints[ii][1], returnedValidPoints2.get(ii).getY(), 0);
        }
    }

    @Test
    public void testSecondTileValidPointsBottomHeavyWithTopHeavyMiddleAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Point> returnedValidPoints = gameBoard.validPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidPoints.get(0));

        tile2.changeOrientation();  //Make tile2 bottomheavy
        tile2.changeAnchorPosition(HexagonPosition.MIDDLE); //Make anchor position middle

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Point> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidPoints[][] = { {198, 198}, {200, 198}, {202, 198}, {204, 198},
                                             {198, 200}, {204, 200}, {200, 202}, {202, 202} };

        for (int ii = 0; ii < returnedValidPoints2.size(); ii++) {
            Assert.assertEquals(expectedValidPoints[ii][0], returnedValidPoints2.get(ii).getX(), 0);
            Assert.assertEquals(expectedValidPoints[ii][1], returnedValidPoints2.get(ii).getY(), 0);
        }
    }

    @Test
    public void testSecondTileValidPointsBottomHeavyWithTopHeavyRightAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Point> returnedValidPoints = gameBoard.validPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidPoints.get(0));

        tile2.changeOrientation();  //Make tile2 bottomheavy
        tile2.changeAnchorPosition(HexagonPosition.RIGHT); //Make anchor position right

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Point> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidPoints[][] = { {199, 199}, {201, 199}, {203, 199}, {205, 199},
                                             {199, 201}, {205, 201}, {201, 203}, {203, 203} };

        for (int ii = 0; ii < returnedValidPoints2.size(); ii++) {
            Assert.assertEquals(expectedValidPoints[ii][0], returnedValidPoints2.get(ii).getX(), 0);
            Assert.assertEquals(expectedValidPoints[ii][1], returnedValidPoints2.get(ii).getY(), 0);
        }
    }

    @Test
    public void testSecondTileValidPointsBottomHeavyWithTopHeavyLeftAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        Tile tile2 = new Tile(grasslands, volcano, rocky);

        ArrayList<Point> returnedValidPoints = gameBoard.validPositionsForNewTile(tile1);
        gameBoard.placeTile(tile1, returnedValidPoints.get(0));

        tile2.changeOrientation();  //Make tile2 bottomheavy
        tile2.changeAnchorPosition(HexagonPosition.LEFT); //Make anchor position left

        //Gets valid tile positions of tile2 (second tile)
        ArrayList<Point> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        //(x,y)
        int expectedValidPoints[][] = { {197, 199}, {199, 199}, {201, 199}, {203, 199},
                                             {197, 201}, {203, 201}, {199, 203}, {201, 203} };

        for (int ii = 0; ii < returnedValidPoints2.size(); ii++) {
            Assert.assertEquals(expectedValidPoints[ii][0], returnedValidPoints2.get(ii).getX(), 0);
            Assert.assertEquals(expectedValidPoints[ii][1], returnedValidPoints2.get(ii).getY(), 0);
        }
    }
    */
}
