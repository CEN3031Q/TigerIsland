import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Board {
    private ArrayList<ArrayList<Hexagon>> gameBoard = new ArrayList<>();
    private int nextTileID = 1;
    private int minBoardX = 3;
    private int maxBoardX = 397;
    private int minBoardY = 3;
    private int maxBoardY = 397;
    private ArrayList<Settlement> settlementList;

    public Board() {
        for (int ii = 0; ii < 400; ii++) {
            ArrayList<Hexagon> hexagons = new ArrayList<Hexagon>();

            for (int jj = 0; jj < 400; jj++) {
                Hexagon hex = new Hexagon();

                if (ii % 2 == 0 && jj % 2 == 0) {
                    hex.setSpaceAsValid(true);
                } else if (ii % 2 == 1 && jj % 2 == 1) {
                    hex.setSpaceAsValid(true);
                } else {
                    hex.setSpaceAsValid(false);
                }

                hexagons.add(hex);
            }

            gameBoard.add(hexagons);
        }
    }

    // Copy constructor
    public Board(Board aBoard) {
        gameBoard = aBoard.getGameBoardCopy();
        nextTileID = aBoard.nextTileID;
        minBoardX = aBoard.minBoardX;
        maxBoardX = aBoard.maxBoardX;
        minBoardY = aBoard.minBoardY;
        maxBoardY = aBoard.maxBoardY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Board other = (Board)obj;

        return other.gameBoard.equals(gameBoard) &&
                other.gameBoard != gameBoard &&
                other.nextTileID == nextTileID &&
                other.minBoardY == minBoardY &&
                other.maxBoardY == maxBoardY &&
                other.minBoardX == minBoardX &&
                other.maxBoardX == maxBoardX;
    }

    public void placeTile(Tile tileToPlace, Point tileCoordinate) throws IllegalArgumentException {
        HashMap<Point, Boolean> validCoordinates = validPositionsForNewTile(tileToPlace);

        if (validCoordinates.get(tileCoordinate) == null) {
            throw new IllegalArgumentException("{ " + tileCoordinate.getX() + ", " + tileCoordinate.getY() + " } is not a playable spot!");
        }

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tileCoordinate, tileToPlace.getOrientation(), tileToPlace.getAnchorPosition());
        HashMap<HexagonPosition, Point> points = calc.pointsForTerrainHexagons();

        Point leftPoint = points.get(HexagonPosition.LEFT);
        Point midPoint = points.get(HexagonPosition.MIDDLE);
        Point rightPoint = points.get(HexagonPosition.RIGHT);

        TerrainType left = tileToPlace.getTerrainTypeForPosition(HexagonPosition.LEFT);
        TerrainType right = tileToPlace.getTerrainTypeForPosition(HexagonPosition.RIGHT);
        TerrainType middle = tileToPlace.getTerrainTypeForPosition(HexagonPosition.MIDDLE);

        hexagonAtPoint(leftPoint).setTerrainType(left);
        hexagonAtPoint(rightPoint).setTerrainType(right);
        hexagonAtPoint(midPoint).setTerrainType(middle);

        for (Point point : calc.neighborsWithinTile()) {
            Hexagon hex = hexagonAtPoint(point);
            hex.incrementLevel();
            hex.setOccupied(false);
            hex.setTileID(nextTileID);
        }

        nextTileID++;

        //trying to fin the min and max to print from

        //This case is just the first tile case. Checking the min and max is different in only this case I believe
        if(minBoardX == 3 && minBoardY == 3 && maxBoardX == 397 && maxBoardY == 397){
            for (int ii = minBoardX - 3; ii < maxBoardX+3; ii++) {
                for (int jj = minBoardY; jj < maxBoardY; jj++) {
                    if(gameBoard.get(jj).get(ii).getTileID() != 0){
                        if(ii < maxBoardX){
                            maxBoardX = ii;
                        }
                        if(jj < maxBoardY){
                            maxBoardY = jj;
                        }
                        if(ii > minBoardX){
                            minBoardX = ii;
                        }
                        if(jj > minBoardY){
                            minBoardY = jj;
                        }
                    }
                }
            }
        }
        else {
            for (int ii = minBoardX - 3; ii < maxBoardX + 3; ii++) {
                for (int jj = minBoardY-3; jj < maxBoardY+3; jj++) {
                    if (gameBoard.get(jj).get(ii).getTileID() != 0) {
                        if (ii > maxBoardX) {
                            maxBoardX = ii;
                        }
                        if (jj > maxBoardY) {
                            maxBoardY = jj;
                        }
                        if (ii < minBoardX) {
                            minBoardX = ii;
                        }
                        if (jj < minBoardY) {
                            minBoardY = jj;
                        }
                    }
                }
            }
        }
        //making sure we don't go less than 0 or greater than 400
        if(minBoardX >= 3){
            minBoardX = minBoardX - 3;
        }
        if(minBoardY >= 3){
            minBoardY = minBoardX - 3;
        }
        if(maxBoardX <= 397){
            maxBoardX = maxBoardX + 3;
        }
        if(maxBoardY <= 397){
            maxBoardY = maxBoardX + 3;
        }

    }

    public HashMap<Point, Boolean> validPositionsForNewTile(Tile tile) {
        HashMap<Point, Boolean> validPoints = new HashMap<>();

        Board boardCopy = new Board(this);
        FirstTilePlacementRule firstTilePR = new FirstTilePlacementRule(tile, boardCopy);
        TilePlacementRule placementRule = new TilePlacementRule(tile, boardCopy);

        List<TilePlacementRule> rules = Arrays.asList(firstTilePR, placementRule);

        for (int y = 3; y <= 396; y++) {
            for (int x = 3; x <= 396; x++) {
                Point point = new Point(x, y);
                for (TilePlacementRule rule : rules) {
                    if (rule.tileMeetsConditionsForRuleToApply() && rule.pointIsValid(point)) {
                        validPoints.put(point, true);
                    }
                }
            }
        }

        return validPoints;
    }

    public ArrayList<ArrayList<Hexagon>> getGameBoardCopy() {
        return new ArrayList<ArrayList<Hexagon>>(gameBoard);
    }

    private Hexagon hexagonAtPoint(Point p) {
        return gameBoard.get(p.y).get(p.x);
    }

    public Hexagon copyOfHexagonAtPoint(Point p) {
        return new Hexagon(hexagonAtPoint(p));
    }


    public TerrainType getTerrainTypeAtPoint(Point point) {
        return hexagonAtPoint(point).getTerrainType();
    }


    public int getLevelAtPoint(Point point) {
        return hexagonAtPoint(point).getLevel();
    }

    public int getTileIDAtPoint(Point point) {
        return hexagonAtPoint(point).getTileID();
    }

    public int getNextTileID() {
        return nextTileID;
    }

    public Point getPointOfHexagon(Hexagon hexagon){
        for (int ii = minBoardX - 3; ii < maxBoardX + 3; ii++) {
            for (int jj = minBoardY - 3; jj < maxBoardY + 3; jj++) {
                if (gameBoard.get(jj).get(ii) == hexagon) {
                    Point point = new Point(ii, jj);
                    return point;
                }
            }
        }
        Point fakePoint = new Point(0,0);
        return fakePoint;
    }

    public Point getPointOfWhereVolcanoWillBePlaced(Tile tile){
        TileOrientation tileOrientation = tile.getOrientation();
        //if(tileOrientation ==  tileOrientation.BOTTOMHEAVY && )
        //TODO: how do I do this
        return new Point(0, 0);
    }

    public ArrayList<Settlement> updateSettlements() {

        ArrayList<Hexagon> visited = new ArrayList();
        ArrayList<Settlement> listOfSettlements = new ArrayList();
        for (int ii = minBoardX; ii < maxBoardX; ii++) {
            for (int jj = minBoardY; jj < maxBoardY; jj++) {
                if(gameBoard.get(jj).get(ii).getOccupied() && !visited.contains(gameBoard.get(jj).get(ii))) {
                    Settlement newSettlement = new Settlement();
                    Hexagon visitingHexagon = gameBoard.get(jj).get(ii);
                    int visitingHexagonX = ii;
                    int visitingHexagonY = jj;
                    int isEvenOrOdd;
                    if(visitingHexagonY % 2 == 0){
                        isEvenOrOdd = 0;
                    }
                    else{
                        isEvenOrOdd = 1;
                    }


                    Hexagon tempHex = hexagonAtPoint(new Point(ii, jj));
                    int playerID = tempHex.getPiece().getPlayerID();
                    ArrayList<Hexagon> queue = new ArrayList();

                    queue.add(visitingHexagon);
                    while(!queue.isEmpty()) {
                        Hexagon hexUp = gameBoard.get(visitingHexagonY-1).get(visitingHexagonX);
                        Hexagon hexDown = gameBoard.get(visitingHexagonY+1).get(visitingHexagonX);
                        Hexagon hexUpRight = gameBoard.get(visitingHexagonY-1).get(visitingHexagonX+1);
                        Hexagon hexUpLeft = gameBoard.get(visitingHexagonY-1).get(visitingHexagonX-1);
                        Hexagon hexLeft = gameBoard.get(visitingHexagonY).get(visitingHexagonX-1);
                        Hexagon hexRight = gameBoard.get(visitingHexagonY).get(visitingHexagonX+1);
                        Hexagon hexDownLeft = gameBoard.get(visitingHexagonY+1).get(visitingHexagonX-1);
                        Hexagon hexDownRight = gameBoard.get(visitingHexagonY+1).get(visitingHexagonX+1);
                        //put all of its neightbors into the queue
                        //Check if it's even
                        if(isEvenOrOdd == 0) {
                            if(hexUp.getOccupied()){
                                if(!visited.contains(hexUp)) {
                                    if (hexUp.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexUp);
                                    }
                                }
                            }
                            else{
                                visited.add(hexUp);
                            }
                            if(hexUpRight.getOccupied()) {
                                if (!visited.contains(hexUpRight)){
                                    if (hexUpRight.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexUpRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexUpRight);
                            }
                            if(hexLeft.getOccupied()) {
                                if( !visited.contains(hexLeft)) {
                                    if (hexLeft.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexLeft);
                                    }
                                }
                            }
                            else{
                                visited.add(hexLeft);
                            }
                            if(hexRight.getOccupied()) {
                                if(!visited.contains(hexRight)) {
                                    if (hexRight.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexRight);
                            }
                            if(hexDownRight.getOccupied()) {
                                if(!visited.contains(hexDownRight)) {
                                    if (hexDownRight.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexDownRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexDownRight);
                            }
                            if(hexDown.getOccupied()) {
                                if (!visited.contains(hexDown)){
                                    if (hexDown.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexDown);
                                    }
                                }
                            }
                            else{
                                visited.add(hexDown);
                            }
                        }
                        //if it's not even, then it's odd
                        else{
                            if(hexUp.getOccupied()) {
                                if(!visited.contains(hexUp)) {
                                    if (hexUp.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexUp);
                                    }
                                }
                            }
                            else{
                                visited.add(hexUp);
                            }
                            if(hexLeft.getOccupied()) {
                                if (!visited.contains(hexLeft)){
                                    if (hexLeft.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexLeft);
                                    }
                                }
                            }
                            else{
                                visited.add(hexLeft);
                            }
                            if(hexRight.getOccupied()) {
                                if(!visited.contains(hexRight)) {
                                    if (hexRight.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexRight);
                                    }
                                }
                            }
                            else{
                                visited.add(hexRight);
                            }
                            if(hexDown.getOccupied()) {
                                if(!visited.contains(hexDown)) {
                                    if (hexDown.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexDown);
                                    }
                                }
                            }
                            else{
                                visited.add(hexDown);
                            }
                            if(hexDownLeft.getOccupied()) {
                                if(!visited.contains(hexDownLeft)) {
                                    if (hexDownLeft.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexDownLeft);
                                    }
                                }
                            }
                            else{
                                visited.add(hexDownLeft);
                            }
                            if(hexUpLeft.getOccupied()) {
                                if(!visited.contains(hexUpLeft)) {
                                    if (hexUpLeft.getPiece().getPlayerID() == playerID) {
                                        queue.add(hexUpLeft);
                                    }
                                }
                            }
                            else{
                                visited.add(hexUpLeft);
                            }
                        }

                        //What we do when we visit
                        //Add our visitingHexagon to the visited list
                        visited.add(visitingHexagon);
                        //update settlementCoordinates
                        newSettlement.occupiedHexes.add(new Point(visitingHexagonX, visitingHexagonY));
                        //pop visitingHexagon out of our queue
                        queue.remove(0);
                        //update our visitingHexagon to the front of the queue
                        visitingHexagon = queue.get(0);

                        //update visitingHexagonX and visitingHexagonY
                        if(visitingHexagon == hexUp){
                            visitingHexagonY--;
                        }
                        else if(visitingHexagon == hexDown){
                            visitingHexagonY++;
                        }
                        else if(visitingHexagon == hexLeft){
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
                            visitingHexagonX++;
                        }
                        else if(visitingHexagon == hexUpLeft){
                            visitingHexagonY--;
                            visitingHexagonX--;
                        }
                        else if(visitingHexagon == hexUpRight){
                            visitingHexagonY--;
                            visitingHexagonX++;
                        }
                    }
                    listOfSettlements.add(newSettlement);
                }

            }
        }
        settlementList = listOfSettlements;
        return listOfSettlements;
    }

}