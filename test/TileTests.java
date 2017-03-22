import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gonzalonunez on 3/16/17.
 */
public class TileTests {
    @Test
    public void testTileInitializedWithCorrectTerrainTypes() {
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile = new Tile(volcano, grasslands, jungle);

        Assert.assertEquals(jungle, tile.getTerrainTypeForPosition(HexagonPosition.MIDDLE));
        Assert.assertEquals(grasslands, tile.getTerrainTypeForPosition(HexagonPosition.RIGHT));
        Assert.assertEquals(volcano, tile.getTerrainTypeForPosition(HexagonPosition.LEFT));
    }

    @Test
    public void testTopheavyTileClockwiseRotation() {
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile = new Tile(volcano, grasslands, jungle);

        tile.clockwiseRotation();

        Assert.assertEquals(volcano, tile.getTerrainTypeForPosition(HexagonPosition.MIDDLE));
        Assert.assertEquals(grasslands, tile.getTerrainTypeForPosition(HexagonPosition.RIGHT));
        Assert.assertEquals(jungle, tile.getTerrainTypeForPosition(HexagonPosition.LEFT));
    }

    @Test
    public void testTopheavyTileCounterClockwiseRotation() {
        TerrainType volcano = TerrainType.VOLCANO;
        TerrainType grasslands = TerrainType.GRASSLANDS;
        TerrainType jungle = TerrainType.JUNGLE;

        Tile tile = new Tile(volcano, grasslands, jungle);

        tile.counterClockwiseRotation();

        Assert.assertEquals(grasslands, tile.getTerrainTypeForPosition(HexagonPosition.MIDDLE));
        Assert.assertEquals(jungle, tile.getTerrainTypeForPosition(HexagonPosition.RIGHT));
        Assert.assertEquals(volcano, tile.getTerrainTypeForPosition(HexagonPosition.LEFT));
    }
}
