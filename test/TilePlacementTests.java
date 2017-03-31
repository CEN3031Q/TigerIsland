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
        tile1.changeAnchorPosition(HexagonPosition.LEFT);

        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        HashMap<Point, Boolean> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints[] = {new Point(199, 199),
                new Point(201, 199),
                new Point(203, 199),
                new Point(198, 200),
                new Point(204, 200),
                new Point(198, 202),
                new Point(204, 202),
                new Point(199, 203),
                new Point(201, 203),
                new Point(203, 203)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point expected : expectedValidPoints) {
            Assert.assertTrue(points.contains(expected));
        }
    }


    @Test
    public void testSecondTileValidPointsTopHeavyWithTopHeavyRightAnchor() {
        Board gameBoard = new Board();
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType rocky = TerrainType.ROCKY;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile1 = new Tile(volcano, grasslands, jungle);
        tile1.changeAnchorPosition(HexagonPosition.LEFT);

        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        tile2.changeAnchorPosition(HexagonPosition.RIGHT);

        HashMap<Point, Boolean> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints[] = {new Point(200, 198),
                new Point(202, 198),
                new Point(204, 198),
                new Point(199, 199),
                new Point(205, 199),
                new Point(199, 201),
                new Point(205, 201),
                new Point(200, 202),
                new Point(202, 202),
                new Point(204, 202)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point expected : expectedValidPoints) {
            Assert.assertTrue(points.contains(expected));
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
        tile1.changeAnchorPosition(HexagonPosition.LEFT);

        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        tile2.changeAnchorPosition(HexagonPosition.LEFT);

        HashMap<Point, Boolean> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints[] = {new Point(198, 198),
                new Point(200, 198),
                new Point(202, 198),
                new Point(197, 199),
                new Point(203, 199),
                new Point(197, 201),
                new Point(203, 201),
                new Point(198, 202),
                new Point(200, 202),
                new Point(202, 202)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point expected : expectedValidPoints) {
            Assert.assertTrue(points.contains(expected));
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
        tile1.changeAnchorPosition(HexagonPosition.LEFT);

        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        tile2.changeOrientation();
        tile2.changeAnchorPosition(HexagonPosition.MIDDLE);

        HashMap<Point, Boolean> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints[] = {new Point(198, 198),
                new Point(200, 198),
                new Point(202, 198),
                new Point(204, 198),
                new Point(198, 200),
                new Point(204, 200),
                new Point(200, 202),
                new Point(202, 202)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point expected : expectedValidPoints) {
            Assert.assertTrue(points.contains(expected));
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
        tile1.changeAnchorPosition(HexagonPosition.LEFT);

        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        tile2.changeOrientation();
        tile2.changeAnchorPosition(HexagonPosition.RIGHT);

        HashMap<Point, Boolean> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints[] = {new Point(199, 199),
                new Point(201, 199),
                new Point(203, 199),
                new Point(205, 199),
                new Point(199, 201),
                new Point(205, 201),
                new Point(201, 203),
                new Point(203, 203)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point expected : expectedValidPoints) {
            Assert.assertTrue(points.contains(expected));
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
        tile1.changeAnchorPosition(HexagonPosition.LEFT);

        Tile tile2 = new Tile(grasslands, volcano, rocky);

        gameBoard.placeTile(tile1, new Point(200, 200));

        tile2.changeOrientation();
        tile2.changeAnchorPosition(HexagonPosition.LEFT);

        HashMap<Point, Boolean> returnedValidPoints2 = gameBoard.validPositionsForNewTile(tile2);

        Point expectedValidPoints[] = {new Point(197, 199),
                new Point(199, 199),
                new Point(201, 199),
                new Point(203, 199),
                new Point(197, 201),
                new Point(203, 201),
                new Point(199, 203),
                new Point(201, 203)};

        Set<Point> points = returnedValidPoints2.keySet();
        for (Point expected : expectedValidPoints) {
            Assert.assertTrue(points.contains(expected));
        }
    }
}
