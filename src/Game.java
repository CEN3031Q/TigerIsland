import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Game {
    private Board board;
    private ArrayList<Player> players;
    Deck tileDeck = new Deck();

    public Game(Player... playersArray) {
        players = new ArrayList<Player>(Arrays.asList(playersArray));
        board = new Board();
    }

    //TODO: Let's make this method a little smaller!
    public void runGameLoop() {
        while (!isGameOver()) {
            for (Player player : players) {
                int idx = players.indexOf(player) + 1;
                System.out.println("* Player " + idx + "'s turn:");

                GameActionPerformer actionPerformer = player.getGameActionPerformer();

                Board currentBoard = board; // Do we need a copy here? I don't want to pass a modifiable reference of our Board...
                Tile tile = tileDeck.gameDeck.get(0);//new Tile(TerrainType.JUNGLE, TerrainType.GRASSLANDS, TerrainType.VOLCANO); //TODO: draw tile instead
                tileDeck.gameDeck.remove(0);

                //TODO: Confirm that requested tile placement is valid
                Coordinate tileCoordinate = actionPerformer.tileAction(tile, currentBoard);

                //TODO: If valid, place tile at this coordinate! Modify the board!

                //TODO: Confirm that the desired build move is valid
                currentBoard = board; // meh
                BuildAction buildAction = actionPerformer.buildAction(currentBoard);

                //TODO: Is this all we need to perform the action? Does this modify the board?
                buildAction.perform(board);
            }
        }
    }

    private boolean isGameOver() {
        return players.isEmpty();
    }

    // Should this be public? Right now it's public for testing purposes...
    public void eliminatePlayer(Player player) {
        players.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}