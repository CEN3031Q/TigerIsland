import org.junit.Test;
import org.junit.Assert;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

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
        ArrayList<Settlement> compareSettlement = new ArrayList();
        //set expected equal to List of settlements
        //then manually make a settlement with the right points and settlements

        Settlement settlement1 = new Settlement(board.boardPointForOffset(new Point(-1,-2)));
        settlement1.getPointsInSettlement().add(board.boardPointForOffset(new Point(-1,-2)));
        settlement1.getPointsInSettlement().add(board.boardPointForOffset(new Point(-2,-1)));

        Settlement settlement2 = new Settlement(board.boardPointForOffset(new Point(-1,-2)));
        settlement1.getPointsInSettlement().add(board.boardPointForOffset(new Point(-2,1)));
        settlement1.getPointsInSettlement().add(board.boardPointForOffset(new Point(-1,1)));
        settlement1.getPointsInSettlement().add(board.boardPointForOffset(new Point(0,1)));

        compareSettlement.add(settlement1);
        compareSettlement.add(settlement2);




        for(int i = 0; i < expected.size(); i++){
            //Assert.assertTrue(expected.get(i).getPointsInSettlement().equals());
        }

        //TODO: need to "place" settlers by setting a couple of these occupied
        //board.boardPointForOffset();

    }
}
