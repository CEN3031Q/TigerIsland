import org.junit.Test;
import org.junit.Assert;
import java.awt.Point;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by gonzalonunez on 4/5/17.
 */
public class SettlementTests {
    @Test
    public void testUpdateSettlementSeparatePlayerSettlements(){
        Board board = new Board();

        Tile tile1 = new Tile(TerrainType.LAKE, TerrainType.GRASSLANDS);
        tile1.setOrientation(6);

        Tile tile2 = new Tile(TerrainType.JUNGLE, TerrainType.ROCKY);
        tile2.setOrientation(5);

        board.placeTile(tile1, Board.axialToCube(new Point(-1, -1)));
        board.placeTile(tile2, Board.axialToCube(new Point(-1, 0)));

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, -2))).setOccupied(1);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, -1))).setOccupied(1);

        board.hexagonAtPoint(board.boardPointForOffset(new Point(-2, 1))).setOccupied(2);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(-1, 1))).setOccupied(2);
        board.hexagonAtPoint(board.boardPointForOffset(new Point(0, 1))).setOccupied(2);

        board.getSettlementManager().updateSettlements(board);

        Set<Settlement> expected = board.getSettlementManager().getListOfSettlements();
        Set<Settlement> compareSettlement = new HashSet<>();

        Settlement settlement1 = new Settlement(board.boardPointForOffset(new Point(-1,-2)));
        settlement1.getPoints().add(board.boardPointForOffset(new Point(-2,-1)));

        Settlement settlement2 = new Settlement(board.boardPointForOffset(new Point(-2,1)));
        settlement2.getPoints().add(board.boardPointForOffset(new Point(-1,1)));
        settlement2.getPoints().add(board.boardPointForOffset(new Point(0,1)));

        compareSettlement.add(settlement1);
        compareSettlement.add(settlement2);

        Assert.assertTrue(expected.equals(compareSettlement));

        //TODO: need to "place" settlers by setting a couple of these occupied
        //board.boardPointForOffset();

    }

    @Test
    public void testBasicSettlementEquality(){
        Settlement settlement1 = new Settlement(new Point(-1,-2));
        Settlement settlement2 = new Settlement(new Point(-1,-2));
        Assert.assertEquals(settlement1, settlement2);
    }

    @Test
    public void testSettlementInequality(){
        Settlement settlement1 = new Settlement(new Point(-1,-2));
        Settlement settlement2 = new Settlement(new Point(0,-2));
        Assert.assertNotEquals(settlement1, settlement2);
    }

    @Test
    public void testSettlementSetBehavior(){
        Settlement settlement1 = new Settlement(new Point(-1,-2));
        settlement1.getPoints().add(new Point(-1,-2));
        Assert.assertTrue(settlement1.getPoints().size() == 1);
    }
}
