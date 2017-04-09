import org.junit.Assert;
import org.junit.Test;
import java.awt.Point;


public class TilePlacementTests {
    @Test
    public void testPlaceFirstTile() {
        Board board = new Board();

        Tile tile = new Tile(TerrainType.LAKE, TerrainType.GRASS);
        tile.setOrientation(6);

        board.placeTile(tile, Board.axialToCube(new Point(-1, -1)));

        Assert.assertEquals(TerrainType.VOLCANO, board.getTerrainTypeAtPoint(board.boardPointForOffset(new Point(-1, -1))));
        Assert.assertEquals(TerrainType.LAKE, board.getTerrainTypeAtPoint(board.boardPointForOffset(new Point(-2, -1))));
        Assert.assertEquals(TerrainType.GRASS, board.getTerrainTypeAtPoint(board.boardPointForOffset(new Point(-1, -2))));
    }

    @Test
    public void testPlaceTileAnchorNotAtEdge() {
        Board board = new Board();

        Tile secondTile = new Tile(TerrainType.ROCK, TerrainType.LAKE);
        secondTile.setOrientation(2);

        board.placeTile(secondTile, Board.axialToCube(new Point(-2, 3)));

        Assert.assertEquals(TerrainType.VOLCANO, board.getTerrainTypeAtPoint(board.boardPointForOffset(new Point(-2, 3))));
        Assert.assertEquals(TerrainType.ROCK, board.getTerrainTypeAtPoint(board.boardPointForOffset(new Point(-1, 2))));
        Assert.assertEquals(TerrainType.LAKE, board.getTerrainTypeAtPoint(board.boardPointForOffset(new Point(-1, 3))));
    }
}
