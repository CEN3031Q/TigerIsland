/**
 * Created by gonzalonunez on 3/21/17.
 */

import cucumber.api.java8.Ar;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameAI implements GameActionPerformer {
    private String id;
    private Inventory inventory;

    public GameAI(String id) {
        this.id = id;
        this.inventory = new Inventory(id);
    }

    public TileAction tileAction(Tile tile, Board board) {
        board.getSettlementManager().updateSettlements();

        Set<RequirementsToStack> stackReqs = board.requirementsToStack().keySet();
        for (RequirementsToStack req : stackReqs) {
            Point offset = req.getOffset();
            int orientation = req.getOrientation();

            HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(orientation);
            HashMap<HexagonPosition, Point> offsetsForAB = calc.offsetsForAB();

            Point offsetA = offsetsForAB.get(HexagonPosition.A);
            Point offsetB = offsetsForAB.get(HexagonPosition.B);

            Settlement settlementForA = board.getSettlementManager().getSettlementForOffset(offsetA);
            Settlement settlementForB = board.getSettlementManager().getSettlementForOffset(offsetB);

            if (settlementForA != null) {
                Point aOffset = settlementForA.getOffsets().iterator().next();
                String settlementAID = board.hexagonAtPoint(board.boardPointForOffset(aOffset)).getOccupiedID();

                if (settlementForB != null) {
                    Point bOffset = settlementForB.getOffsets().iterator().next();
                    String settlementBID = board.hexagonAtPoint(board.boardPointForOffset(bOffset)).getOccupiedID();

                    if (!settlementAID.equals(id) && !settlementBID.equals(id) && settlementForA.size() == 6) {
                        return new TileAction(id, tile, offset, orientation);
                    }

                } else {
                    if (!settlementAID.equals(id) && settlementForA.size() == 5) {
                        return new TileAction(id, tile, offset, orientation);
                    }
                }
            }

            if (settlementForB != null) {
                Point bOffset = settlementForB.getOffsets().iterator().next();
                String settlementBID = board.hexagonAtPoint(board.boardPointForOffset(bOffset)).getOccupiedID();

                if (!settlementBID.equals(id) && settlementForB.size() == 5) {
                    return new TileAction(id, tile, offset, orientation);
                }
            }
        }

        /** TRY TO PLACE BECAUSE WE DIDN'T STACK **/

        Set<Point> edgePoints = board.offsetsAtEdgeOfCurrentlyPlayedBoard().keySet();
        tile.setOrientation(ThreadLocalRandom.current().nextInt(1,7));

        while (true) {
            for (Point edgeOffset : edgePoints) {
                if (board.canPlaceTileAtEdgeOffset(tile, edgeOffset)) {
                    return new TileAction(id, tile, edgeOffset, tile.getOrientation());
                }
            }
            int currentOrientation = tile.getOrientation();
            tile.setOrientation(currentOrientation + 1);
        }
    }

    public BuildAction buildAction(Board board) {
        board.getSettlementManager().updateSettlements();

        /** TRY TO PLACE A TOTORO **/
        //TODO: Make totoro not connect settlements together
        if (!inventory.isTotoroEmpty()) {
            List<Settlement> settlements = ourSettlementsOnBoard(board);

            Collections.sort(settlements, new Comparator<Settlement>() {
                @Override
                public int compare(Settlement lhs, Settlement rhs) {
                    return lhs.getOffsets().size() - rhs.getOffsets().size();
                }
            });

            for (Settlement settlement : settlements) {
                if (settlement.size() >= 5) {
                    Set<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset((Point)settlement.getOffsets().toArray()[0]).keySet();
                    for (Point offset : offsets) {
                        Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(offset));
                        if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO && hex.getTerrainType() != TerrainType.EMPTY) {
                            inventory.removeTotoroPiece();
                            return new BuildAction(id, BuildActionType.TOTORO_SANCTUARY, offset);
                        }
                    }

                }
            }
        }

        /** TRY TO PLACE A TIGER **/
        if (!inventory.isTigerEmpty()) {
            Set<Point> offsets = eligibleTigerOffsets(board);
            if (!offsets.isEmpty()) {
                inventory.removeTigerPiece();
                return new BuildAction(id, BuildActionType.TIGER_PLAYGROUND, offsets.iterator().next());
            }
        }


        /** TRY TO EXPAND OR PLACE A MEEPLE **/
        if (!inventory.isMeepleEmpty()) {
            List<Settlement> settlements = ourSettlementsOnBoard(board);

            //Our first turn
            if (settlements.isEmpty()) {
                Set<Point> offsets = board.offsetsEligibleForSettlementFounding().keySet();
                Integer random = ThreadLocalRandom.current().nextInt(0, offsets.size());
                inventory.removeMeeplePiece();
                return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, (Point)offsets.toArray()[random]);
            }

            for (Settlement settlement : settlements) {
                Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];

                int meeplesForLake = board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.LAKE, id);

                if(meeplesForLake + settlement.size() <= 7 &&
                   meeplesForLake <= inventory.getMeepleSize() &&
                   !board.doesExpansionConnectTwoSettlements(firstOffsetInSettlement, TerrainType.LAKE, settlements, settlement) &&
                   meeplesForLake + settlement.size() >= 4
                   ) {
                    for(int i = 0; i < (board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.LAKE, id));i++){
                        inventory.removeMeeplePiece();
                    }
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, firstOffsetInSettlement, TerrainType.LAKE);
                }

                int meeplesForRocky = board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.ROCKY, id);

                if(meeplesForRocky + settlement.size() <=7 &&
                        meeplesForRocky <= inventory.getMeepleSize() &&
                 !board.doesExpansionConnectTwoSettlements(firstOffsetInSettlement, TerrainType.ROCKY, settlements, settlement) &&
                        meeplesForRocky + settlement.size() >= 4
                 ){
                    for(int i = 0; i < (board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.ROCKY, id));i++){
                        inventory.removeMeeplePiece();
                    }
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, firstOffsetInSettlement, TerrainType.ROCKY);
                }

                int meeplesForGrasslands = board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.GRASSLANDS, id);

                if(meeplesForGrasslands + settlement.size() <=7 &&
                        meeplesForGrasslands <= inventory.getMeepleSize()&&
                !board.doesExpansionConnectTwoSettlements(firstOffsetInSettlement, TerrainType.GRASSLANDS, settlements, settlement) &&
                        meeplesForGrasslands + settlement.size() >= 4
                ){
                    for(int i = 0; i < (board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.GRASSLANDS, id));i++){
                        inventory.removeMeeplePiece();
                    }
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, firstOffsetInSettlement, TerrainType.GRASSLANDS);
                }

                int meeplesForJungle = board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.JUNGLE, id);

                if(meeplesForJungle + settlement.size() <=7 &&
                        meeplesForJungle <= inventory.getMeepleSize() &&
                !board.doesExpansionConnectTwoSettlements(firstOffsetInSettlement, TerrainType.JUNGLE, settlements, settlement) &&
                        meeplesForJungle + settlement.size() >= 4

                        ){
                    for(int i = 0; i < (board.numberOfMeeplesNeededForExpansion(firstOffsetInSettlement, TerrainType.JUNGLE, id));i++){
                        inventory.removeMeeplePiece();
                    }
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, firstOffsetInSettlement, TerrainType.JUNGLE);
                }

                if(settlement.size()==4){

                    Set<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset((Point)settlement.getOffsets().toArray()[0]).keySet();
                    for (Point offset : offsets) {
                        Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(offset));
                        if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO && hex.getLevel() == 1 && hex.getTerrainType() != TerrainType.EMPTY) {
                            inventory.removeMeeplePiece();
                            return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, offset);
                        }
                    }

                }
                if(settlement.size()==3){

                    Set<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset((Point)settlement.getOffsets().toArray()[0]).keySet();
                    for (Point offset : offsets) {
                        Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(offset));
                        if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO && hex.getLevel() == 1) {
                            inventory.removeMeeplePiece();
                            return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, offset);
                        }
                    }

                }
            }
            //found settlement not adjacent to one of our settlements
            int minBoardX = board.getMinOffset().x;
            int maxBoardX = board.getMaxOffset().x;
            int minBoardY = board.getMinOffset().y;
            int maxBoardY = board.getMaxOffset().y;

            for (int x = minBoardX; x < maxBoardX; x++) {
                for (int y = minBoardY; y < maxBoardY; y++) {
                    Point rootOffset =  new Point(x, y);
                    Hexagon rootHex = board.hexagonAtPoint(board.boardPointForOffset(rootOffset));
                    if(!rootHex.isOccupied() && rootHex.getTerrainType() != TerrainType.VOLCANO && rootHex.getTerrainType() != TerrainType.EMPTY && rootHex.getLevel() == 1) {
                        inventory.removeMeeplePiece();
                        return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, rootOffset);

                    }
                }
            }
        }
        
        return new BuildAction(id, BuildActionType.UNABLE_TO_BUILD, new Point(0, 0));

    }

    public List<Settlement> ourSettlementsOnBoard(Board board) {
        List<Settlement> results = new ArrayList<>();

        for (Settlement settlement : board.getSettlementManager().getListOfSettlements()) {
            Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];

            Point boardPointForOffset = board.boardPointForOffset(firstOffsetInSettlement);

            if (board.hexagonAtPoint(boardPointForOffset).getOccupiedID().equals(id)) {
                results.add(settlement);
            }
        }

        return results;
    }

    public Set<Point> eligibleTigerOffsets(Board board) {
        Set<Point> eligibleOffsets = new HashSet<>();

        List<Settlement> settlements = ourSettlementsOnBoard(board);

        for (Settlement settlement : settlements) {
            Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];

            Set<Point> offsetsAtEdge = board.offsetsAtEdgeOfSettlementAtOffset(firstOffsetInSettlement).keySet();

            for (Point offset : offsetsAtEdge) {
                Point boardPointForOffset = board.boardPointForOffset(offset);
                Hexagon hex = board.hexagonAtPoint(boardPointForOffset);

                if (hex.getLevel() >= 3 &&
                    !hex.isOccupied() &&
                    hex.getTerrainType() != TerrainType.VOLCANO &&
                    hex.getTerrainType() != TerrainType.EMPTY)
                {
                    eligibleOffsets.add(offset);
                }
            }
        }

        return eligibleOffsets;
    }
}