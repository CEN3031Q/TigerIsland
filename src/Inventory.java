/**
 * Created by user on 3/27/2017.
 */
import java.util.ArrayList;
public class Inventory {

    private ArrayList<Pieces> meepleList;
    private ArrayList<Pieces> totoroList;
    private ArrayList<Pieces> tigerList;

    public Inventory (){
        meepleList = new ArrayList<>(20);
        totoroList = new ArrayList<>(3);
        tigerList = new ArrayList<>(2);
        initMeepleList();
        initTotoroList();
        initTigerList();
    }

    private void initMeepleList() {
        for (int i = 0; i < 20; i++) {
            meepleList.add(Pieces.MEEPLE);
        }
    }

    private void initTotoroList(){
        for (int i = 0; i < 3; i++){
            totoroList.add(Pieces.TOTORO);
        }
    }

    private void initTigerList(){
        for (int i = 0; i < 2; i++) {
            tigerList.add(Pieces.TIGER);
        }

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
    public Pieces removeMeeplePiece() throws ArrayIndexOutOfBoundsException {
        if(isMeepleEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No more Meeple pieces remaining.");
        }
        return meepleList.remove(0);
    }
    public Pieces removeTotoroPiece() throws ArrayIndexOutOfBoundsException {
        if(isTotoroEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No more Totoro pieces remaining.");
        }
        return totoroList.remove(0);
    }
    public Pieces removeTigerPiece() throws ArrayIndexOutOfBoundsException {
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
        if(isMeepleEmpty() && isTotoroEmpty() && isTigerEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
