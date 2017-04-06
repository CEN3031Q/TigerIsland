import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gonzalonunez on 3/16/17.
 */
public class HexagonTests {
    @Test
    public void testHexagonInitializedWithCorrectTerrainType() {
        Hexagon hex = new Hexagon();
        TerrainType volcano = TerrainType.VOLCANO;
        hex.setTerrainType(volcano);
        Assert.assertEquals(volcano, hex.getTerrainType());
    }
    @Test
    public void getLevelTest() {
        Hexagon hex = new Hexagon();
        TerrainType grass = TerrainType.GRASSLANDS;
        hex.setTerrainType(grass);
        hex.incrementLevel();
        hex.incrementLevel();
        Assert.assertEquals(2, hex.getLevel());
    }
    @Test
    public void getTotoroOnTopTest() {
        Hexagon hex = new Hexagon();
        TerrainType grass = TerrainType.GRASSLANDS;
        hex.setTerrainType(grass);
        hex.incrementLevel();
        hex.setTotoroOnTop(true);
        Assert.assertEquals(true, hex.isTotoroOnTop());
    }
    @Test
    public void getTigerOnTopTest() {
        Hexagon hex = new Hexagon();
        TerrainType grass = TerrainType.GRASSLANDS;
        hex.setTerrainType(grass);
        hex.incrementLevel();
        hex.incrementLevel();
        hex.incrementLevel();
        Assert.assertEquals(3, hex.getLevel());
        hex.setTigerOnTop(true);
        Assert.assertEquals(true, hex.isTigerOnTop());
    }
}
