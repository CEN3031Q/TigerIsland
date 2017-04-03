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

}
