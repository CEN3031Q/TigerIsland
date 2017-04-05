import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Created by gonzalonunez on 3/28/17.
 */
public class TilePlacementRule {
    protected final Tile tile;
    protected final Board board;

    public TilePlacementRule(Tile tile, Board board) {
        this.tile = tile;
        this.board = board;
    }

    public boolean tileMeetsConditionsForRuleToApply() {
        return true;
    };

    public boolean pointIsValid(Point point) {
        TileOrientation orientation = tile.getOrientation();
        HexagonPosition position = tile.getAnchorPosition();

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(point, orientation, position);

        List<Hexagon> hexagonsWithinTile = calc.neighborsWithinTile()
                .stream()
                .map( neighborPoint -> board.copyOfHexagonAtPoint(neighborPoint))
                .filter( hex -> hex.getTileID() != 0)
                .collect(toList());

        List<Hexagon> hexagonsAroundTile = calc.neighborsAroundTile()
                .stream()
                .map( neighborPoint -> board.copyOfHexagonAtPoint(neighborPoint))
                .filter( hex -> hex.getTileID() != 0)
                .collect(toList());

        //TILE STACKING BELOW

        int count = 0;
        int firstHexTileID = 0;
        int secondHexTileID = 0;
        int thirdHexTileID = 0;
        int firstHexLevel = 0;
        int secondHexLevel = 0;
        int thirdHexLevel = 0;
        Hexagon firstHexagon;
        Hexagon secondHexagon;
        Hexagon thirdHexagon;
        ArrayList<Hexagon> hexagonsBeneath = new ArrayList<Hexagon>();
        ArrayList<Hexagon> volcanoHexesBeneath = new ArrayList<Hexagon>();
        Hexagon hexWithVolcanoOnIt = null;
        Point boardVolcanoHex;
        int numberOfMeepleHexes = 0;

        for (int i = 0; i < hexagonsWithinTile.size(); i++) {
            if (hexagonsWithinTile.get(i).getTileID() != 0) {
                    count++;
                if (count == 1) {
                    firstHexagon = hexagonsWithinTile.get(i);
                    hexagonsBeneath.add(firstHexagon);
                    firstHexTileID = hexagonsWithinTile.get(i).getTileID();
                    firstHexLevel = hexagonsWithinTile.get(i).getLevel();

                }
                if (count == 2) {
                    secondHexagon = hexagonsWithinTile.get(i);
                    hexagonsBeneath.add(secondHexagon);
                    secondHexTileID = hexagonsWithinTile.get(i).getTileID();
                    secondHexLevel = hexagonsWithinTile.get(i).getLevel();
                }
                if (count == 3) {
                    thirdHexagon = hexagonsWithinTile.get(i);
                    hexagonsBeneath.add(thirdHexagon);
                    thirdHexTileID = hexagonsWithinTile.get(i).getTileID();
                    thirdHexLevel = hexagonsWithinTile.get(i).getLevel();
                }
            }
            if (hexagonsWithinTile.get(i).getTerrainType() == TerrainType.VOLCANO) {
                volcanoHexesBeneath.add(hexagonsWithinTile.get(i));
            }
            if(hexagonsWithinTile.get(i).getOccupied()){
                numberOfMeepleHexes++;
            }
            //if(hexagonsWithinTile.get(i)/*.getOccupiedByTotoro*/ == true){
            //  return false;
            //}
        }
        if(count != 3){
            //there weren't 3 hexes there somehow
            return false;
        }
        count = 0;

        //if all of the hexes beneath this are filled, run the tile stacking rule checks
        if(firstHexTileID != 0 && secondHexTileID != 0 && thirdHexTileID != 0) {

            ArrayList<Hexagon> tempHexagonsBeneath = hexagonsBeneath;



            //Checking if it directly overlaps another tile
            if (firstHexTileID == secondHexTileID && secondHexTileID == thirdHexTileID) {
                //Then this rules check passes
            } else {
                //your tile directly overlaps only one single tile. the point is invalid
                return false;
            }



            //Checking that you are nuking only one level of spaces
            if(firstHexLevel == secondHexLevel && secondHexLevel == thirdHexLevel){
                //Then this rules check passes
            }
            else{
                //your tile is trying to nuke spots of different heights. You can't have that
                return false;
            }


            //Checking if you are nuking something with a settlement issue
            for (int i = 0; i<hexagonsBeneath.size();i++){
                if(hexagonsBeneath.get(i).getOccupied()){
                    //we found an occupied settlement
                    ArrayList<Point> occupiedHexPoints = new ArrayList<Point>();
                    ArrayList<Hexagon> occupiedHexes = new ArrayList<Hexagon>();
                    occupiedHexes.add(hexagonsBeneath.get(i));
                    //occupiedHexPoints.add(gameBoard.getPointOfHex(hexagonsBeneath(i)));
                    hexagonsBeneath.remove(i);

                    //Now checking for a second occupied Hex
                    for(int j = 0; j<hexagonsBeneath.size();j++){
                        if(hexagonsBeneath.get(j).getOccupied()){
                            //you have 2 occupied spaces
                            //occupiedHexPoints.add(gameBoard.getPointOfHex(hexagonsBeneath(j)));
                            occupiedHexes.add(hexagonsBeneath.get(j));
                            hexagonsBeneath.remove(j);

                            //Now checking if the two occupied spaces belong to the same player
                            //if(occupiedHexes.get(0).getPiece().getPlayerIDOfPiece() == occupiedHexes.get(0).getPiece().getPlayerIDOfPiece()) {
                                //the 2 occupied spaces are occupied by the same player
                                //check if the settlement is size 2
                                //need a method getSettlementAtPoint()
                                //if (getSettlementAtPoint(occupiedHexPoints.get(0)).size() == 2){
                                //then, you can't wipe out a settlement of size 2
                                //return false;
                                //}
                            //}
                            //else{
                              //the 2 occupied spaces are occupied by different players
                                //check if first occupied space settlement is of size 1
                                //if(getSettlementAtPoint(occupiedHexPoints.get(0)).size() == 1){
                                //then you can't wipe out the first settlement which is of size 1
                                //return false;
                                //}
                                //check if second occupied space settlement is of size 1
                                //if(getSettlementAtPoint(occupiedHexPoints.get(1)).size() == 1){
                                //then you can't wipe out the second settlement which is of size 1
                                //return false;
                                //}

                            //}
                        }
                    }
                    if(hexagonsBeneath.size() == 2){
                        //we have exactly 1 occupied settlement
                        //check if the occupied space settlement is of size 1
                        //if(getSettlementAtPoint(occupiedHexPoints.get(0)).size() == 1){
                        //then you can't wipe out a settlement which is of size 1!
                        //return false;
                        //}

                    }
                }
                //else none of the spaces  you are potentially nuking are occupied, so you're good
            }
            //if(firstHexagon.getTotoroOnTop() || secondHexagon.getTotoroOnTop() || thirdHexagon.getTotoroOnTop){
                //You can't nuke a totoro!
                //return false;
            //}
            //if(firstHexagon.getTigerOnTop() || secondHexagon.getTigerOnTop() || thirdHexagon.getTigerOnTop){
                //You can't nuke a tiger!
                //return false;
            //}


            //TODO: need getTotoroOnTop() and need getTigerOnTop()
            //TODO: need getPiece() and getPlayerID
            //TODO: need Settlement getSettlementAtPoint(Point point)


            //What I am about to implement right now:
            //TODO: need a public Point getPointOfHex(Hexagon hexagon)    can be done in board.java
            //TODO: need a public Point getPointOfWhereVolcanoWillBePlaced(orientation tileOrientation, position anchor, tile tile)

        }

        //END OF TILE STACKING RULES


        if (!hexagonsWithinTile.isEmpty()) {
            return false;
            //Stick stacking logic in here
        }

        return !hexagonsAroundTile.isEmpty();
    }
}