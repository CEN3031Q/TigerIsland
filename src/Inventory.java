import java.util.ArrayList;
public class Inventory {
    private String playerID;

    private ArrayList<Meeple> meepleList;
    private ArrayList<Totoro> totoroList;
    private ArrayList<Tiger> tigerList;

    public Inventory (String playerID){
        this.playerID = playerID;

        initMeepleList();
        initTotoroList();
        initTigerList();
    }

    private void initMeepleList() {
        meepleList = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            meepleList.add(new Meeple(playerID));
        }
    }

    private void initTotoroList() {
        totoroList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++){
            totoroList.add(new Totoro(playerID));
        }
    }

    private void initTigerList() {
        tigerList = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            tigerList.add(new Tiger(playerID));
        }

    }
    public String getPlayerID() {
        return playerID;
    }

    public int getMeepleSize(){
       return meepleList.size();
    }

    public int getTotoroSize(){
        return totoroList.size();
    }

    public int getTigerSize(){
        return tigerList.size();
    }

    public Meeple removeMeeplePiece() throws ArrayIndexOutOfBoundsException {
        if(isMeepleEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No more Meeple pieces remaining.");
        }
        return meepleList.remove(0);
    }

    public Totoro removeTotoroPiece() throws ArrayIndexOutOfBoundsException {
        if(isTotoroEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No more Totoro pieces remaining.");
        }
        return totoroList.remove(0);
    }

    public Tiger removeTigerPiece() throws ArrayIndexOutOfBoundsException {
        if(isTigerEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No more Tiger pieces remaining.");
        }
        return tigerList.remove(0);
    }

    public boolean isMeepleEmpty() {
        return meepleList.size() == 0;
    }

    public boolean isTotoroEmpty() {
        return totoroList.size() == 0;
    }

    public boolean isTigerEmpty() {
        return tigerList.size() == 0;
    }

    public boolean isInventoryEmpty() {
        return isMeepleEmpty() && isTotoroEmpty() && isTigerEmpty();
    }
}
