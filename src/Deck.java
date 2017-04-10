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
        tilePile[0] = new Tile(TerrainType.LAKE, TerrainType.LAKE);
        tilePile[1]= new Tile(TerrainType.LAKE, TerrainType.LAKE);
        tilePile[2]= new Tile(TerrainType.LAKE, TerrainType.LAKE);

        //LAKE JUNGLE VOLCANO
        tilePile[3]= new Tile(TerrainType.LAKE, TerrainType.JUNGLE);
        tilePile[4]= new Tile(TerrainType.LAKE, TerrainType.JUNGLE);
        tilePile[5]= new Tile(TerrainType.LAKE, TerrainType.JUNGLE);

        //LAKE GRASS VOLCANO
        tilePile[6]= new Tile(TerrainType.LAKE, TerrainType.GRASS);
        tilePile[7]= new Tile(TerrainType.LAKE, TerrainType.GRASS);
        tilePile[8]= new Tile(TerrainType.LAKE, TerrainType.GRASS);

        //LAKE ROCK VOLCANO
        tilePile[9]= new Tile(TerrainType.LAKE, TerrainType.ROCK);
        tilePile[10]= new Tile(TerrainType.LAKE, TerrainType.ROCK);
        tilePile[11]= new Tile(TerrainType.LAKE, TerrainType.ROCK);

        //JUNGLES FIRST
        //JUNGLE LAKE VOLCANO
        tilePile[12]= new Tile(TerrainType.JUNGLE, TerrainType.LAKE);
        tilePile[13]= new Tile(TerrainType.JUNGLE, TerrainType.LAKE);
        tilePile[14]= new Tile(TerrainType.JUNGLE, TerrainType.LAKE);

        //JUNGLE JUNGLE VOLCANO
        tilePile[15]= new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE);
        tilePile[16]= new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE);
        tilePile[17]= new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE);

        //JUNGLE GRASS VOLCANO
        tilePile[18]= new Tile(TerrainType.JUNGLE, TerrainType.GRASS);
        tilePile[19]= new Tile(TerrainType.JUNGLE, TerrainType.GRASS);
        tilePile[20]= new Tile(TerrainType.JUNGLE, TerrainType.GRASS);

        //JUNGLE ROCK VOLCANO
        tilePile[21]= new Tile(TerrainType.JUNGLE, TerrainType.ROCK);
        tilePile[22]= new Tile(TerrainType.JUNGLE, TerrainType.ROCK);
        tilePile[23]= new Tile(TerrainType.JUNGLE, TerrainType.ROCK);

        //GRASS FIRST
        //GRASS LAKE VOLCANO
        tilePile[24]= new Tile(TerrainType.GRASS, TerrainType.LAKE);
        tilePile[25]= new Tile(TerrainType.GRASS, TerrainType.LAKE);
        tilePile[26]= new Tile(TerrainType.GRASS, TerrainType.LAKE);

        //GRASS JUNGLE VOLCANO
        tilePile[27]= new Tile(TerrainType.GRASS, TerrainType.JUNGLE);
        tilePile[28]= new Tile(TerrainType.GRASS, TerrainType.JUNGLE);
        tilePile[29]= new Tile(TerrainType.GRASS, TerrainType.JUNGLE);

        //GRASS GRASS VOLCANO
        tilePile[30]= new Tile(TerrainType.GRASS, TerrainType.GRASS);
        tilePile[31]= new Tile(TerrainType.GRASS, TerrainType.GRASS);
        tilePile[32]= new Tile(TerrainType.GRASS, TerrainType.GRASS);

        //GRASS ROCK VOLCANO
        tilePile[33]= new Tile(TerrainType.GRASS, TerrainType.ROCK);
        tilePile[34]= new Tile(TerrainType.GRASS, TerrainType.ROCK);
        tilePile[35]= new Tile(TerrainType.GRASS, TerrainType.ROCK);

        //ROCK FIRST
        //ROCK LAKE VOLCANO
        tilePile[36]= new Tile(TerrainType.ROCK, TerrainType.LAKE);
        tilePile[37]= new Tile(TerrainType.ROCK, TerrainType.LAKE);
        tilePile[38]= new Tile(TerrainType.ROCK, TerrainType.LAKE);

        //ROCK JUNGLE VOLCANO
        tilePile[39]= new Tile(TerrainType.ROCK, TerrainType.JUNGLE);
        tilePile[40]= new Tile(TerrainType.ROCK, TerrainType.JUNGLE);
        tilePile[41]= new Tile(TerrainType.ROCK, TerrainType.JUNGLE);

        //ROCK GRASS VOLCANO
        tilePile[42]= new Tile(TerrainType.ROCK, TerrainType.GRASS);
        tilePile[43]= new Tile(TerrainType.ROCK, TerrainType.GRASS);
        tilePile[44]= new Tile(TerrainType.ROCK, TerrainType.GRASS);

        //ROCK ROCK VOLCANO
        tilePile[45]= new Tile(TerrainType.ROCK, TerrainType.ROCK);
        tilePile[46]= new Tile(TerrainType.ROCK, TerrainType.ROCK);
        tilePile[47]= new Tile(TerrainType.ROCK, TerrainType.ROCK);

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

