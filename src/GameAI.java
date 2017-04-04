/**
 * Created by gonzalonunez on 3/21/17.
 */
import javafx.geometry.Point3D;

import java.awt.*;
import java.util.HashMap;

public class GameAI implements GameActionPerformer {
    private int id;
    private Inventory inventory;

    public GameAI(int id) {
        this.id = id;
        this.inventory = new Inventory(id);
    }

    public Point3D tileAction(Tile tile, Board board) {
        // TODO: some naive optimal tile placement algorithm?
        HashMap<Point, Boolean> validPoints = board.validPositionsForNewTile(tile);
        Point chosenPoint = (Point)validPoints.keySet().toArray()[0];

        // TODO: convert chosenPoint to Point3D and return it
        return new Point3D(0, 0, 0);
    }

    public BuildAction buildAction(Board board) {
        //TODO: I want to iterate all of my settlements on the board
        /**
         So we go to each settlement and do a few checks:
         If no Totoros and size >= 5, place a Totoro
         If no Tigers and there is an adjacent level 3, place a Tiger
         Check all possible expansions for each type of adjacent terrain type and pick highest of those
         If none of the above place a single villager adjacent to a settlement of size < 5 if one exists, otherwise just put it anywhere
         **/
        return new BuildAction(BuildActionType.FOUND_SETTLEMENT, new Point3D(0, 0, 0));
    }
}