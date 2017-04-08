/**
 * Created by gonzalonunez on 3/21/17.
 */
import com.sun.scenario.effect.Offset;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GameAI implements GameActionPerformer {
    private int id;
    private Inventory inventory;

    public GameAI(int id) {
        this.id = id;
        this.inventory = new Inventory(id);
    }

    public Point tileAction(Tile tile, Board board) {
        //TODO: Make stacking decisions
        Set<RequirementsToStack> stackReqs = board.requirementsToStack().keySet();
        for (RequirementsToStack req : stackReqs) {
            Point offset = req.getOffset();
            int orientation = req.getOrientation();
            boolean nukeAtOffset;
            boolean ettlementBIsNukable;

            HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(orientation);
            HashMap<HexagonPosition, Point> offsetsForAB = calc.offsetsForAB();

            Point offsetA = offsetsForAB.get(HexagonPosition.A);
            Point offsetB = offsetsForAB.get(HexagonPosition.B);

            Settlement settlementForA = board.getSettlementManager().getSettlementForOffset(offsetA);
            Settlement settlementForB = board.getSettlementManager().getSettlementForOffset(offsetB);

            if (settlementForA != null) {

                // There is a settlement here. See if we want to stack and fuck it up.

                if(settlementForB != null){

                    //There are settlements on both of them
                    if(settlementForA.size() <= 6){
                        //return the tile nuking placement at that offset and that orientation
                    }
                }
                else{
                    //There is just a settlement on A
                    if(settlementForA.size() <= 5){
                        //return the tile nuking placement at that offset and that orientation
                    }

                }
            }


            if (settlementForB != null) {
                // There is just a settlement here at B
                if(settlementForB.size() <= 5){
                    //return the tile nuking placement at that offset and that orientation
                    //TODO: return a proper tile placement action
                }
            }


        }

        /**** PLACE AT EDGE ****/
        Set<Point> edgePoints = board.offsetsAtEdgeOfCurrentlyPlayedBoard().keySet();
        tile.setOrientation(ThreadLocalRandom.current().nextInt(1,7));

        while (true) {
            for (Point edgeOffset : edgePoints) {
                if (board.canPlaceTileAtEdgeOffset(tile, edgeOffset)) {
                    return edgeOffset;
                    //TODO: return a proper tile placement action
                }
            }
            int orientation = tile.getOrientation();
            tile.setOrientation(orientation + 1);
        }
        /*********************/
    }

    public BuildAction buildAction(Board board) {
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
                        if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO) {
                            inventory.removeTotoroPiece();
                            return new BuildAction(id, BuildActionType.TOTORO_SANCTUARY, offset);
                        }
                    }

                }
            }
        }

        /** TRY TO PLACE A TIGER **/
        if (!inventory.isTigerEmpty()) {
            List<Settlement> settlements = ourSettlementsOnBoard(board);

            for (Settlement settlement : settlements) {
                if (settlement.size() >= 1) {
                    List<Point> offsets = board.offsetsAtEdgeOfSettlementAtOffset((Point)settlement.getOffsets().toArray()[0])
                            .keySet()
                            .stream()
                            .filter(offset -> {
                                return board.hexagonAtPoint(board.boardPointForOffset(offset)).getLevel() >= 3;
                            })
                            .collect(Collectors.toList());

                    for (Point offset : offsets) {
                        Hexagon hex = board.hexagonAtPoint(board.boardPointForOffset(offset));
                        if (!hex.isOccupied() && hex.getTerrainType() != TerrainType.VOLCANO) {
                            inventory.removeTigerPiece();
                            return new BuildAction(id, BuildActionType.TIGER_PLAYGROUND, offset);
                        }
                    }
                }
            }
        }


        /** TRY TO EXPAND OR PLACE A MEEPLE **/
        if (!inventory.isMeepleEmpty()) {
            List<Settlement> settlements = ourSettlementsOnBoard(board);

            //Our first turn
            if (settlements.isEmpty()) {
                Set<Point> offsets = board.offsetsEligibleForSettlementFounding().keySet();
                Integer random = ThreadLocalRandom.current().nextInt(0, offsets.size());
                return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, (Point)offsets.toArray()[random]);
            }


            for (Settlement settlement : settlements) {


                Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];
                Point boardPointForOffset = board.boardPointForOffset(firstOffsetInSettlement);

                if(board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.LAKE, id) + settlement.size() <= 5 &&
                   board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.LAKE, id) <= inventory.getMeepleSize() &&
                   !board.doesExpansionConnectTwoSettlements(boardPointForOffset, TerrainType.LAKE, settlements, settlement)
                        ) {
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, boardPointForOffset, TerrainType.LAKE);
                }
                if(board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.ROCKY, id) + settlement.size() <=7 &&
                        board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.ROCKY, id) <= inventory.getMeepleSize() &&
                        !board.doesExpansionConnectTwoSettlements(boardPointForOffset, TerrainType.ROCKY, settlements, settlement)
                        ){
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, boardPointForOffset, TerrainType.ROCKY);
                }
                if(board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.GRASSLANDS, id) + settlement.size() <=7 &&
                        board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.GRASSLANDS, id) <= inventory.getMeepleSize()&&
                        !board.doesExpansionConnectTwoSettlements(boardPointForOffset, TerrainType.GRASSLANDS, settlements, settlement)
                        ){
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, boardPointForOffset, TerrainType.GRASSLANDS);
                }
                if(board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.JUNGLE, id) <=7 &&
                        board.numberOfMeeplesNeededForExpansion(boardPointForOffset, TerrainType.JUNGLE, id) <= inventory.getMeepleSize() &&
                        !board.doesExpansionConnectTwoSettlements(boardPointForOffset, TerrainType.JUNGLE, settlements, settlement)
                        ){
                    return new BuildAction(id, BuildActionType.EXPAND_SETTLEMENT, boardPointForOffset, TerrainType.JUNGLE);
                }

                if(settlement.size()==3 || settlement.size()==4){

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
                    if(!rootHex.isOccupied() && rootHex.getTerrainType() != TerrainType.VOLCANO && rootHex.getTerrainType() != TerrainType.EMPTY
                            && rootHex.getLevel() == 1
                            ){

                        return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, rootOffset);

                    }
                }
            }
            else{
                //WE CAN'T BUILD
                //TODO: Write in can't build
            }
        }



        //TODO: I want to make a decision and pick an action
        /**
         So we go to each settlement and do a few checks:
         If no Totoros and size >= 5, place a Totoro
         If no Tigers and there is an adjacent level 3, place a Tiger
         Check all possible expansions for each type of adjacent terrain type and pick highest of those
         If none of the above place a single villager adjacent to a settlement of size < 5 if one exists, otherwise just put it anywhere
         **/

        //TODO: We want to make sure we can actually perform the action before we pick it! Aka check Inventory.
        return new BuildAction(id, BuildActionType.FOUND_SETTLEMENT, new Point(-1, 0));
    }
    public List<Settlement> ourSettlementsOnBoard(Board board) {
        List<Settlement> results = new ArrayList<>();

        for (Settlement settlement : board.getSettlementManager().getListOfSettlements()) {
            Point firstOffsetInSettlement = (Point)settlement.getOffsets().toArray()[0];

            Point boardPointForOffset = board.boardPointForOffset(firstOffsetInSettlement);

            if (board.hexagonAtPoint(boardPointForOffset).getOccupiedID() == id) {
                results.add(settlement);
            }
        }

        return results;
    }
}