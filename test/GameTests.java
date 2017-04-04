import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by gonzalonunez on 3/21/17.
 */
public class GameTests {
    class MockActionPerformer implements GameActionPerformer {
        private Point point;
        boolean decrements;

        public MockActionPerformer(Point p, boolean decr) {
            point = p;
            decrements = decr;
        }

        public MockActionPerformer(Point p) {
            point = p;
            decrements = false;
        }

        public MockActionPerformer() {
            point = new Point(0, 0);
            decrements = false;
        }

        public Point tileAction(Tile tile, Board board) {
            Point toReturn = point;
            if (decrements) { point = new Point(toReturn.x, toReturn.y - 2); }
            return toReturn;
        }

        public BuildAction buildAction(Board board) {
            return new CreateSettlementAction();
        }
    }

    @Test
    public void testGameCreatedWithPlayers() {
        MockActionPerformer actionPerformer = new MockActionPerformer();

        Player p1 = new Player(1, actionPerformer);
        Player p2 = new Player(2, actionPerformer);

        Player[] originalPlayers = {p1, p2};

        Game game = new Game(originalPlayers);

        Assert.assertArrayEquals(originalPlayers, game.getPlayers().toArray());
    }

    @Test
    public void testGameEliminatesPlayer() {
        MockActionPerformer actionPerformer = new MockActionPerformer();

        Player p1 = new Player(1, actionPerformer);
        Player p2 = new Player(2, actionPerformer);

        Player[] originalPlayers = {p1, p2};
        Player[] onlyPlayer2 = {p2};
        Player[] noPlayers = {};

        Game game = new Game(originalPlayers);

        game.eliminatePlayer(p1);
        Assert.assertArrayEquals(onlyPlayer2, game.getPlayers().toArray());

        game.eliminatePlayer(p1);
        Assert.assertArrayEquals(onlyPlayer2, game.getPlayers().toArray());

        game.eliminatePlayer(p2);
        Assert.assertArrayEquals(noPlayers, game.getPlayers().toArray());
    }

    @Test()
    public void testGameEndsWhenDeckEmptyAndHandlesException() {
        MockActionPerformer actionPerformer1 = new MockActionPerformer(new Point(200, 200), true);
        MockActionPerformer actionPerformer2 = new MockActionPerformer(new Point(200, 198), true);

        Player p1 = new Player(1, actionPerformer1);
        Player p2 = new Player(2, actionPerformer2);

        Player[] players = {p1, p2};

        // Create a deck with only 5 tiles left
        Deck deck = new Deck();
        int size = deck.size();

        for(int i = 0; i < size-5; i++) {
            Tile t = deck.drawTile();
        }

        Game game = new Game(deck, players);
        game.runGameLoop();

        Assert.assertTrue(game.isGameOver());
    }
}