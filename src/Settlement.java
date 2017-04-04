import java.awt.*;
import java.util.ArrayList;

/**
 * Created by hugh on 3/28/17.
 * Contains information about which spots on the board are considered a settlement
 * TODO: Find out what should be the entity should be in charge of settlements
 */

public class Settlement {
    // List of all hexagons in a particular settlement
    private ArrayList<Point> pointsInSettlement = new ArrayList<>();
    private boolean containsTotoro;
    private boolean containsTiger;

    // Constructor taking in a Point object
    public Settlement(Point newSettlementPoint){
        pointsInSettlement.add(newSettlementPoint);
        containsTotoro = false;
        containsTiger = false;
    }

    // Returns an array list of the points in this settlement
    public ArrayList<Point> getPointsInSettlement(){
        return pointsInSettlement;
    }

    // Adds a new point to this settlement
    // Does not check if the point already exists however
    public void addPointToSettlement(Point pointToAdd){
        pointsInSettlement.add(pointToAdd);
    }

    // Removes a certain point
    // Goes through each point in the list and checks if the point actually exists first
    public void removePointToSettle(Point pointToRemove){
        for(Point p : pointsInSettlement){
            if(p.equals(pointToRemove)){
                pointsInSettlement.remove(pointToRemove);
            }
        }

    }

    // Returns the number of hexagon spaces in this particular settlement
    public int getSettlementSize(){
        return pointsInSettlement.size();
    }

    // Checks if a given point is already in this settlement
    public boolean pointExistsInThisSettlement(Point pointToCheck){
        for(Point p : pointsInSettlement){
            if(p.equals(pointToCheck)){
                return true;
            }
        }
        return false;
    }

    public boolean containsTotoro(){
        return containsTotoro;
    }

    public boolean containsTiger(){
        return containsTiger;
    }
}
