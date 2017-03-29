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
    public Pieces getMeepleObject(){
        return meepleList.get(5);
    }
    public Pieces getTotoroObject(){
        return totoroList.get(1);
    }
    public Pieces getTigerObject(){
        return tigerList.get(1);
    }
    public void removeMeeplePiece(){
        meepleList.remove(Pieces.MEEPLE);
    }
    public void removeTotoroPiece(){
        totoroList.remove(Pieces.TOTORO);
    }
    public void removeTigerPiece(){
        tigerList.remove(Pieces.TIGER);
    }
    public boolean isMeepleEmpty() {
        if(getMeepleSize() == 0) {
            return true;
        }
        else return false;
    }
    public boolean isTotoroEmpty() {
        if(getTotoroSize() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isTigerEmpty() {
        if(getTigerSize() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isInventoryEmpty() {
        if(getMeepleSize() == 0 && getTotoroSize() == 0 && getTigerSize() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
