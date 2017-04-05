import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Hexagon {
    private TerrainType terrainType;
    private int tileID;
    private int level;
<<<<<<< HEAD
    private int numberOfVillagersOnTop;
    private boolean totoroOnTop;
    private boolean tigerOnTop;
=======
>>>>>>> master
    private boolean occupied;
    private boolean validSpace;
    private Point boardPoint;
    private boolean meepleOnTop;
    private boolean totoroOnTop;
    private boolean tigerOnTop;
    ArrayList<Meeple> meepleList;
    ArrayList<Totoro> totoroList;
    ArrayList<Tiger> tigerList;



    public Hexagon() {
        this.terrainType = TerrainType.EMPTY;
        this.tileID = 0;
        this.level = 0;
<<<<<<< HEAD
        this.numberOfVillagersOnTop = 0;
        this.totoroOnTop = false;
        this.tigerOnTop = false;
=======
>>>>>>> master
        this.occupied = false;
        this.validSpace = false;
        this.meepleOnTop = false;
        this.totoroOnTop = false;
        this.tigerOnTop = false;
        this.meepleList = new ArrayList<>(20);
        this.totoroList = new ArrayList<>(1);
        this.tigerList = new ArrayList<>(1);
    }

    public Hexagon(Hexagon hex) {
        if (hex == null) { return; }
        this.terrainType = hex.terrainType;
        this.tileID = hex.tileID;
        this.level = hex.level;
<<<<<<< HEAD
        this.numberOfVillagersOnTop = hex.numberOfVillagersOnTop;
=======
>>>>>>> master
        this.occupied = hex.occupied;
        this.validSpace = hex.validSpace;
        this.meepleOnTop = hex.meepleOnTop;
        this.totoroOnTop = hex.totoroOnTop;
        this.tigerOnTop = hex.tigerOnTop;
        this.meepleList = hex.meepleList;
        this.totoroList = hex.totoroList;
        this.tigerList = hex.tigerList;
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


    public boolean getMeepleOnTop() {return this.meepleOnTop; }
    public void setMeepleOnTop(boolean meepleOnTop) { this.meepleOnTop = meepleOnTop; }
    public boolean getTotoroOnTop(){ return this.totoroOnTop; }
    public void setTotoroOnTop (boolean totoroOnTop) { this.totoroOnTop = totoroOnTop; }
    public boolean getTigerOnTop(){ return this.tigerOnTop; }
    public void setTigerOnTop (boolean tigerOnTop) { this.tigerOnTop = tigerOnTop; }

    public int getMeepleSize(){
        return meepleList.size();
    }
    public int getTotoroSize(){
        return totoroList.size();
    }
    public int getTigerSize(){
        return tigerList.size();
    }

    public boolean isZeroMeepleOnHex() {
        return getMeepleSize() == 0;
    }
    public boolean isZeroTotoroOnHex() {
        return getTotoroSize() == 0;
    }
    public boolean isZeroTigerOnHex() {
        return getTigerSize() == 0;
    }

    public Piece getPiece() {
        if(getMeepleOnTop() == true && !isZeroMeepleOnHex()) {
            return meepleList.get(0);
        }
        else if(getTotoroOnTop() == true && !isZeroTotoroOnHex()) {
            return totoroList.get(0);
        }
        else if(getTigerOnTop() == true && !isZeroTigerOnHex()) {
            return tigerList.get(0);
        }
        else {
            System.out.println("There is no Piece on this Hex.");
            return null;
        }
    }

    public void addPiece(Piece piece) {
        //TODO: Should Hex class be responsible for enforcing # of Meeple on Level rule?
        if(piece instanceof Meeple && getMeepleSize() < getLevel() && getTerrainType() != TerrainType.VOLCANO) {
            setMeepleOnTop(true);
            meepleList.add((Meeple)piece);
            if(getMeepleSize() == getLevel()) {
                setOccupied(true);
            }
        }
        else if(piece instanceof Totoro && getTotoroSize() < 1 && getTerrainType() != TerrainType.VOLCANO) {
            setTotoroOnTop(true);
            setOccupied(true);
            totoroList.add((Totoro)piece);
        }
        else if(piece instanceof Tiger && getTigerSize() < 1 && getTerrainType() != TerrainType.VOLCANO && getLevel() >= 3) {
            setTigerOnTop(true);
            setOccupied(true);
            tigerList.add((Tiger)piece);
        }
        else {
            System.out.println("Cannot add Piece on Hex.");
        }
    }

    public void removeMeeple(Meeple meeple) {
        if(meeple instanceof Meeple && getMeepleSize() > 0) {
            meepleList.remove(meeple);
            if(getMeepleSize() == 0) {
                setMeepleOnTop(false);
            }
        }
        else {
            System.out.println("Cannot remove Meeple from Hex.");
        }
    }

}