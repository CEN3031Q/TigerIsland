import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Game {
    private Board board;
    private ArrayList<Player> players;
    private Deck tileDeck = new Deck();

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

                try {
                    Tile tile = tileDeck.drawTile();
                    Coordinate coord = performTileAction(player, tile);
                    performBuildAction(player, coord);
                } catch (ArrayIndexOutOfBoundsException e) {
                    //TODO: Game over, we've run out of tiles!
                    System.out.println("Game over! No tiles remaining.");
                }
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void eliminatePlayer(Player player) {
        players.remove(player);
    }

    public boolean isGameOver() {
        return players.isEmpty() || tileDeck.isEmpty();
    }

    private Coordinate performTileAction(Player player, Tile tile) {
        Board boardCopy = new Board(board);
        GameActionPerformer actionPerformer = player.getGameActionPerformer();

        Coordinate tileCoordinate = actionPerformer.tileAction(tile, boardCopy);

        //TODO: Check if valid...

        adoptBoard(boardCopy);

        return tileCoordinate;
    }

    private void performBuildAction(Player player, Coordinate lastPlacedCoordinate) {
        Board boardCopy = new Board(board);
        GameActionPerformer actionPerformer = player.getGameActionPerformer();

        BuildAction buildAction = actionPerformer.buildAction(boardCopy);
        buildAction.perform(boardCopy);

        //TODO: Check if valid...

        adoptBoard(boardCopy);
    }

    private void adoptBoard(Board newBoard) {
        board = newBoard;
    }
}