/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Hexagon {
    private TerrainType terrainType;
    private int tileID;
    private int level;

    private String occupiedID = Integer.toString(Integer.MIN_VALUE);

    private boolean totoroOnTop;
    private boolean tigerOnTop;

    public Hexagon() {
        this.terrainType = TerrainType.EMPTY;

        this.tileID = 0;
        this.level = 0;

        this.totoroOnTop = false;
        this.tigerOnTop = false;
    }

    public Hexagon(TerrainType type) {
        this();
        this.terrainType = type;
    }

    public Hexagon(TerrainType type, int tileID, int level) {
        this();
        this.terrainType = type;
        this.tileID = tileID;
        this.level = level;
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

    public void incrementLevel() {
        this.level += 1;
    }

    public int getTileID() {
        return this.tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public boolean isOccupied() {
        return !occupiedID.equals(Integer.toString(Integer.MIN_VALUE));
    }

    public String getOccupiedID() {
        return occupiedID;
    }

    public void setOccupied(String occupiedID) {
        this.occupiedID = occupiedID;
    }

    public boolean isTotoroOnTop() {
        return this.totoroOnTop;
    }

    public void setTotoroOnTop (boolean totoroOnTop) {
        this.totoroOnTop = totoroOnTop;
    }

    public boolean isTigerOnTop() {
        return this.tigerOnTop;
    }

    public void setTigerOnTop (boolean tigerOnTop) {
        this.tigerOnTop = tigerOnTop;
    }
}