import java.awt.*;
import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/8/17.
 */
public class Tournament {
    private String ourPID;
    private String opponentPID;

    final private HashMap<String, Game> games = new HashMap<>();
    final private HashMap<String, Player> players = new HashMap<>();

    public Tournament(String ourPID) {
        this.ourPID = ourPID;
    }

    public String getOurPID() {
        return ourPID;
    }

    public void startNewRound(String rid, String rounds) {
        synchronized (players) {
            for (String gid : players.keySet()) {
                players.remove(gid);
            }
        }
    }

    public void startNewMatch(String opponentPID) {
        this.opponentPID = opponentPID;
    }

    public void endMatch(String gid, String pid1, String score1, String pid2, String score2) {
        synchronized (games) {
            games.remove(gid);
        }
    }

    public void endGame(String gid) {
        synchronized (games) {
            games.remove(gid);
        }
    }

    public void applyMoveForGame(String gid, TileAction tileAction, BuildAction buildAction) {
        if (tileAction.getIDString().equals(ourPID) || buildAction.getID().equals(ourPID)) {
            // We don't want to reapply our move, we apply it in performTileAction and performBuildAction.
            return;
        }

        Game game = createGameIfNeeded(gid);
        synchronized (game) {
            game.applyTileAction(tileAction);
            game.applyBuildAction(buildAction);
        }
    }

    public String makeMoveForGame(String gid, String time, String moveNumber, Tile tile) {
        Game game = createGameIfNeeded(gid);
        synchronized (game) {
            Player player;
            synchronized (players) {
                player = players.get(gid);
            }

            TileAction tileAction = player.performTileAction(tile);
            BuildAction buildAction = player.performBuildAction();

            return "GAME " + gid +
                    " MOVE " + moveNumber + " " +
                    tileAction.tileActionToString() + " " +
                    buildAction.createServerStringFromBuildAction();
        }
    }

    private Game createGameIfNeeded(String gid) {
        synchronized (games) {
            Game game = games.get(gid);
            if (game == null) {
                game = new Game(gid);
                games.put(gid, game);

                Player player = new Player(ourPID, game);
                players.put(gid, player);
            }
            return game;
        }
    }
}