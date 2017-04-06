/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Hexagon {
    private TerrainType terrainType;
    private int tileID;
    private int level;

    private Integer occupiedID = Integer.MIN_VALUE;

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
        return occupiedID != Integer.MIN_VALUE;
    }

    public Integer getOccupiedID() {
        return occupiedID;
    }

    public void setOccupied(Integer occupiedID) {
        this.occupiedID = occupiedID;
    }

    public void printTerrain (TerrainType terrain){
        if(terrain == TerrainType.VOLCANO){
            System.out.println("VOLCANO");
        }
        else if(terrain == TerrainType.JUNGLE){
            System.out.println("JUNGLE");
        }
        else if(terrain == TerrainType.LAKE){
            System.out.println("LAKE");
        }
        else if(terrain == TerrainType.ROCKY){
            System.out.println("ROCKY");
        }
        else if(terrain == TerrainType.GRASSLANDS){
            System.out.println("GRASSLANDS");
        }
        else{
            System.out.println("EMPTY");
        }
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