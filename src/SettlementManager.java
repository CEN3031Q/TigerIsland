/**
 * Created by hugh on 4/2/17.
 * This class is responsible for managing all the settlements on the board
 * It keeps a list of settlements - which are a list of Points
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class SettlementManager {
    private Board board;
    private HashSet<Settlement> listOfSettlements;

    public SettlementManager(Board board) {
        this.board = board;
        listOfSettlements = new HashSet<>();
    }

    public int sizeOfSettlementAtOffset(Point offset) {
        for(Settlement settlement : listOfSettlements) {
            if (settlement.containsOffset(offset)) {
                return settlement.size();
            }
        }
        return 0;
    }

    public Settlement getSettlementForOffset(Point offset) {
        for (Settlement settlement : listOfSettlements) {
            if (settlement.containsOffset(offset)) {
                return settlement;
            }
        }
        return null;
    }

    public void updateSettlements() {
        HashMap<Point, Boolean> visited = new HashMap<>();
        HashSet<Settlement> updatedListOfSettlements = new HashSet<>();

        int minBoardX = board.getMinOffset().x;
        int maxBoardX = board.getMaxOffset().x;
        int minBoardY = board.getMinOffset().y;
        int maxBoardY = board.getMaxOffset().y;

        for (int x = minBoardX; x < maxBoardX; x++) {
            for (int y = minBoardY; y < maxBoardY; y++) {

                Point rootOffset =  new Point(x, y);
                Hexagon rootHex = board.hexagonAtPoint(board.boardPointForOffset(rootOffset));

                if (visited.get(rootOffset) == null && rootHex.isOccupied()) {

                    Settlement settlement = new Settlement();

                    ArrayList<Point> queue = new ArrayList<>();
                    queue.add(rootOffset);

                    String settlementID = board.hexagonAtPoint(board.boardPointForOffset(rootOffset)).getOccupiedID();

                    while (!queue.isEmpty()) {
                        Point offset = queue.remove(0);

                        settlement.addOffset(offset);
                        visited.put(offset, true);

                        ArrayList<Point> appliedNeighborOffsets = new ArrayList<>();
                        for (Point neighborOffset : HexagonNeighborsCalculator.hexagonNeighborOffsets()) {
                            appliedNeighborOffsets.add(Board.pointTranslatedByPoint(offset, neighborOffset));
                        }

                        for (Point neighborOffset : appliedNeighborOffsets) {
                            Point neighborPoint = board.boardPointForOffset(neighborOffset);
                            Hexagon neighborHex = board.hexagonAtPoint(neighborPoint);

                            if (visited.get(neighborOffset) == null && neighborHex.getOccupiedID().equals(settlementID)) {
                                queue.add(neighborOffset);
                            } else if (!neighborHex.isOccupied() || neighborHex.getTerrainType() == TerrainType.EMPTY) {
                                visited.put(neighborOffset, true);
                            }
                        }

                    }

                    updatedListOfSettlements.add(settlement);

                }
            }
        }

        listOfSettlements = updatedListOfSettlements;
    }

    public Set<Settlement> getListOfSettlements(){
        return listOfSettlements;
    }

}
