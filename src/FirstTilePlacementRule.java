import java.awt.*;
import java.util.HashMap;

/**
 * Created by gonzalonunez on 3/28/17.
 */
public class FirstTilePlacementRule extends TilePlacementRule {
    public FirstTilePlacementRule(Tile tile, Board board) {
        super(tile, board);
    }

    @Override
    public boolean tileMeetsConditionsForRuleToApply() {
        return board.getNextTileID() == 1;
    }

    @Override
    public boolean pointIsValid(Point point) {
        Point mid = new Point(200, 200);
        return point.equals(mid);
    }
}