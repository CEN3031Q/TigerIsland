import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hugh on 4/2/17.
 */
public class SettlementManager {
    private ArrayList<Settlement> listOfSettlements = new ArrayList<>();
    private Board gameBoard;

    public SettlementManager(Board gameBoard){
        this.gameBoard = gameBoard;
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

    // Given two settlements, it takes the list of points in the second settlement
    // and adds them to the list in the first settlement
    public void mergeSettlement(Settlement settlementToAddTo, Settlement settlementToDelete){
        settlementToAddTo.getPointsInSettlement().addAll(settlementToDelete.getPointsInSettlement());
        listOfSettlements.remove(settlementToDelete);
    }

    // Overloaded method that merges to settlements given two points
    public void mergeSettlement(Point pointInNewSettlement, Point pointInOldSettlememnt){
        // Do nothing if the two points are the same
        if(pointInNewSettlement.equals(pointInOldSettlememnt)){
            return;
        }

        Settlement settlementToAddTo = null;
        Settlement settlementToDelete = null;
        for(Settlement s : listOfSettlements){
            if(s.pointExistsInThisSettlement(pointInNewSettlement)){
                settlementToAddTo = s;
            }
            if(s.pointExistsInThisSettlement(pointInOldSettlememnt)){
                 settlementToDelete = s;
            }
        }
        // If the settlements found in the list are the same, that means the points are in the same settlement
        if(settlementToAddTo == settlementToDelete){
            return;
        }

        // This may throw an error if one of the settlements is not found
        mergeSettlement(settlementToAddTo, settlementToDelete);
    }



}
