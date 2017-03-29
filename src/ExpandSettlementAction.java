import java.awt.geom.Point2D;

/**
 * Created by gonzalonunez on 3/21/17.
 */
public class ExpandSettlementAction implements BuildAction {
    private Point2D coordinateToExpandFrom;

    public ExpandSettlementAction(Point2D coordinate) {
        coordinateToExpandFrom = coordinate;
    }

    public void perform(Board board) {
        //TODO: Run expansion algorithm on board, starting on that coordinate. Find that spot's corresponding TerrainType.
    }
}
