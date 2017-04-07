import java.awt.Point;

public class Player {
    private int id;
    private Game game;

    private GameActionPerformer gameActionPerformer;

    public Player(int id, Game game) {
        this.id = id;
        this.game = game;

        this.gameActionPerformer = new GameAI(id);
    }

    public int getPlayerID() {
        return id;
    }

    /**** CHANNELS OF COMMUNICATION ****/

    // Make our move

    public Point performTileAction(Tile tile) {
        Point chosenPoint = gameActionPerformer.tileAction(tile, game.getGameBoard());
        game.applyTileAction(tile, chosenPoint);
        return chosenPoint;
    }

    public BuildAction performBuildAction() {
        BuildAction chosenBuildAction = gameActionPerformer.buildAction(game.getGameBoard());
        game.applyBuildAction(chosenBuildAction);
        return chosenBuildAction;
    }

    // Apply opponents move

    public void applyOtherTileAction(Tile tile, Point point) {
        game.applyTileAction(tile, point);
    }

    public void applyOtherBuildAction(BuildAction action) {
        game.applyBuildAction(action);
    }
}