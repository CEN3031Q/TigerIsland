/**
 * Created by user on 3/27/2017.
 */
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

public class PlayerTests {
    public Game game;

    public Player firstPlayer;
    public Player secondPlayer;

    @Before
    public void setUp() {
        game = new Game();
        firstPlayer = new Player(1, game);
        secondPlayer = new Player(2, game);
    }

    @Test
    public void testSuccessiveTilePlacements() {
        Deck deck = new Deck();
        Board board = game.getGameBoard();

        for (int i = 0; i < 24; i++) {
            for (Player player : new Player[]{firstPlayer, secondPlayer}) {
                Tile tile = deck.drawTile();
                Point offset = player.performTileAction(tile);

                HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tile.getOrientation());
                HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

                Point offsetA = Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.A));
                Point offsetB = Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.B));

                Assert.assertEquals(board.hexagonAtPoint(board.boardPointForOffset(offset)).getTerrainType(), TerrainType.VOLCANO);
                Assert.assertEquals(tile.getTerrainTypeForPosition(HexagonPosition.A), board.getTerrainTypeAtPoint(board.boardPointForOffset(offsetA)));
                Assert.assertEquals(tile.getTerrainTypeForPosition(HexagonPosition.B), board.getTerrainTypeAtPoint(board.boardPointForOffset(offsetB)));
            }
        }
    }

    @Test(timeout=1500)
    public void testOneTurnInUnderOneAndAHalfSeconds() {
        Tile tile = new Tile(TerrainType.JUNGLE, TerrainType.ROCKY);
        Point offset = firstPlayer.performTileAction(tile);
        BuildAction action = firstPlayer.performBuildAction();
    }
}