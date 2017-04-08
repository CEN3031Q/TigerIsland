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

    public TileAction performTileAction(Tile tile) {
        TileAction chosenAction = gameActionPerformer.tileAction(tile, game.getGameBoard());
        game.applyTileAction(chosenAction);
        return chosenAction;
    }

    public BuildAction performBuildAction() {
        BuildAction chosenBuildAction = gameActionPerformer.buildAction(game.getGameBoard());
        game.applyBuildAction(chosenBuildAction);
        return chosenBuildAction;
    }

    // Apply opponents move

    public void applyOtherTileAction(TileAction action) {
        game.applyTileAction(action);
    }

    public void applyOtherBuildAction(BuildAction action) {
        game.applyBuildAction(action);
    }
}