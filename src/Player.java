import javafx.geometry.Point3D;

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

    public Point3D performTileAction(Tile tile) {
        Point3D chosenPoint = gameActionPerformer.tileAction(tile, game.getBoardCopy());
        game.applyTileAction(tile, chosenPoint);
        return chosenPoint;
    }

    public BuildAction performBuildAction() {
        BuildAction chosenBuildAction = gameActionPerformer.buildAction(game.getBoardCopy());
        game.applyBuildAction(chosenBuildAction);
        return chosenBuildAction;
    }

    // Apply opponents move

    public void applyOtherTileAction(Tile tile, Point3D point) {
        game.applyTileAction(tile, point);
    }

    public void applyOtherBuildAction(BuildAction action) {
        game.applyBuildAction(action);
    }
}