import javafx.geometry.Point3D;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import java.util.Comparator;

public class BoardTests {
    @Test
    public void testCreateBoard() {
        Board board = new Board();
        Assert.assertTrue(true);
    }

    @Test
    public void testBoardCreatedWithFirstTile() {
        Board board = new Board();

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
        Assert.assertEquals(TerrainType.GRASSLANDS, actual);

        Point bl = new Point(-1, 1);
        actual = board.getTerrainTypeAtPoint(board.boardPointForOffset(bl));
        Assert.assertEquals(TerrainType.ROCKY, actual);
    }

    @Test
    public void testBoardExpectedFirstValidSpots() {
        Board board = new Board();
        Set<Point> offsets = board.offsetsAtEdgeOfCurrentlyPlayedBoard().keySet();

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(-1, 2),
                new Point(-2, 2),
                new Point(-2, 1),
                new Point(-1, 0),
                new Point(-1, -1),
                new Point(0, -2),
                new Point(1, -2),
                new Point(2, -2),
                new Point(2, -1),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 2)
        ));

        Assert.assertTrue(expected.equals(offsets));
    }

    @Test
    public void testBoardExpectedSecondValidSpots() {
        Board board = new Board();

        Tile tile = new Tile(TerrainType.LAKE, TerrainType.LAKE);
        tile.setOrientation(6);

        board.placeTile(tile, Board.axialToCube(new Point(-1, -1)));

        Set<Point> offsets = board.offsetsAtEdgeOfCurrentlyPlayedBoard().keySet();

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(-1, 2),
                new Point(-2, 2),
                new Point(-2, 1),
                new Point(-1, 0),
                new Point(0, -3),
                new Point(-1, -3),
                new Point(-2, -2),
                new Point(-3, -1),
                new Point(-3, 0),
                new Point(-2, 0),
                new Point(0, -2),
                new Point(1, -2),
                new Point(2, -2),
                new Point(2, -1),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 2)
        ));

        Assert.assertTrue(expected.equals(offsets));
    }

}