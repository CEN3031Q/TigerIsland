import java.awt.Point;

public class Player {
    private String id;
    private Game game;

    private GameActionPerformer gameActionPerformer;

    public Player(String id, Game game) {
        this.id = id;
        this.game = game;

        this.gameActionPerformer = new GameAI(id);
    }

    public String getPlayerID() {
        return id;
    }

    /**** CHANNELS OF COMMUNICATION ****/

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
}