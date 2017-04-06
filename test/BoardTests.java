import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    @Test
    public void testBoardFindsAllVolcanoes() {
        Board board = new Board();

        Tile firstTile = new Tile(TerrainType.LAKE, TerrainType.GRASSLANDS);
        firstTile.setOrientation(4);

        board.placeTile(firstTile, Board.axialToCube(new Point(1, 1)));

        Tile secondTile = new Tile(TerrainType.ROCKY, TerrainType.JUNGLE);
        secondTile.setOrientation(5);

        board.placeTile(secondTile, Board.axialToCube(new Point(-2, 2)));

        Set<Point> offsets = board.volcanoesOnCurrentlyPlayedBoard().keySet();

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(0, 0),
                new Point(1, 1),
                new Point(-2, 2)
        ));

        Assert.assertTrue(expected.equals(offsets));
    }

    @Test
    public void testBoardFindsEdgeOfSettlements(){
        Board board = new Board();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);

        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 0))).setOccupied(1);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied(1);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied(1);

        Set<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset(settlementOffset).keySet();

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(-2, 2),
                new Point(-3, 2),
                new Point(-3, 1),
                new Point(-3, 0),
                new Point(-2, -1),
                new Point(-1, -1),
                new Point(0, -1),
                new Point(0, 0),
                new Point(-1, 1)
        ));

        Assert.assertTrue(expected.equals(offsets));
    }

    @Test
    public void testFiltersEdgeOfSettlementByTerrain() {
        Board board = new Board();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 0))).setOccupied(1);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied(1);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied(1);

        Tile secondTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        secondTile.setOrientation(2);

        board.placeTile(secondTile, Board.axialToCube(new Point(-2, 3)));

        Set<Point> edgeOffsets = board.offsetsAtEdgeOfSettlementAtOffset(new Point(-1, 0)).keySet();

        TerrainType type = TerrainType.ROCKY;

        Set<Point> offsetsOfTerrain = edgeOffsets.stream().filter( edgeOffset -> {
            Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(edgeOffset));
            return hex.getTerrainType() == type;
        }).collect(Collectors.toSet());

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(-1, 1)
        ));

        System.out.println(offsetsOfTerrain);

        Assert.assertTrue(expected.equals(offsetsOfTerrain));
    }

    @Test
    public void testBoardExpandsSettlement(){
        Board board = new Board();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);

        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Integer settlementID = 1;

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 0))).setOccupied(settlementID);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied(settlementID);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied(settlementID);

        Tile secondTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        secondTile.setOrientation(2);

        board.placeTile(secondTile, Board.axialToCube(new Point(-2, 3)));

        board.expandSettlementAtOffset(settlementOffset, TerrainType.ROCKY, settlementID);

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(-1, 1),
                new Point(-1, 2),
                new Point(-1, 3)
        ));

        for (Point offset : expected) {
            Assert.assertEquals(TerrainType.ROCKY, board.hexagonAtPoint(board.boardPointForOffset(offset)).getTerrainType());
            Assert.assertEquals(settlementID, board.hexagonAtPoint(board.boardPointForOffset(offset)).getOccupiedID());
        }
    }

    public void testBoardExpandsSameSettlementFromDifferentOffset(){
        Board board = new Board();

        Tile firstTile = new Tile (TerrainType.LAKE, TerrainType.LAKE);
        firstTile.setOrientation(5);

        Point settlementOffset = new Point(-1, 0);
        board.placeTile(firstTile, Board.axialToCube(settlementOffset));

        Integer settlementID = 1;

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 0))).setOccupied(settlementID);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 0))).setOccupied(settlementID);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied(settlementID);

        Tile secondTile = new Tile (TerrainType.ROCKY, TerrainType.ROCKY);
        secondTile.setOrientation(2);

        board.placeTile(secondTile, Board.axialToCube(new Point(-2, 3)));

        board.expandSettlementAtOffset(new Point(-2, 0), TerrainType.ROCKY, settlementID);

        Set<Point> expected = new HashSet<Point>(Arrays.asList(
                new Point(-1, 1),
                new Point(-1, 2),
                new Point(-1, 3)
        ));

        for (Point offset : expected) {
            Assert.assertEquals(TerrainType.ROCKY, board.hexagonAtPoint(board.boardPointForOffset(offset)).getTerrainType());
            Assert.assertEquals(settlementID, board.hexagonAtPoint(board.boardPointForOffset(offset)).getOccupiedID());
        }
    }

}