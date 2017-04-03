import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PieceTest {

    private static int playerID;

    @BeforeClass
    public static void initPieceTest() {
        playerID = 1;
    }

    @Test
    public void getPlayerIDFromMeeple(){
        Meeple meeple = new Meeple(playerID);
        Assert.assertEquals(1, meeple.getPlayerID());
    }
    @Test
    public void getPlayerIDFromTotoro(){
        Totoro totoro = new Totoro(playerID);
        Assert.assertEquals(1, totoro.getPlayerID());
    }
    @Test
    public void getPlayerIDFromTiger(){
        Tiger tiger = new Tiger(playerID);
        Assert.assertEquals(1, tiger.getPlayerID());
    }
}
