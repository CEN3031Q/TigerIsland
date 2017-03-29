import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Game {
    private Board board;
    private ArrayList<Player> players;
    private Deck tileDeck = new Deck();

    public Game(Deck deck, Player... playersArray) {
        players = new ArrayList<Player>(Arrays.asList(playersArray));
        board = new Board();
        tileDeck = deck;
    }

    public Game(Player... playersArray) {
        players = new ArrayList<Player>(Arrays.asList(playersArray));
        board = new Board();
    }

    public void runGameLoop() {
        while (!isGameOver()) {
            for (Player player : players) {
                //int idx = players.indexOf(player) + 1;
                //System.out.println("* Player " + idx + "'s turn *");
                try {
                    Tile tile = tileDeck.drawTile();
                    performTurn(player, tile);
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

    private void performTurn(Player player, Tile tile)  {
        Point point = performTileAction(player, tile);
        performBuildAction(player, point);
    }

    private Point performTileAction(Player player, Tile tile) {
        GameActionPerformer actionPerformer = player.getGameActionPerformer();

        Board boardCopy;
        Point tileCoordinate;

        while (true) {
            try {
                boardCopy = new Board(board);
                tileCoordinate = actionPerformer.tileAction(tile, boardCopy);
                boardCopy.placeTile(tile, tileCoordinate);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Please try again.");
            }
        }

        adoptBoard(boardCopy);
        return tileCoordinate;
    }

    private void performBuildAction(Player player, Point lastPlacedCoordinate) {
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