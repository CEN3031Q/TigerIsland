import cucumber.api.java8.Ar;
import cucumber.api.java8.He;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

public class Board {
    private ArrayList<ArrayList<Hexagon>> gameBoard = new ArrayList<>();
    private int nextTileID = 1;
    private int minBoardX = 400;
    private int maxBoardX = 0;
    private int minBoardY = 400;
    private int maxBoardY = 0;

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

    //don't need rule enforcement
    //change the name of setLevel needs to become incrementLevel
    //make it not take inputs. just increment
    //
    public void placeTile(Tile tileToPlace, Point tileCoordinate) throws IllegalArgumentException {
        HashMap<Point, Boolean> validCoordinates = validPositionsForNewTile(tileToPlace);

        if (validCoordinates.get(tileCoordinate) == null) {
            throw new IllegalArgumentException("{ " + tileCoordinate.getX() + ", " + tileCoordinate.getY() + " } is not a playable spot!");
        }

        int xCoordinate = (int)tileCoordinate.getX();
        int yCoordinate = (int)tileCoordinate.getY();

        //this is where it changes all of the values
        //change the bounds if i loop based off of the anchor
        //or don't even loop, just get the left and right locations and change the values
        for (int ii = yCoordinate; ii < yCoordinate+2; ii++) {
            for (int jj = xCoordinate; jj < xCoordinate+3; jj++) {
                Hexagon hex = gameBoard.get(ii).get(jj);
                hex.incrementLevel();
                hex.setTileID(nextTileID);
                hex.setOccupied(false);
            }
        }
        this.nextTileID++;

        HexagonNeighborsCalculator calc = new HexagonNeighborsCalculator(tileCoordinate,
                                                                        tileToPlace.getOrientation(),
                                                                        tileToPlace.getAnchorPosition());

        HashMap<HexagonPosition, Point> points = calc.pointsForTerrainHexagons();

        Point leftPoint = points.get(HexagonPosition.LEFT);
        Point midPoint = points.get(HexagonPosition.MIDDLE);
        Point rightPoint = points.get(HexagonPosition.RIGHT);

        TerrainType leftTerrain = tileToPlace.getTerrainTypeForPosition(HexagonPosition.LEFT);
        TerrainType rightTerrain = tileToPlace.getTerrainTypeForPosition(HexagonPosition.RIGHT);
        TerrainType middleTerrain = tileToPlace.getTerrainTypeForPosition(HexagonPosition.MIDDLE);

        gameBoard.get(leftPoint.y).get(leftPoint.x).setTerrainType(leftTerrain);
        gameBoard.get(midPoint.y).get(midPoint.x).setTerrainType(middleTerrain);
        gameBoard.get(rightPoint.y).get(rightPoint.x).setTerrainType(rightTerrain);

        // Trying to find the min and max to print from

        for (int ii = 0; ii < 400; ii++) {
            for (int jj = 0; jj < 400; jj++) {
                if(gameBoard.get(ii).get(jj).getTileID() != 0){
                    if(ii > maxBoardX){
                        maxBoardX = ii;
                    }
                    if(jj > maxBoardY){
                        maxBoardY = jj;
                    }
                    if(ii < minBoardX){
                        minBoardX = ii;
                    }
                    if(jj < minBoardY){
                        minBoardY = jj;
                    }
                }
            }
        }
        
        // Making sure we don't go less than 0 or greater than 400
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

    public Hexagon copyOfHexagonAtPoint(Point p) {
        return new Hexagon(gameBoard.get(p.y).get(p.x));
    }

    public TerrainType getTerrainTypeAtPosition(int xPosition, int yPosition) {
        return gameBoard.get(yPosition).get(xPosition).getTerrainType();
    }

    public int getLevelAtPosition(int xPosition, int yPosition) {
        return gameBoard.get(yPosition).get(xPosition).getLevel();
    }

    public int getTileIDAtPosition(int xPosition, int yPosition) {
        return gameBoard.get(yPosition).get(xPosition).getTileID();
    }

    public int getVillagerNumberAtPosition(int xPosition, int yPosition) {
        return gameBoard.get(yPosition).get(xPosition).getNumVillagersOnTop();
    }

    public void setVillagersAtPosition(int numOfVillagers, int xPosition, int yPosition){
        gameBoard.get(yPosition).get(xPosition).setVillagersOnTop(numOfVillagers);
    }

    public int getNextTileID() {
        return nextTileID;
    }
}