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

    // Constructor taking in a Point object
    public Settlement(Point newSettlementPoint){

    }

    public void addPointToSettlement(Point pointToAdd){
        pointsInSettlement.add(pointToAdd);
    }

    public void removePointToSettle(Point pointToRemove){
            pointsInSettlement.remove(pointToRemove);
    }

    public int getSettlementSize(){
        return pointsInSettlement.size();
    }



}
