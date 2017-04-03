import org.junit.Test;

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

        gameBoard.setVillagersAtPosition(1, 200, 200);
        gameBoard.setVillagersAtPosition(1, 202, 200);
        gameBoard.setVillagersAtPosition(1, 201, 201);

        Settlement s = new Settlement(200, 200);


    }

    // Can probably split this function up or move it
    public int checkForAdjacentVillager(Board gameBoard){

    }

}
