/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Hexagon {
    private TerrainType terrainType;
    private int tileID;
    private int level;
    private int numberOfVillagersOnTop;
    private boolean totoroOnTop;
    private boolean tigerOnTop;
    private boolean occupied;
    private boolean validSpace;


    public Hexagon() {
        this.terrainType = TerrainType.EMPTY;
        this.tileID = 0;
        this.level = 0;
        this.numberOfVillagersOnTop = 0;
        this.totoroOnTop = false;
        this.tigerOnTop = false;
        this.occupied = false;
        this.validSpace = false;
    }

    public Hexagon(Hexagon hex) {
        if (hex == null) { return; }
        this.terrainType = hex.terrainType;
        this.tileID = hex.tileID;
        this.level = hex.level;
        this.numberOfVillagersOnTop = hex.numberOfVillagersOnTop;
        this.occupied = hex.occupied;
        this.validSpace = hex.validSpace;
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

    public void incrementLevel() {
        this.level += 1;
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

    public boolean getOccupied() { return this.occupied; }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getNumberOfVillagersOnTop() { return this.numberOfVillagersOnTop; }

    public void setVillagersOnTop(int numberOfVillagers){ this.numberOfVillagersOnTop = numberOfVillagers; }

    public boolean getTotoroOnTop(){ return this.totoroOnTop; }

    public void setTotoroOnTop (boolean totoroOnTop) { this.totoroOnTop = totoroOnTop; }

    public boolean getTigerOnTop(){ return this.tigerOnTop; }


    public int getNumVillagersOnTop() { return this.numberOfVillagersOnTop; }

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

    public void setTigerOnTop (boolean tigerOnTop) { this.tigerOnTop = tigerOnTop; }

}