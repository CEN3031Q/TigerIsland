import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gonzalonunez on 3/28/17.
 */
public class DeckTests {
    @Test
    public void testDeckRemovesTileDrawn() {
        Deck deck = new Deck();
        int originalSize = deck.size();

        Tile tile = deck.drawTile();

        Assert.assertNotEquals(originalSize, deck.size());
        Assert.assertTrue(originalSize == deck.size() + 1);
    }

    @Test
    public void testDeckHasFortyEightTiles() {
        Deck deck = new Deck();
        Assert.assertEquals(48, deck.size());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testDeckEmptyThrowsException() {
        Deck deck = new Deck();
        int sizePlusOne = deck.size() + 1;
        for (int i = 0; i < sizePlusOne; i++) {
            Tile t = deck.drawTile();
        }
    }
}
