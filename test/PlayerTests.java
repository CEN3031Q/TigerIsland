/**
 * Created by user on 3/27/2017.
 */
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTests {

    private static GameActionPerformer gap;
    private static int playerID;

    //Will need to provide override method implementation later if keeping GameActionPerformer interface
    @BeforeClass
    public static void initGameActionPerformer() {
        playerID = 1;
        gap = new GameActionPerformer() {
            @Override
            public Coordinate tileAction(Tile tile, Board board) {
                return null;
            }

            @Override
            public BuildAction buildAction(Board board) {
                return null;
            }
        };
    }

    @Test
    public void getPlayerIDTest() {
        Player p1 = new Player(playerID, gap);
        Assert.assertEquals(1, p1.getPlayerID());
    }
    @Test
    public void initMeepleSizeTest(){
        Player p1 = new Player(playerID, gap);
        Assert.assertEquals(20, p1.getMeepleSize());
    }
    @Test
    public void initTotoroSizeTest(){
        Player p1 = new Player(playerID, gap);
        Assert.assertEquals(3, p1.getTotoroSize());
    }
    @Test
    public void initTigerSizeTest(){
        Player p1 = new Player(playerID, gap);
        Assert.assertEquals(2, p1.getTigerSize());
    }
    @Test
    public void meepleSizeAfterPlacementTest(){
        Player p1 = new Player(playerID, gap);
        p1.placeMeeplePiece();
        p1.placeMeeplePiece();
        Assert.assertEquals(18, p1.getMeepleSize());
    }
    @Test
    public void totoroSizeAfterPlacementTest(){
        Player p1 = new Player(playerID, gap);
        p1.placeTotoroPiece();
        p1.placeTotoroPiece();
        Assert.assertEquals(1, p1.getTotoroSize());
    }
    @Test
    public void tigerSizeAfterPlacementTest(){
        Player p1 = new Player(playerID, gap);
        p1.placeTigerPiece();
        p1.placeTigerPiece();
        Assert.assertEquals(0, p1.getTigerSize());
    }
}
