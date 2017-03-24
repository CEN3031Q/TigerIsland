/**
 * Created by gonzalonunez on 3/16/17.
 */
public class Hexagon {
    private TerrainType terrainType;
    private int tileID;
    private int level;
    private int numVillagersOnTop;
    private boolean occupied;
    private boolean validSpace;

    public Hexagon() {
        this.terrainType = TerrainType.EMPTY;
        this.tileID = 0;
        this.level = 0;
        this.numVillagersOnTop = 0;
        this.occupied = false;
        this.validSpace = false;
    }

    public Hexagon(TerrainType type) {
        this.terrainType = type;
    }

    public TerrainType getTerrainType() {
        return this.terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level += level;
    }

    public boolean getSpaceIsValid() {
        return this.validSpace;
    }

    public void setSpaceAsValid(boolean valid) {
        this.validSpace = valid;
    }

    public int getTileID() {
        return this.tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public boolean getOccupied() {
        return this.occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setVillagersOnTop(int numOfVillagers){ numVillagersOnTop = numOfVillagers; }

    public int getNumVillagersOnTop() { return this.numVillagersOnTop; }
}