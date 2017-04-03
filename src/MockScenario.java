import java.awt.*;

/**
 * Created by hugh on 3/29/17.
 * This class helps to quickly create different states for the board to
 * run different tests and scenarios
 * Doesn't need to be used but could help reduce code in the other files
 * TODO: Add more scenarios
 */
public class MockScenario {
    MockScenario(){

    }

    // Returns a tile with the given Terrain Type in left, center, right order
    public Tile getTileWithGivenTerrain(TerrainType leftHexTerrain, TerrainType centerHexTerrain, TerrainType rightHexTerrain){
        Tile mockTile = new Tile(leftHexTerrain, centerHexTerrain, rightHexTerrain);
        return mockTile;
    }

    // Returns a board with a single tile placed in the center
    public Board getBoardWithOneTile(){
        Board gameBoard = new Board();

        Tile testTile = new Tile(TerrainType.GRASSLANDS, TerrainType.LAKE, TerrainType.VOLCANO);
        gameBoard.placeTile(testTile, new Point(200, 200));
        return gameBoard;
    }



}
