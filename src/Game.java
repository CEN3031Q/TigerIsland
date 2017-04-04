import javafx.geometry.Point3D;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Game {
    private Board board;
    private Deck tileDeck = new Deck();

    public Game(Deck deck) {
        board = new Board();
        tileDeck = deck;
    }

    public Game() {
        board = new Board();
    }

    public Board getBoardCopy() {
        return new Board(board);
    }

    public void applyTileAction(Tile tile, Point3D point) {
        //TODO: convert this to our coordinate system and apply it to our board
    }

    public void applyBuildAction(BuildAction build) {
        //TODO: apply it to our board
    }
}