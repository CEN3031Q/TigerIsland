import java.util.ArrayList;

/**
 * Created by hugh on 3/28/17.
 * Contains information about which spots on the board are considered a settlement
 * TODO: Find out what should be the entity should be in charge of settlements
 */


public class Settlement {
    private ArrayList<Coordinate> coordinatesInSettlement = new ArrayList<>();

    // Constructor takin in a Coordinate object
    public Settlement(Coordinate newSettlementCoordinate){
        coordinatesInSettlement.add(newSettlementCoordinate);
    }

    // An overloaded constructor if we want to use x, y int values
    public Settlement(int xPosition, int yPosition){
        coordinatesInSettlement.add(new Coordinate(xPosition, yPosition));
    }

 


}
