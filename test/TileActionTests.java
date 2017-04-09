import org.junit.Assert;
import org.junit.Test;
import java.awt.*;

public class TileActionTests {
    @Test
    public void tileActionTestOne() {
        //PLACE <tile> AT <x> <y> <z> <orientation>
        String id = "1";
        Tile tile = new Tile(TerrainType.GRASS, TerrainType.JUNGLE);
        Point offset = new Point(-1, 0);
        int orientation = 5;
        TileAction tileAction = new TileAction(id, tile, offset, orientation);
        Assert.assertEquals("PLACE GRASS+JUNGLE AT -1 1 0 5", tileAction.tileActionToString());
    }

    @Test
    public void tileActionTestTwo() {
        //PLACE <tile> AT <x> <y> <z> <orientation>
        String id = "1";
        Tile tile = new Tile(TerrainType.LAKE, TerrainType.ROCK);
        Point offset = new Point(2, -1);
        int orientation = 4;
        TileAction tileAction = new TileAction(id, tile, offset, orientation);
        Assert.assertEquals("PLACE LAKE+ROCK AT 2 -1 -1 4", tileAction.tileActionToString());
    }
}