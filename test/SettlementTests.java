import org.junit.Test;
import java.awt.Point;

/**
 * Created by gonzalonunez on 4/5/17.
 */
public class SettlementTests {
    @Test
    public void testUpdateSettlement(){
        Board board = new Board();

        Tile tile1 = new Tile(TerrainType.LAKE, TerrainType.GRASSLANDS);
        tile1.setOrientation(6);

        Tile tile2 = new Tile(TerrainType.JUNGLE, TerrainType.ROCKY);
        tile2.setOrientation(5);

        //TODO: need to "place" settlers by setting a couple of these occupied
        //board.boardPointForOffset();

        board.placeTile(tile1, Board.axialToCube(new Point(-1, -1)));
        board.placeTile(tile2, Board.axialToCube(new Point(-1, 0)));

    }
}
