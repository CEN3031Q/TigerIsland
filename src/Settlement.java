import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hugh on 3/28/17.
 * Contains information about which spots on the board are considered a settlement
 * TODO: Find out what should be the entity should be in charge of settlements
 */

public class Settlement {
    // List of all hexagons in a particular settlement
    private Set<Point> points = new HashSet<>();
    private boolean containsTotoro;
    private boolean containsTiger;

    // Constructor taking in a Point object
    public Settlement(Point newSettlementPoint){
        points.add(newSettlementPoint);
        containsTotoro = false;
        containsTiger = false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int tmp = 0;
        for (Point p : points) {
            tmp = (hash + p.hashCode());
            hash = (tmp << 5) - tmp;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Settlement))
            return false;
        if (obj == this)
            return true;
        Settlement rhs = (Settlement) obj;
        return points.equals(rhs.points) &&
                containsTotoro == rhs.containsTotoro &&
                containsTiger == rhs.containsTiger;
    }

    // Returns an array list of the points in this settlement
    public Set<Point> getPoints(){
        return points;
    }

    // Adds a new point to this settlement
    // Does not check if the point already exists however
    public void addPointToSettlement(Point pointToAdd){
        points.add(pointToAdd);
    }

    // Removes a certain point
    // Goes through each point in the list and checks if the point actually exists first
    public void removePointInSettlement(Point pointToRemove){
        for(Point p : points){
            if(p.equals(pointToRemove)){
                points.remove(pointToRemove);
            }
        }

    }

    // Returns the number of hexagon spaces in this particular settlement
    public int getSettlementSize(){
        return points.size();
    }

    // Checks if a given point is already in this settlement
    public boolean pointExistsInThisSettlement(Point pointToCheck){
        for(Point p : points){
            if(p.equals(pointToCheck)){
                return true;
            }
        }
        return false;
    }

    public void setSettlementContainsTotoro(){
        containsTotoro = true;
    }

    public boolean containsTotoro(){
        return containsTotoro;
    }

    public void setSettlementContainsTiger(){
        containsTiger = true;
    }

    public boolean containsTiger() {
        return containsTiger;
    }

    // Prints a list of (x, y) points in this settlement
    public void printPoints(){
        for(Point p : points){
            System.out.format("(%d, %d)", p.getX(), p.getY());
        }
    }

}
