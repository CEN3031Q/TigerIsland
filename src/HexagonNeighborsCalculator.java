import java.awt.Point;
import java.util.HashMap;

public class HexagonNeighborsCalculator {
    private int orientation;

    public HexagonNeighborsCalculator(int orientation) {
        this.orientation = orientation;
    }

    public HashMap<HexagonPosition, Point> offsetsForAB() {
        HashMap<HexagonPosition, Point> points = new HashMap<>();
        switch (orientation) {
            case 1:
                points.put(HexagonPosition.A, new Point(0, -1));
                points.put(HexagonPosition.B, new Point(1, -1));
                break;
            case 2:
                points.put(HexagonPosition.A, new Point(1, -1));
                points.put(HexagonPosition.B, new Point(1, 0));
                break;
            case 3:
                points.put(HexagonPosition.A, new Point(1, 0));
                points.put(HexagonPosition.B, new Point(0, 1));
                break;
            case 4:
                points.put(HexagonPosition.A, new Point(0, 1));
                points.put(HexagonPosition.B, new Point(-1, 1));
                break;
            case 5:
                points.put(HexagonPosition.A, new Point(-1, 1));
                points.put(HexagonPosition.B, new Point(-1, 0));
                break;
            case 6:
                points.put(HexagonPosition.A, new Point(-1, 0));
                points.put(HexagonPosition.B, new Point(0, -1));
                break;
            default:
                break;
        }
        return points;
    }

    static public Point[] hexagonNeighborOffsets() {
        return new Point[] {
                    new Point(1,0),
                    new Point(1,-1),
                    new Point(0,-1),
                    new Point(-1,0),
                    new Point(-1,1),
                    new Point( 0,1)
                };
    }

}
