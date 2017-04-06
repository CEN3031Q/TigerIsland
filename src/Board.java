import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javafx.geometry.Point3D;

public class Board {
    public Hexagon[][] boardStorage;
    private SettlementManager settlementManager;

    private int nextTileID = 1;

    private int dimensions = 200;

    private int minBoardX = -3;
    private int maxBoardX = 3;

    private int minBoardY = -3;
    private int maxBoardY = 3;

    private ArrayList<Settlement> settlementList;

    public Board() {
        settlementManager = new SettlementManager();

        int dim = dimensions;

        boardStorage = new Hexagon[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                boardStorage[i][j] = new Hexagon(TerrainType.EMPTY, 0,0);
            }
        }

        // Place first tile

        Point point = boardPointForOffset(new Point(0, 0));
        hexagonAtPoint(point).setTerrainType(TerrainType.VOLCANO);

        point = boardPointForOffset(new Point(0, -1));
        hexagonAtPoint(point).setTerrainType(TerrainType.JUNGLE);

        point = boardPointForOffset(new Point(1, -1));
        hexagonAtPoint(point).setTerrainType(TerrainType.LAKE);

        point = boardPointForOffset(new Point(0, 1));
        hexagonAtPoint(point).setTerrainType(TerrainType.GRASSLANDS);

        point = boardPointForOffset(new Point(-1, 1));
        hexagonAtPoint(point).setTerrainType(TerrainType.ROCKY);

        Point[] points = new Point[] {
                boardPointForOffset(new Point(0, 0)),
                boardPointForOffset(new Point(0, -1)),
                boardPointForOffset(new Point(1, -1)),
                boardPointForOffset(new Point(0, 1)),
                boardPointForOffset(new Point(-1, 1))
        };

        for (Point p : points) {
            Hexagon hex = hexagonAtPoint(p);
            hex.incrementLevel();
            hex.setTileID(nextTileID);
            //hex.setOccupied(false);

            minBoardX = java.lang.Math.min(minBoardX, (int)p.getX());
            minBoardY = java.lang.Math.min(minBoardY, (int)p.getY());
        }

        nextTileID++;
    }

    // Didn't want to make a new PointUtils class rn sorry not sorry
    static public Point pointTranslatedByPoint(Point point, Point offset) {
        Point copy = new Point(point);
        copy.translate(offset.x, offset.y);
        return copy;
    }

    public Point centerOfBoard() {
        return new Point(dimensions/2, dimensions/2);
    }

    public Point boardPointForOffset(Point offset) {
        return Board.pointTranslatedByPoint(centerOfBoard(), offset);
    }

    public void placeTile(Tile tileToPlace, Point3D centerOffset) {
        Point axialOffset = Board.cubeToAxial(centerOffset);

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tileToPlace.getOrientation());
        HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

        Point points[] = new Point[3];
        points[0] = boardPointForOffset(axialOffset);
        points[1] = Board.pointTranslatedByPoint(points[0], abOffsets.get(HexagonPosition.A));
        points[2] = Board.pointTranslatedByPoint(points[0], abOffsets.get(HexagonPosition.B));

        hexagonAtPoint(points[0]).setTerrainType(TerrainType.VOLCANO);
        hexagonAtPoint(points[1]).setTerrainType(tileToPlace.getTerrainTypeForPosition(HexagonPosition.A));
        hexagonAtPoint(points[2]).setTerrainType(tileToPlace.getTerrainTypeForPosition(HexagonPosition.B));

        for (Point point : points) {
            Hexagon hex = hexagonAtPoint(point);
            hex.incrementLevel();
            hex.setTileID(nextTileID);
            //hex.setOccupied(false);

            minBoardX = java.lang.Math.min(minBoardX, (int)point.getX());
            minBoardY = java.lang.Math.min(minBoardY, (int)point.getY());
        }

        nextTileID++;
    }

    public HashMap<Point, Boolean> offsetsAtEdgeOfCurrentlyPlayedBoard() {
        // This does a BFS from the center of the board in order to find all the empty hexagons at the edge where we can place a tile.
        HashMap<Point, Boolean> validOffsets = new HashMap<>();
        HashMap<Point, Boolean> visited = new HashMap<>();

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(new Point(0, 0));

        while (!queue.isEmpty()) {
            Point offset = queue.remove(0);

            Point point = boardPointForOffset(offset);
            Hexagon hex = hexagonAtPoint(point);

            // If this hex is empty, we're at the edge of played board. We can potentially place there.
            if (visited.get(offset) == null && hex.getTerrainType() == TerrainType.EMPTY) {
                validOffsets.put(offset, true);
            }

            visited.put(offset, true);

            ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
            for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                appliedNeighborOffsets.add(Board.pointTranslatedByPoint(offset, neighborOffset));
            }

            for (Point neighborOffset : appliedNeighborOffsets) {
                Point neighborPoint = boardPointForOffset(neighborOffset);
                Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                if (neighborHex.getTerrainType() == TerrainType.EMPTY) {
                    validOffsets.put(neighborOffset, true);
                    visited.put(neighborOffset, true);
                } else if (visited.get(neighborOffset) == null) {
                    queue.add(neighborOffset);
                }
            }
        }

        //TODO: Add spots where we can potentially stack

        return validOffsets;
    }

    public HashMap<Point, Boolean> volcanoesOnCurrentlyPlayedBoard() {
        HashMap<Point, Boolean> validOffsets = new HashMap<>();
        HashMap<Point, Boolean> visited = new HashMap<>();

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(new Point(0, 0));

        while (!queue.isEmpty()) {
            Point offset = queue.remove(0);

            Point point = boardPointForOffset(offset);
            Hexagon hex = hexagonAtPoint(point);

            // If this hex is empty, we're at the edge of played board. We can potentially place there.
            if (visited.get(offset) == null && hex.getTerrainType() == TerrainType.VOLCANO) {
                validOffsets.put(offset, true);
            }

            visited.put(offset, true);

            ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
            for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                appliedNeighborOffsets.add(Board.pointTranslatedByPoint(offset, neighborOffset));
            }

            for (Point neighborOffset : appliedNeighborOffsets) {
                Point neighborPoint = boardPointForOffset(neighborOffset);
                Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                if (visited.get(neighborOffset) == null && neighborHex.getTerrainType() != TerrainType.EMPTY) {
                    queue.add(neighborOffset);
                }
            }
        }

        //TODO: Add spots where we can potentially stack

        return validOffsets;
    }

    public boolean canPlaceTileAtEdgeOffset(Tile tile, Point offset) {
        Set<Point> edgePoints = offsetsAtEdgeOfCurrentlyPlayedBoard().keySet();
        if (!edgePoints.contains(offset)) {
            return false;
        }

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tile.getOrientation());
        HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

        Point pointA = boardPointForOffset(Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.A)));
        Point pointB = boardPointForOffset(Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.B)));

        Hexagon hexA = hexagonAtPoint(pointA);
        Hexagon hexB = hexagonAtPoint(pointB);

        return hexA.getTerrainType() == TerrainType.EMPTY && hexB.getTerrainType() == TerrainType.EMPTY;
    }

    public HashMap<RequirementsToStack, Boolean> requirementsToStack() {
        HashMap<RequirementsToStack, Boolean> stackInfo = new HashMap<>();

        Set<Point> volcanoOffsets = volcanoesOnCurrentlyPlayedBoard().keySet();

        for (Point offset : volcanoOffsets) {
            for (int orientation = 1; orientation < 7; orientation++) {
                HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(orientation);
                HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

                Point pointA = boardPointForOffset(Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.A)));
                Point pointB = boardPointForOffset(Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.B)));

                Hexagon volcanoHexagon = hexagonAtPoint(boardPointForOffset(offset));
                Hexagon hexagonA = hexagonAtPoint(pointA);
                Hexagon hexagonB = hexagonAtPoint(pointB);

                if (volcanoHexagon.isTotoroOnTop() || hexagonA.isTotoroOnTop() || hexagonB.isTotoroOnTop()) {
                    continue;
                }

                if (volcanoHexagon.isTigerOnTop() || hexagonA.isTigerOnTop() || hexagonB.isTigerOnTop()) {
                    continue;
                }

                if (hexagonA.getTileID() != hexagonB.getTileID() || hexagonA.getTileID() != volcanoHexagon.getTileID()) {
                    if (hexagonA.getLevel() == hexagonB.getLevel() && hexagonA.getLevel() == hexagonB.getLevel()) {

                    }
                }
            }
        }

        return stackInfo;
    }

    /***** CONVERSIONS *****/

    /*
    # convert cube to axial
    q = x
    r = z
    */

    public static Point cubeToAxial(Point3D cube) {
        return new Point((int)cube.getX(), (int)cube.getZ());
    }

    /*
    # convert axial to cube
    x = q
    z = r
    y = -x-z
    */

    public static Point3D axialToCube(Point point) {
        return new Point3D(point.getX(),-point.getX()-point.getY(), point.getY());
    }

    /***** GETTERS *****/

    public Hexagon hexagonAtPoint(Point p) {
        return boardStorage[p.x][p.y];
    }

    public TerrainType getTerrainTypeAtPoint(Point point) {
        return hexagonAtPoint(point).getTerrainType();
    }

    public int getLevelAtPoint(Point point) {
        return hexagonAtPoint(point).getLevel();
    }

    public int getTileIDAtPoint(Point point) {
        return hexagonAtPoint(point).getTileID();
    }

    public int getNextTileID() {
        return nextTileID;
    }

    public int getMinX() {
        return minBoardX;
    }

    public int getMinY() {
        return minBoardY;
    }

    public int getMaxX() {
        return maxBoardX;
    }

    public int getMaxY() {
        return maxBoardY;
    }

    /*
    public Point getPointOfHexagon(Hexagon hexagon){
        for (int ii = minBoardX - 3; ii < maxBoardX + 3; ii++) {
            for (int jj = minBoardY - 3; jj < maxBoardY + 3; jj++) {
                if (gameBoard.get(jj).get(ii) == hexagon) {
                    Point point = new Point(ii, jj);
                    return point;
                }
            }
        }
        Point fakePoint = new Point(0,0);
        return fakePoint;
    }

    public Point getPointOfWhereVolcanoWillBePlaced(Tile tile){
        TileOrientation tileOrientation = tile.getOrientation();
        //if(tileOrientation ==  tileOrientation.BOTTOMHEAVY && )
        //TODO: how do I do this
        return new Point(0, 0);
    }
    */

    /***** ACTIONS *****/

    // Founds a new settlement at the specified hexagon
    // Puts one villager at the location and creates a new settlement object
    public void foundSettlementAtPoint(Point point, int id) {
        hexagonAtPoint(point).setOccupied(id);
        settlementManager.addNewSettlement(new Settlement(point));
    }

    // Places a Totoro at the specified Hexagon
    public void buildTotoroSanctuary(Point point, int id) {
        // Add a Totoro to the hexagon
        hexagonAtPoint(point).setTotoroOnTop(true);

        // Find the particular settlement this point belongs to and add the point to the settlement
        // as well as setting the Totoro to be true;
        Settlement settlementToUpdate = settlementManager.getSettlementFromPoint(point);
        settlementToUpdate.addPointToSettlement(point);
        settlementToUpdate.addTotoro();
    }

    // Places a Tiger at the specified hexagon
    // Assumes that the location is a valid spot
    public void buildTigerPlayground(Point point, int id) {
        // Add a Tiger to the hexagon
        hexagonAtPoint(point).setTigerOnTop(true);

        // Find the particular settlement this point belongs to and add the point to the settlement
        // as well as setting the Tiger to be true;
        Settlement settlementToUpdate = settlementManager.getSettlementFromPoint(point);
        settlementToUpdate.addPointToSettlement(point);
        settlementToUpdate.addTiger();
    }
}