import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gonzalonunez on 3/16/17.
 */
public class TileTests {
    @Test
    public void testLakeGrasslandsTileFromServer() {
        String serverString = "LAKE+GRASS";
        Tile tile = new Tile(serverString);

        TerrainType expectedA = TerrainType.LAKE;
        TerrainType expectedB = TerrainType.GRASS;

        Assert.assertEquals(expectedA, tile.getTerrainTypeForPosition(HexagonPosition.A));
        Assert.assertEquals(expectedB, tile.getTerrainTypeForPosition(HexagonPosition.B));
    }
}
