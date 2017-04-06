/**
 * Created by gonzalonunez on 3/21/17.
 */
import java.awt.Point;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GameAI implements GameActionPerformer {
    private int id;
    private Inventory inventory;

    public GameAI(int id) {
        this.id = id;
        this.inventory = new Inventory(id);
    }

    /** DO NOT MODIFY THE BOARD. I'M SORRY BUT DEADLINES ARE DEATH */
    public Point tileAction(Tile tile, Board board) {
        //TODO: Make stacking decisions

        /**** PLACE AT EDGE ****/
        Set<Point> edgePoints = board.offsetsAtEdgeOfCurrentlyPlayedBoard().keySet();
        tile.setOrientation(ThreadLocalRandom.current().nextInt(1,7));

        while (true) {
            for (Point edgeOffset : edgePoints) {
                if (board.canPlaceTileAtEdgeOffset(tile, edgeOffset)) {
                    return edgeOffset;
                }
            }
            int orientation = tile.getOrientation();
            tile.setOrientation(orientation + 1);
        }
        /*********************/
    }

    /** DO NOT MODIFY THE BOARD. I'M SORRY BUT DEADLINES ARE DEATH */
    public BuildAction buildAction(Board board) {
        //TODO: I want to make a decision and pick an action
        /**
         So we go to each settlement and do a few checks:
         If no Totoros and size >= 5, place a Totoro
         If no Tigers and there is an adjacent level 3, place a Tiger
         Check all possible expansions for each type of adjacent terrain type and pick highest of those
         If none of the above place a single villager adjacent to a settlement of size < 5 if one exists, otherwise just put it anywhere
         **/

        //TODO: We want to make sure we can actually perform the action before we pick it! Aka check Inventory.
        return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, new Point(-1, 0));
    }
}