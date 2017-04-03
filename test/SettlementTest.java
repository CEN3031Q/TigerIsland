import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by hugh on 3/29/17.
 * Tests if the game recognizes a settlement object and its methods
 */


public class SettlementTest {

    @Test
    public void findSettlementSizeTest(){
        MockScenario m = new MockScenario();
        Board gameBoard = new Board();

        m.getTileWithGivenTerrain(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);

        gameBoard.setVillagersAtPoint(1, new Point(200, 200));
        gameBoard.setVillagersAtPoint(1, new Point(202, 200));
        gameBoard.setVillagersAtPoint(1, new Point(201, 201);

        SettlementManager settlementManager = new SettlementManager();


        Assert.assertEquals(3, settlementManager.calculateSettlementSizeAtPoint(new Point(200, 200)););
    }




}
