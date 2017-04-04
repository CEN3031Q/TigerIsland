import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by user on 3/27/2017.
 */
public class InventoryTests {

    private static int playerID;

    @BeforeClass
    public static void initInventoryTest() {
        playerID = 1;
    }

    @Test
    public void initMeepleListCountTest(){
        Inventory inventory = new Inventory(playerID);
        Assert.assertEquals(20, inventory.getMeepleSize());
    }
    @Test
    public void initTotoroListCountTest(){
        Inventory inventory = new Inventory(playerID);
        Assert.assertEquals(3, inventory.getTotoroSize());
    }
    @Test
    public void initTigerListCountTest(){
        Inventory inventory = new Inventory(playerID);
        Assert.assertEquals(2, inventory.getTigerSize());
    }
    @Test
    public void getPlayerIDTest() {
        Inventory inventory = new Inventory(playerID);
        Assert.assertEquals(1, inventory.getPlayerID());
    }
    @Test
    public void meepleRemovedTest(){
        Inventory inventory = new Inventory(playerID);
        Assert.assertTrue(inventory.removeMeeplePiece() instanceof Meeple);
    }
    @Test
    public void totoroRemovedTest(){
        Inventory inventory = new Inventory(playerID);
        Assert.assertTrue(inventory.removeTotoroPiece() instanceof Totoro);
    }
    @Test
    public void tigerRemovedTest(){
        Inventory inventory = new Inventory(playerID);
        Assert.assertTrue(inventory.removeTigerPiece() instanceof Tiger);
    }
    @Test
    public void meepleListCountTest(){
        Inventory inventory = new Inventory(playerID);
        inventory.removeMeeplePiece();
        inventory.removeMeeplePiece();
        Assert.assertEquals(18, inventory.getMeepleSize());
    }
    @Test
    public void totoroListCountTest(){
        Inventory inventory = new Inventory(playerID);
        inventory.removeTotoroPiece();
        inventory.removeTotoroPiece();
        Assert.assertEquals(1, inventory.getTotoroSize());
    }
    @Test
    public void tigerListCountTest(){
        Inventory inventory = new Inventory(playerID);
        inventory.removeTigerPiece();
        Assert.assertEquals(1, inventory.getTigerSize());
    }
    @Test
    public void isInventoryEmptyTest(){
        Inventory inventory = new Inventory(playerID);
        for (int i = 0; i < 20; i++){
            inventory.removeMeeplePiece();
        }
        for (int i = 0; i < 3; i++){
            inventory.removeTotoroPiece();
        }
        for (int i = 0; i < 2; i++){
            inventory.removeTigerPiece();
        }
        Assert.assertEquals(true, inventory.isInventoryEmpty());
    }
    @Test
    public void isMeepleEmptyTest(){
        Inventory inventory = new Inventory(playerID);
        for (int i = 0; i < 20; i++){
            inventory.removeMeeplePiece();
        }
        Assert.assertEquals(true, inventory.isMeepleEmpty());
    }
    @Test
    public void isTotoroEmptyTest(){
        Inventory inventory = new Inventory(playerID);
        for (int i = 0; i < 3; i++){
            inventory.removeTotoroPiece();
        }
        Assert.assertEquals(true, inventory.isTotoroEmpty());
    }
    @Test
    public void isTigerEmptyTest(){
        Inventory inventory = new Inventory(playerID);
        for (int i = 0; i < 2; i++){
            inventory.removeTigerPiece();
        }
        Assert.assertEquals(true, inventory.isTigerEmpty());
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void alreadyEmptyTest() {
        Inventory inventory = new Inventory(playerID);
        for (int i = 0; i < 20; i++){
            inventory.removeMeeplePiece();
        }
        inventory.removeMeeplePiece();
    }
}
