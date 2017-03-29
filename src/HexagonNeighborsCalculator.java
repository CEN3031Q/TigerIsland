/**
 * Created by gonzalonunez on 3/28/17.
 */

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class HexagonNeighborsCalculator {
    private Point point;
    private TileOrientation orientation;
    private HexagonPosition position;

    public HexagonNeighborsCalculator(Point point, TileOrientation orientation, HexagonPosition position) {
        this.point = point;
        this.orientation = orientation;
        this.position = position;
    }

    public List<Point> neighborsAroundTile() {
        return  offsetsAroundTile().stream()
                .map( offset -> new Point(point.x + offset.x, point.y + offset.y))
                .collect(toList());
    }

    public List<Point> neighborsWithinTile() {
        return  offsetsWithinTile().stream()
                .map( offset -> new Point(point.x + offset.x, point.y + offset.y))
                .collect(toList());
    }

    private List<Point> offsetsWithinTile() {
        List<Point> offsetsList = new ArrayList<Point>();

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.MIDDLE) {
            offsetsList = Arrays.asList(
                    new Point(-1, -1),
                    new Point(-1, 0),
                    new Point(0, -1),
                    new Point(0, 0),
                    new Point(1, 0),
                    new Point(1, -1)
            );
        }

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.LEFT) {
            offsetsList = Arrays.asList(
                    new Point(0, 0),
                    new Point(1, 0),
                    new Point(2, 0),
                    new Point(0, 1),
                    new Point(1, 1),
                    new Point(2, 1)
            );
        }

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.RIGHT) {
            offsetsList = Arrays.asList(
                    new Point(0, 0),
                    new Point(-1, 0),
                    new Point(-2, 0),
                    new Point(-2, 1),
                    new Point(-1, 1),
                    new Point(0, 1)
            );
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.MIDDLE) {
            offsetsList = Arrays.asList(
                    new Point(0, 0),
                    new Point(-1, 0),
                    new Point(1, 0),
                    new Point(1, 1),
                    new Point(-1, 1),
                    new Point(0, 1)
            );
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.LEFT) {
            offsetsList = Arrays.asList(
                    new Point(0, -1),
                    new Point(1, -1),
                    new Point(2, -1),
                    new Point(0, 0),
                    new Point(1, 0),
                    new Point(2, 0)
            );
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.RIGHT) {
            offsetsList = Arrays.asList(
                    new Point(0, -1),
                    new Point(-1, -1),
                    new Point(-2, -1),
                    new Point(0, 0),
                    new Point(-1, 0),
                    new Point(-2, 0)
            );
        }

        return offsetsList;
    }

    private List<Point> offsetsAroundTile() {
        List<Point> offsetsList = new ArrayList<Point>();

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.MIDDLE) {
            offsetsList = Arrays.asList(
                    new Point(-2, -2),
                    new Point(0, -2),
                    new Point(2, -2),
                    new Point(-2, 0),
                    new Point(2, 0),
                    new Point(-1, 1),
                    new Point(1, 1)
            );
        }

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.LEFT) {
            offsetsList = Arrays.asList(
                    new Point(-1, -1),
                    new Point(1, -1),
                    new Point(3, -1),
                    new Point(-1, 1),
                    new Point(3, 1),
                    new Point(0, 2),
                    new Point(2, 2)
            );
        }

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.RIGHT) {
            offsetsList = Arrays.asList(
                    new Point(-3, -1),
                    new Point(-1, -1),
                    new Point(1, -1),
                    new Point(1, 1),
                    new Point(-3, 1),
                    new Point(0, 2),
                    new Point(-2, 2)
            );
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.MIDDLE) {
            offsetsList = Arrays.asList(
                    new Point(-2, 2),
                    new Point(0, 2),
                    new Point(2, 2),
                    new Point(2, 0),
                    new Point(-2, 0),
                    new Point(-1, -1),
                    new Point(1, -1)
            );
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.LEFT) {
            offsetsList = Arrays.asList(
                    new Point(-1, -1),
                    new Point(3, -1),
                    new Point(0, -2),
                    new Point(2, -2),
                    new Point(-1, 1),
                    new Point(1, 1),
                    new Point(3, 1)
            );
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.RIGHT) {
            offsetsList = Arrays.asList(
                    new Point(-3, -1),
                    new Point(1, -1),
                    new Point(0, -2),
                    new Point(-2, -2),
                    new Point(1, 1),
                    new Point(-1, 1),
                    new Point(-3, 1)
            );
        }

        return offsetsList;
    }
}
