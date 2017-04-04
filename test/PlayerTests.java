/**
 * Created by user on 3/27/2017.
 */
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

public class PlayerTests {
    public Player player;

    @Before public void setUp() {
        Game game = new Game();
        player = new Player(1, game);
    }

    @Test
    public void testTilePlacementChangesBoard() {

    }

    @Test
    public void testBuildActionChangesBoard() {

    }
}