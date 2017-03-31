/**
 * Created by gonzalonunez on 3/28/17.
 */
import java.awt.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import java.util.function.Function;
import java.util.stream.Collectors;

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

    public HashMap<HexagonPosition, Point> pointsForTerrainHexagons() {
        HashMap<HexagonPosition, Point> offsetsHashMap = offsetsForTerrainHexagonsInTile();
        Map<HexagonPosition, Point> pointsMap = offsetsHashMap.keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(),
                        key ->  HexagonNeighborsCalculator.pointTranslatedByPoint(point, offsetsHashMap.get(key))
                ));
        return new HashMap<>(pointsMap);
    }

    private HashMap<HexagonPosition, Point> offsetsForTerrainHexagonsInTile() {
        HashMap<HexagonPosition, Point> positionsMap = new HashMap<>();

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.MIDDLE) {
            positionsMap.put(HexagonPosition.MIDDLE, new Point(0, 0));
            positionsMap.put(HexagonPosition.LEFT, new Point(-1, -1));
            positionsMap.put(HexagonPosition.RIGHT, new Point(1, -1));
        }

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.LEFT) {
            positionsMap.put(HexagonPosition.MIDDLE, new Point(1, 1));
            positionsMap.put(HexagonPosition.LEFT, new Point(0, 0));
            positionsMap.put(HexagonPosition.RIGHT, new Point(0, 2));
        }

        if (orientation == TileOrientation.TOPHEAVY && position == HexagonPosition.RIGHT) {
            positionsMap.put(HexagonPosition.MIDDLE, new Point(-1, 1));
            positionsMap.put(HexagonPosition.LEFT, new Point(-2, 0));
            positionsMap.put(HexagonPosition.RIGHT, new Point(0, 0));
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.MIDDLE) {
            positionsMap.put(HexagonPosition.MIDDLE, new Point(0, 0));
            positionsMap.put(HexagonPosition.LEFT, new Point(-1, 1));
            positionsMap.put(HexagonPosition.RIGHT, new Point(1, 1));
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.LEFT) {
            positionsMap.put(HexagonPosition.MIDDLE, new Point(1, -1));
            positionsMap.put(HexagonPosition.LEFT, new Point(0, 0));
            positionsMap.put(HexagonPosition.RIGHT, new Point(2, 0));
        }

        if (orientation == TileOrientation.BOTTOMHEAVY && position == HexagonPosition.RIGHT) {
            positionsMap.put(HexagonPosition.MIDDLE, new Point(-1, -1));
            positionsMap.put(HexagonPosition.LEFT, new Point(-2, 0));
            positionsMap.put(HexagonPosition.RIGHT, new Point(0, 0));
        }

        return positionsMap;
    }

    public List<Point> neighborsAroundTile() {
        return  offsetsAroundTile().stream()
                .map( offset -> HexagonNeighborsCalculator.pointTranslatedByPoint(point, offset))
                .collect(toList());
    }

    public List<Point> neighborsWithinTile() {
        return  offsetsWithinTile().stream()
                .map( offset -> HexagonNeighborsCalculator.pointTranslatedByPoint(point, offset))
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

    // Didn't want to make a new PointUtils class rn sorry not sorry
    static private Point pointTranslatedByPoint(Point point, Point offset) {
        Point copy = new Point(point);
        copy.translate(offset.x, offset.y);
        return copy;
    }
}
