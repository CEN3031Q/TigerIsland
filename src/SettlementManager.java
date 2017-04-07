/**
 * Created by hugh on 4/2/17.
 * This class is responsible for managing all the settlements on the board
 * It keeps a list of settlements - which are a list of Points
 */

import java.util.ArrayList;
import java.awt.Point;
import java.util.Set;
import java.util.HashSet;

public class SettlementManager {
    private Set<Settlement> listOfSettlements = new HashSet<>();

    public SettlementManager() {

    }

    /* TODO: BFS STUFF
    public int calculateSettlementSizeAtPoint(Point pointToCheck){
        TerrainType pointTerrainType = gameBoard.getTerrainTypeAtPoint(pointToCheck);

        Queue<Point> adjacentSpaces = new LinkedList<Point>();
        ArrayList<Point> visitedSpaces = new ArrayList<Point>();

        adjacentSpaces.add(pointToCheck);
        visitedSpaces.add(pointToCheck);

        while(!adjacentSpaces.isEmpty()){

        }
    }
    */

    public void addNewSettlement(Settlement settlementToAdd){
        listOfSettlements.add(settlementToAdd);
    }

    // Given a point, runs through every settlement and checks if the point is in a settlement
    // and returns that size
    public int calculateSettlementSizeAtPoint(Point pointToCheck){
        for(Settlement s : listOfSettlements){
            if (s.pointExistsInThisSettlement(pointToCheck)){
                return s.getSettlementSize();
            }
        }
        return 0;
    }

    // Given a point, checks the list of settlements and checks every settlement list of points
    // for the point specified. Otherwise returns null.
    public Settlement getSettlementFromPoint(Point pointTocheck){
        for(Settlement s : listOfSettlements){
            if(s.pointExistsInThisSettlement(pointTocheck)){
                return s;
            }
        }
        return null;
    }

    // Given any number of settlements, this method will add to the first
    // settlement's list of points all the other settlement's points
    public void mergeSettlement(Settlement ... settlementsToMerge){
        // Do nothing if there is only one settlement given
        if(settlementsToMerge.length == 1){
            return;
        }

        // Loop through the remaining settlements, add their points to
        // the first list and then remove them from the total list of settlements
        Settlement baseSettlement = settlementsToMerge[0];
        for(int i = 1; i < settlementsToMerge.length; i++){
            baseSettlement.getPoints().addAll(settlementsToMerge[i].getPoints());
            listOfSettlements.remove(settlementsToMerge[i]);
        }
    }

    // Overloaded method that merges settlements given a list of points
    public void mergeSettlement(Point ... pointsToMerge){
        // Do nothing if there is only one point given
        if(pointsToMerge.length == 1){
            return;
        }

        // This creates the set of all settlements that were found given
        // the points we want to merge
        Set<Settlement> correspondingSettlements = new HashSet<Settlement>();
        for(Point p : pointsToMerge){
            correspondingSettlements.add(getSettlementFromPoint(p));
        }

        // If all the points were in the same settlement, then there is no need to merge
        if(correspondingSettlements.size() <= 1){
            return;
        }

        // Loop through the remaining settlements, add their points to
        // the first list and then remove them from the total list of settlements
        Settlement baseSettlement = getSettlementFromPoint(pointsToMerge[0]);
        correspondingSettlements.remove(baseSettlement);
        for(Settlement s : correspondingSettlements){
            baseSettlement.getPoints().addAll(s.getPoints());
            listOfSettlements.remove(s);
        }
    }

    public void updateSettlements(Board board) {
        ArrayList<Hexagon> visited = new ArrayList<>();
        Set<Settlement> updatedListOfSettlements = new HashSet<>();

        int minBoardX = board.getMinX();
        int maxBoardX = board.getMaxX();
        int minBoardY = board.getMinY();
        int maxBoardY = board.getMaxY();

        for (int ii = minBoardX; ii < maxBoardX; ii++) {
            for (int jj = minBoardY; jj < maxBoardY; jj++) {
                if(board.hexagonAtPoint(new Point(ii,jj)).isOccupied() && !visited.contains(board.hexagonAtPoint(new Point(ii,jj)))) {
                    Settlement newSettlement = new Settlement(new Point(jj, ii));
                    Hexagon visitingHexagon = board.hexagonAtPoint(new Point(ii,jj));
                    int visitingHexagonX = ii;
                    int visitingHexagonY = jj;



                    Integer playerID = board.hexagonAtPoint(new Point(ii,jj)).getOccupiedID();
                    ArrayList<Hexagon> queue = new ArrayList<>();

                    queue.add(visitingHexagon);
                    while(!queue.isEmpty()) {
                        Hexagon hexUpRight = board.hexagonAtPoint(new Point((visitingHexagonY+1),(visitingHexagonX-1)));
                        Hexagon hexUpLeft = board.hexagonAtPoint(new Point((visitingHexagonY),(visitingHexagonX-1)));
                        Hexagon hexLeft = board.hexagonAtPoint(new Point ((visitingHexagonY),(visitingHexagonX-1)));
                        Hexagon hexRight = board.hexagonAtPoint(new Point ((visitingHexagonY),(visitingHexagonX+1)));
                        Hexagon hexDownLeft = board.hexagonAtPoint(new Point ((visitingHexagonY+1),(visitingHexagonX-1)));
                        Hexagon hexDownRight = board.hexagonAtPoint(new Point ((visitingHexagonY+1),(visitingHexagonX+1)));
                        //put all of its neighbors into the queue
                            if(hexUpRight.isOccupied()) {
                                if (!visited.contains(hexUpRight)){
                                    if (hexUpRight.getOccupiedID() == playerID) {
                                        queue.add(hexUpRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexUpRight);
                            }
                            if(hexLeft.isOccupied()) {
                                if( !visited.contains(hexLeft)) {
                                    if (hexLeft.getOccupiedID()== playerID) {
                                        queue.add(hexLeft);
                                    }
                                }
                            }
                            else{
                                visited.add(hexLeft);
                            }
                            if(hexRight.isOccupied()) {
                                if(!visited.contains(hexRight)) {
                                    if (hexRight.getOccupiedID() == playerID) {
                                        queue.add(hexRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexRight);
                            }
                            if(hexDownRight.isOccupied()) {
                                if(!visited.contains(hexDownRight)) {
                                    if (hexDownRight.getOccupiedID() == playerID) {
                                        queue.add(hexDownRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexDownRight);
                            }
                            if(hexUpRight.isOccupied()) {
                                if (!visited.contains(hexUpRight)){
                                    if (hexDownLeft.getOccupiedID() == playerID) {
                                        queue.add(hexUpRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexUpRight);
                            }

                        //What we do when we visit
                        //Add our visitingHexagon to the visited list
                        visited.add(visitingHexagon);
                        //update settlementCoordinates
                        newSettlement.getPoints().add(new Point(visitingHexagonX, visitingHexagonY));
                        //pop visitingHexagon out of our queue
                        queue.remove(0);
                        //update our visitingHexagon to the front of the queue
                        visitingHexagon = queue.get(0);

                        //update visitingHexagonX and visitingHexagonY
                        if(visitingHexagon == hexLeft){
                            visitingHexagonX--;
                        }
                        else if(visitingHexagon == hexRight){
                            visitingHexagonX++;
                        }
                        else if(visitingHexagon == hexDownLeft){
                            visitingHexagonY++;
                            visitingHexagonX--;
                        }
                        else if(visitingHexagon == hexDownRight){
                            visitingHexagonY++;
                        }
                        else if(visitingHexagon == hexUpLeft){
                            visitingHexagonY--;
                        }
                        else if(visitingHexagon == hexUpRight){
                            visitingHexagonY--;
                            visitingHexagonX++;
                        }
                    }
                    updatedListOfSettlements.add(newSettlement);
                }

            }
        }
        this.listOfSettlements = updatedListOfSettlements;

    }

    public Set<Settlement> getListOfSettlements(){
        return listOfSettlements;
    }

}
