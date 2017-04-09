import javafx.geometry.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

public class Board {
    private Hexagon[][] boardStorage;
    private SettlementManager settlementManager;

    private int nextTileID = 1;

    private int dimensions = 200;

    private int minBoardXOffset = -3;
    private int maxBoardXOffset = 3;

    private int minBoardYOffset = -3;
    private int maxBoardYOffset = 3;

    public Board() {
        settlementManager = new SettlementManager(this);

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
            hex.setOccupied(Integer.toString(Integer.MIN_VALUE));
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
            hex.setOccupied(Integer.toString(Integer.MIN_VALUE));
        }

        Point offsets[] = new Point[]{ axialOffset,
                                        Board.pointTranslatedByPoint(axialOffset, abOffsets.get(HexagonPosition.A)),
                                        Board.pointTranslatedByPoint(axialOffset, abOffsets.get(HexagonPosition.B))
        };

        for (Point offset : offsets) {
            minBoardXOffset = java.lang.Math.min(minBoardXOffset, (int)offset.getX() - 2);
            minBoardYOffset = java.lang.Math.min(minBoardYOffset, (int)offset.getY() - 2);
            maxBoardXOffset = java.lang.Math.max(maxBoardXOffset, (int)offset.getX() + 2);
            maxBoardYOffset = java.lang.Math.max(maxBoardYOffset, (int)offset.getY() + 2);
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

        return validOffsets;
    }

    public HashMap<Point, Boolean> offsetsEligibleForSettlementFounding() {
        // This does a BFS from the center of the board in order to find places where we can found a settlement.
        HashMap<Point, Boolean> validOffsets = new HashMap<>();
        HashMap<Point, Boolean> visited = new HashMap<>();

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(new Point(0, 0));

        while (!queue.isEmpty()) {
            Point offset = queue.remove(0);

            Point point = boardPointForOffset(offset);
            Hexagon hex = hexagonAtPoint(point);

            if (visited.get(offset) == null && !hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO) {
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

                if (!neighborHex.isOccupied() && neighborHex.getTerrainType() != TerrainType.VOLCANO) {
                    validOffsets.put(neighborOffset, true);
                    visited.put(neighborOffset, true);
                } else if (neighborHex.getTerrainType() == TerrainType.EMPTY) {
                    visited.put(neighborOffset, true);
                } else if (visited.get(neighborOffset) == null) {
                    queue.add(neighborOffset);
                }
            }
        }

        return validOffsets;
    }


    public HashMap<Point, Boolean> volcanoesOnCurrentlyPlayedBoard() {
        // Finds all volcano points on the current board.
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

        return validOffsets;
    }

    public boolean canPlaceTileAtEdgeOffset(Tile tile, Point offset) {
        // Checks if you can place this tile (at it's current orientation) on this edge offset.
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
        // Finds all volcano points and returns what orientation you can use to stack at that point, if any.
        HashMap<RequirementsToStack, Boolean> stackInfo = new HashMap<>();

        Set<Point> volcanoOffsets = volcanoesOnCurrentlyPlayedBoard().keySet();

        for (Point offset : volcanoOffsets) {
            for (int orientation = 1; orientation < 7; orientation++) {
                HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(orientation);
                HashMap<HexagonPosition, Point> abOffsets = calc.offsetsForAB();

                Point offsetA = Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.A));
                Point offsetB = Board.pointTranslatedByPoint(offset, abOffsets.get(HexagonPosition.B));

                Point pointA = boardPointForOffset(offsetA);
                Point pointB = boardPointForOffset(offsetB);

                Hexagon volcanoHexagon = hexagonAtPoint(boardPointForOffset(offset));
                Hexagon hexagonA = hexagonAtPoint(pointA);
                Hexagon hexagonB = hexagonAtPoint(pointB);

                if (hexagonA.isTotoroOnTop() || hexagonB.isTotoroOnTop()) {
                    continue;
                }

                if (hexagonA.isTigerOnTop() || hexagonB.isTigerOnTop()) {
                    continue;
                }

                if (volcanoHexagon.getTileID() == hexagonA.getTileID() && hexagonA.getTileID() == hexagonB.getTileID()) {
                    continue;
                }

                if (volcanoHexagon.getLevel() != hexagonA.getLevel() || hexagonA.getLevel() != hexagonB.getLevel()) {
                    continue;
                }

                if (settlementManager.getSettlementForOffset(offsetA) != null) {
                    Settlement settlementA = settlementManager.getSettlementForOffset(offsetA);
                    if (settlementA.size() == 1) {
                        continue;
                    }

                    if (settlementA.size() == 2) {
                        Point otherOffset = offsetB;
                        for (Point candidate : settlementA.getOffsets()) {
                            if (!candidate.equals(offsetA)) {
                                otherOffset = candidate;
                                break;
                            }
                        }

                        if (!otherOffset.equals(offsetB)) {
                            RequirementsToStack stackReqs = new RequirementsToStack(offset, orientation);
                            stackInfo.put(stackReqs, true);
                        }
                    } else if (settlementA.size() > 2) {
                        RequirementsToStack stackReqs = new RequirementsToStack(offset, orientation);
                        stackInfo.put(stackReqs, true);
                    }
                }

                if (settlementManager.getSettlementForOffset(offsetB) != null) {
                    Settlement settlementB = settlementManager.getSettlementForOffset(offsetB);
                    if (settlementB.size() == 1) {
                        continue;
                    }

                    if (settlementB.size() == 2) {
                        Point otherOffset = offsetA;
                        for (Point candidate : settlementB.getOffsets()) {
                            if (!candidate.equals(offsetB)) {
                                otherOffset = candidate;
                                break;
                            }
                        }

                        if (!otherOffset.equals(offsetA)) {
                            RequirementsToStack stackReqs = new RequirementsToStack(offset, orientation);
                            stackInfo.put(stackReqs, true);
                        }
                    } else if (settlementB.size() > 2) {
                        RequirementsToStack stackReqs = new RequirementsToStack(offset, orientation);
                        stackInfo.put(stackReqs, true);
                    }
                }

            }
        }

        return stackInfo;
    }

    public HashMap<Point, Boolean> offsetsAtEdgeOfSettlementAtOffset(Point settlementOffset) {
        // This does a BFS from settlementOffset and finds the offsets at the edge of the settlement.
        HashMap<Point, Boolean> validOffsets = new HashMap<>();
        HashMap<Point, Boolean> visited = new HashMap<>();

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(settlementOffset);

        Point boardPoint = boardPointForOffset(settlementOffset);
        String settlementID =  hexagonAtPoint(boardPoint).getOccupiedID();

        if (settlementID.equals(Integer.toString(Integer.MIN_VALUE))) {
            return validOffsets;
        }

        while (!queue.isEmpty()) {
            Point offset = queue.remove(0);

            Point point = boardPointForOffset(offset);
            Hexagon hex = hexagonAtPoint(point);

            visited.put(offset, true);

            ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
            for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                appliedNeighborOffsets.add(Board.pointTranslatedByPoint(offset, neighborOffset));
            }

            for (Point neighborOffset : appliedNeighborOffsets) {
                Point neighborPoint = boardPointForOffset(neighborOffset);
                Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                if (!neighborHex.getOccupiedID().equals(settlementID)) {
                    if (!neighborHex.isOccupied()) {
                        validOffsets.put(neighborOffset, true);
                    }
                    visited.put(neighborOffset, true);
                } else if (visited.get(neighborOffset) == null) {
                    queue.add(neighborOffset);
                }
            }
        }

        return validOffsets;
    }

    private void expandAndConquerFromRootOffset(Point rootOffset, String settlementID) {
        HashMap<Point, Boolean> visited = new HashMap<>();

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(rootOffset);

        Hexagon rootHex = hexagonAtPoint(boardPointForOffset(rootOffset));
        TerrainType desiredTerrainType = rootHex.getTerrainType();

        while (!queue.isEmpty()) {
            Point offset = queue.remove(0);
            Hexagon hex = hexagonAtPoint(boardPointForOffset(offset));

            if (visited.get(offset) == null && hex.getTerrainType() == desiredTerrainType) {
                if (!hex.isOccupied()) {
                    hex.setOccupied(settlementID);
                }
            }

            visited.put(offset, true);

            ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
            for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                appliedNeighborOffsets.add(Board.pointTranslatedByPoint(offset, neighborOffset));
            }

            for (Point neighborOffset : appliedNeighborOffsets) {
                Point neighborPoint = boardPointForOffset(neighborOffset);
                Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                if (visited.get(neighborOffset) == null && neighborHex.getTerrainType() == desiredTerrainType) {
                    if (!neighborHex.isOccupied()) {
                        neighborHex.setOccupied(settlementID);
                    }
                    queue.add(neighborOffset);
                }

                visited.put(neighborOffset, true);
            }
        }
    }

    private int meepleCountForExpansionFromOffset(Point rootOffset) {
        Integer meepleCount = 0;

        HashMap<Point, Boolean> visited = new HashMap<>();

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(rootOffset);

        Hexagon rootHex = hexagonAtPoint(boardPointForOffset(rootOffset));
        TerrainType desiredTerrainType = rootHex.getTerrainType();

        while (!queue.isEmpty()) {
            Point offset = queue.remove(0);
            Hexagon hex = hexagonAtPoint(boardPointForOffset(offset));

            if (visited.get(offset) == null && hex.getTerrainType() == desiredTerrainType) {
                if (!hex.isOccupied()) {
                    meepleCount += hex.getLevel();
                }
            }

            visited.put(offset, true);

            ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
            for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                appliedNeighborOffsets.add(Board.pointTranslatedByPoint(offset, neighborOffset));
            }

            for (Point neighborOffset : appliedNeighborOffsets) {
                Point neighborPoint = boardPointForOffset(neighborOffset);
                Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                if (visited.get(neighborOffset) == null && neighborHex.getTerrainType() == desiredTerrainType) {
                    if (!neighborHex.isOccupied()) {
                        meepleCount += hex.getLevel();
                        queue.add(neighborOffset);
                    }
                }

                visited.put(neighborOffset, true);
            }
        }

        return meepleCount;
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

    public Point getMinOffset() {
        return new Point(minBoardXOffset, minBoardYOffset);
    }

    public Point getMaxOffset() {
        return new Point(maxBoardXOffset, maxBoardYOffset);
    }

    public SettlementManager getSettlementManager(){
        return settlementManager;
    }

    /***** ACTIONS *****/

    public void foundSettlementAtOffset(Point offset, String id) {
        hexagonAtPoint(boardPointForOffset(offset)).setOccupied(id);
    }

    public void expandSettlementAtOffset(Point offset, TerrainType type, String id) {
        Set<Point> edgeOffsets = offsetsAtEdgeOfSettlementAtOffset(offset).keySet();

        if (edgeOffsets.isEmpty()) {
            return;
        }

        Set<Point> offsetsOfTerrain = edgeOffsets.stream().filter( edgeOffset -> {
            Hexagon hex = hexagonAtPoint(boardPointForOffset(edgeOffset));
            return hex.getTerrainType() == type;
        }).collect(Collectors.toSet());

        if (offsetsOfTerrain.isEmpty()) {
            return;
        }

        for (Point terrainOffset : offsetsOfTerrain) {
            expandAndConquerFromRootOffset(terrainOffset, id);
        }
    }

    public int numberOfMeeplesNeededForExpansion(Point offset, TerrainType type, String id) {
        Set<Point> edgeOffsets = offsetsAtEdgeOfSettlementAtOffset(offset).keySet();

        if (edgeOffsets.isEmpty()) {
            return Integer.MIN_VALUE;
        }

        Set<Point> offsetsOfTerrain = edgeOffsets.stream().filter( edgeOffset -> {
            Hexagon hex = hexagonAtPoint(boardPointForOffset(edgeOffset));
            return hex.getTerrainType() == type;
        }).collect(Collectors.toSet());

        if (offsetsOfTerrain.isEmpty()) {
            return Integer.MIN_VALUE;
        }

        int meepleCount = 0;
        for (Point terrainOffset : offsetsOfTerrain) {
            meepleCount += meepleCountForExpansionFromOffset(terrainOffset);
        }

        return meepleCount;
    }



    public void buildTotoroSanctuaryAtOffset(Point offset, String id) {
        Hexagon hex = hexagonAtPoint(boardPointForOffset(offset));
        hex.setTotoroOnTop(true);
        hex.setOccupied(id);
    }

    public void buildTigerPlaygroundAtOffset(Point offset, String id) {
        Hexagon hex = hexagonAtPoint(boardPointForOffset(offset));
        hex.setTigerOnTop(true);
        hex.setOccupied(id);
    }
    public HashMap<Point, Boolean> offsetsAroundAllSettlementsButOneSpecified(Settlement expansionSettlement, List<Settlement> ourSettlements){
// This does a BFS from settlementOffset and finds the offsets at the edge of each settlement given a settlement we're expanding from.
        HashMap<Point, Boolean> validOffsets = new HashMap<>();
        HashMap<Point, Boolean> visited = new HashMap<>();
        for(Settlement settlement: ourSettlements) {

            if(settlement == expansionSettlement){
                continue;
            }

            Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];

            ArrayList<Point> queue = new ArrayList<>();
            queue.add(firstOffsetInSettlement);

            String settlementID = hexagonAtPoint(boardPointForOffset(firstOffsetInSettlement)).getOccupiedID();

            if (settlementID.equals(Integer.toString(Integer.MIN_VALUE))) {
                return validOffsets;
            }

            while (!queue.isEmpty()) {
                Point otherOffset = queue.remove(0);

                Point point = boardPointForOffset(otherOffset);
                Hexagon hex = hexagonAtPoint(point);

                visited.put(otherOffset, true);

                ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
                for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                    appliedNeighborOffsets.add(Board.pointTranslatedByPoint(otherOffset, neighborOffset));
                }

                for (Point neighborOffset : appliedNeighborOffsets) {
                    Point neighborPoint = boardPointForOffset(neighborOffset);
                    Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                    if (!neighborHex.getOccupiedID().equals(settlementID)) {
                        if (!neighborHex.isOccupied()) {
                            validOffsets.put(neighborOffset, true);
                        }
                        visited.put(neighborOffset, true);
                    } else if (visited.get(neighborOffset) == null) {
                        queue.add(neighborOffset);
                    }
                }
            }
        }

        return validOffsets;
    }
    public boolean doesExpansionConnectTwoSettlements(Point offset, TerrainType type, List<Settlement> settlements, Settlement settlement) {
        Set<Point> edgeOffsets = offsetsAroundAllSettlementsButOneSpecified(settlement, settlements).keySet();
        Set<Point> edgeOffsetsForExpansion = offsetsAtEdgeOfSettlementAtOffset(offset).keySet();

        if (edgeOffsetsForExpansion.isEmpty()) {
            return false;
        }

        Set<Point> offsetsOfTerrain = edgeOffsetsForExpansion.stream().filter( edgeOffset -> {
            Hexagon hex = hexagonAtPoint(boardPointForOffset(edgeOffset));
            return hex.getTerrainType() == type;
        }).collect(Collectors.toSet());

        if (offsetsOfTerrain.isEmpty()) {
            return false;
        }


        int meepleCount = 0;
        for (Point terrainOffset : offsetsOfTerrain) {
            //for each Point compareOffset within edgeOffsets
            for(Point compareOffset: edgeOffsets) {
                //if terrainOffset Point equals that compareOffset at that terrainOffset, return true
                if (terrainOffset == compareOffset) {
                    return true;
                }
            }

        }
        return false;
    }
    public HashMap<Point, Boolean> offsetsAroundAllOurSettlements( List<Settlement> ourSettlements){
// This does a BFS from settlementOffset and finds the offsets at the edge of each settlement given a settlement we're expanding from.
        HashMap<Point, Boolean> validOffsets = new HashMap<>();
        HashMap<Point, Boolean> visited = new HashMap<>();
        for(Settlement settlement: ourSettlements) {

            //boardPointForOffset being instantiated. Use this as the offset to bfs from each time
            Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];
            Point boardPointForOffset = boardPointForOffset(firstOffsetInSettlement);

            ArrayList<Point> queue = new ArrayList<>();
            queue.add(boardPointForOffset);

            String settlementID = hexagonAtPoint(boardPointForOffset(boardPointForOffset)).getOccupiedID();

            if (settlementID.equals(Integer.toString(Integer.MIN_VALUE))) {
                return validOffsets;
            }

            while (!queue.isEmpty()) {
                Point otherOffset = queue.remove(0);

                Point point = boardPointForOffset(boardPointForOffset);
                Hexagon hex = hexagonAtPoint(point);

                visited.put(otherOffset, true);

                ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
                for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                    appliedNeighborOffsets.add(Board.pointTranslatedByPoint(boardPointForOffset, neighborOffset));
                }

                for (Point neighborOffset : appliedNeighborOffsets) {
                    Point neighborPoint = boardPointForOffset(neighborOffset);
                    Hexagon neighborHex = hexagonAtPoint(neighborPoint);

                    if (!neighborHex.getOccupiedID().equals(settlementID)) {
                        if (!neighborHex.isOccupied()) {
                            validOffsets.put(neighborOffset, true);
                        }
                        visited.put(neighborOffset, true);
                    } else if (visited.get(neighborOffset) == null) {
                        queue.add(neighborOffset);
                    }
                }
            }
        }

        return validOffsets;
    }
}