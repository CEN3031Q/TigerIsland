import java.awt.*;

import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Created by gonzalonunez on 3/28/17.
 */
public class TilePlacementRule {
    protected final Tile tile;
    protected final Board board;

    public TilePlacementRule(Tile tile, Board board) {
        this.tile = tile;
        this.board = board;
    }

    public boolean tileMeetsConditionsForRuleToApply() {
        return true;
    };

    public boolean pointIsValid(Point point) {
        TileOrientation orientation = tile.getOrientation();
        HexagonPosition position = tile.getAnchorPosition();

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(point, orientation, position);

        List<Hexagon> hexagonsWithinTile = calc.neighborsWithinTile()
                .stream()
                .map( neighborPoint -> board.hexagonAtPoint(neighborPoint))
                .filter( hex -> hex.getTileID() != 0)
                .collect(toList());

        List<Hexagon> hexagonsAroundTile = calc.neighborsAroundTile()
                .stream()
                .map( neighborPoint -> board.hexagonAtPoint(neighborPoint))
                .filter( hex -> hex.getTileID() != 0)
                .collect(toList());

        if (!hexagonsWithinTile.isEmpty()) {
            return false;
        }

        return !hexagonsAroundTile.isEmpty();
    }
}