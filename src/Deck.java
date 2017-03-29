import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Created by patrickwert on 3/20/17.
 */
public class Deck {
    private ArrayList<Tile> gameDeck;

    public Deck(){
        Tile[] tilePile = new Tile [48];

        //LAKES FIRST:
        //LAKE LAKE VOLCANO
        tilePile[0] = new Tile(TerrainType.LAKE, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[1]= new Tile(TerrainType.LAKE, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[2]= new Tile(TerrainType.LAKE, TerrainType.LAKE, TerrainType.VOLCANO);

        //LAKE JUNGLE VOLCANO
        tilePile[3]= new Tile(TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[4]= new Tile(TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[5]= new Tile(TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.VOLCANO);

        //LAKE GRASSLANDS VOLCANO
        tilePile[6]= new Tile(TerrainType.LAKE, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[7]= new Tile(TerrainType.LAKE, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[8]= new Tile(TerrainType.LAKE, TerrainType.GRASSLANDS, TerrainType.VOLCANO);

        //LAKE ROCKY VOLCANO
        tilePile[9]= new Tile(TerrainType.LAKE, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[10]= new Tile(TerrainType.LAKE, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[11]= new Tile(TerrainType.LAKE, TerrainType.ROCKY, TerrainType.VOLCANO);

        //JUNGLES FIRST
        //JUNGLE LAKE VOLCANO
        tilePile[12]= new Tile(TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[13]= new Tile(TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[14]= new Tile(TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.VOLCANO);

        //JUNGLE JUNGLE VOLCANO
        tilePile[15]= new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[16]= new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[17]= new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.VOLCANO);

        //JUNGLE GRASSLANDS VOLCANO
        tilePile[18]= new Tile(TerrainType.JUNGLE, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[19]= new Tile(TerrainType.JUNGLE, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[20]= new Tile(TerrainType.JUNGLE, TerrainType.GRASSLANDS, TerrainType.VOLCANO);

        //JUNGLE ROCKY VOLCANO
        tilePile[21]= new Tile(TerrainType.JUNGLE, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[22]= new Tile(TerrainType.JUNGLE, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[23]= new Tile(TerrainType.JUNGLE, TerrainType.ROCKY, TerrainType.VOLCANO);

        //GRASSLANDS FIRST
        //GRASSLANDS LAKE VOLCANO
        tilePile[24]= new Tile(TerrainType.GRASSLANDS, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[25]= new Tile(TerrainType.GRASSLANDS, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[26]= new Tile(TerrainType.GRASSLANDS, TerrainType.LAKE, TerrainType.VOLCANO);

        //GRASSLANDS JUNGLE VOLCANO
        tilePile[27]= new Tile(TerrainType.GRASSLANDS, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[28]= new Tile(TerrainType.GRASSLANDS, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[29]= new Tile(TerrainType.GRASSLANDS, TerrainType.JUNGLE, TerrainType.VOLCANO);

        //GRASSLANDS GRASSLANDS VOLCANO
        tilePile[30]= new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[31]= new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[32]= new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS, TerrainType.VOLCANO);

        //GRASSLANDS ROCKY VOLCANO
        tilePile[33]= new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[34]= new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[35]= new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY, TerrainType.VOLCANO);

        //ROCKY FIRST
        //ROCKY LAKE VOLCANO
        tilePile[36]= new Tile(TerrainType.ROCKY, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[37]= new Tile(TerrainType.ROCKY, TerrainType.LAKE, TerrainType.VOLCANO);
        tilePile[38]= new Tile(TerrainType.ROCKY, TerrainType.LAKE, TerrainType.VOLCANO);

        //ROCKY JUNGLE VOLCANO
        tilePile[39]= new Tile(TerrainType.ROCKY, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[40]= new Tile(TerrainType.ROCKY, TerrainType.JUNGLE, TerrainType.VOLCANO);
        tilePile[41]= new Tile(TerrainType.ROCKY, TerrainType.JUNGLE, TerrainType.VOLCANO);

        //ROCKY GRASSLANDS VOLCANO
        tilePile[42]= new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[43]= new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS, TerrainType.VOLCANO);
        tilePile[44]= new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS, TerrainType.VOLCANO);

        //ROCKY ROCKY VOLCANO
        tilePile[45]= new Tile(TerrainType.ROCKY, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[46]= new Tile(TerrainType.ROCKY, TerrainType.ROCKY, TerrainType.VOLCANO);
        tilePile[47]= new Tile(TerrainType.ROCKY, TerrainType.ROCKY, TerrainType.VOLCANO);

        //Randomize tilePile and instantiate gameDeck
        tilePile = randomizeTilePile(tilePile);
        gameDeck = new ArrayList<Tile>(Arrays.asList(tilePile));
    }

    public Tile drawTile() throws ArrayIndexOutOfBoundsException {
        if (gameDeck.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No more tiles remaining in the Deck");
        }
        return gameDeck.remove(0);
    }

    public int size() {
        return gameDeck.size();
    }

    public boolean isEmpty() {
        return gameDeck.isEmpty();
    }

    public void printDeck(){
        for(int i = 0; i < 48; i++) {
            System.out.println("TILE " + (i+1) + "!!!");
            if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.MIDDLE) == TerrainType.LAKE){
                System.out.println("TOP is LAKE");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.MIDDLE) == TerrainType.JUNGLE){
                System.out.println("TOP is JUNGLE");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.MIDDLE) == TerrainType.ROCKY){
                System.out.println("TOP is ROCKY");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.MIDDLE) == TerrainType.VOLCANO){
                System.out.println("TOP is VOLCANO");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.MIDDLE) == TerrainType.GRASSLANDS) {
                System.out.println("TOP is GRASSLANDS");
            }
            if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.LEFT) == TerrainType.LAKE){
                System.out.println("LEFT is LAKE");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.LEFT) == TerrainType.JUNGLE){
                System.out.println("LEFT is JUNGLE");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.LEFT) == TerrainType.ROCKY){
                System.out.println("LEFT is ROCKY");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.LEFT) == TerrainType.VOLCANO){
                System.out.println("LEFT is VOLCANO");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.LEFT) == TerrainType.GRASSLANDS) {
                System.out.println("LEFT is GRASSLANDS");
            }
            if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.RIGHT) == TerrainType.LAKE){
                System.out.println("RIGHT is LAKE");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.RIGHT) == TerrainType.JUNGLE){
                System.out.println("RIGHT is JUNGLE");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.RIGHT) == TerrainType.ROCKY){
                System.out.println("RIGHT is ROCKY");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.RIGHT) == TerrainType.VOLCANO){
                System.out.println("RIGHT is VOLCANO");
            }
            else if(gameDeck.get(i).getTerrainTypeForPosition(HexagonPosition.RIGHT) == TerrainType.GRASSLANDS) {
                System.out.println("RIGHT is GRASSLANDS");
            }
        }
    }

    private Tile[] randomizeTilePile(Tile[] tiles){
        Random randomNumberGenerator = new Random();
        for (int i = 0; i<48; i++){
            int randomPosition = randomNumberGenerator.nextInt(48);
            Tile temp = tiles[i];
            tiles[i] = tiles[randomPosition];
            tiles[randomPosition] = temp;
        }
        return tiles;
    }

}

