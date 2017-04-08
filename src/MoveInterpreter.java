import javafx.geometry.Point3D;

import java.awt.*;

/**
 * Created by taylo on 4/4/2017.
 */
public class MoveInterpreter {
    private Tile gameTile;
    private Point gameTilePoint;
    private BuildAction gameBuildAction;

    public MoveInterpreter() {
    }


    public void serverMoveToGameFormat(String pid, int moveType, String tile, Point3D tilePoint, Point3D piecePoint, int orientation, TerrainType terrain) {
        this.gameTilePoint = Board.cubeToAxial(tilePoint);

        String delims = "[+]";
        String[] tokens = tile.split(delims);
        this.gameTile = new Tile(TerrainType.valueOf(tokens[0]), TerrainType.valueOf(tokens[1]));
        this.gameTile.setOrientation(orientation);

        Point gamePiece = Board.cubeToAxial(piecePoint);

        BuildActionType buildActionType = BuildActionType.UNABLE_TO_BUILD;
        switch (moveType) {
            case 1:
                buildActionType = BuildActionType.FOUND_SETTLEMENT;
                break;
            case 2:
                buildActionType = BuildActionType.EXPAND_SETTLEMENT;
                break;
            case 3:
                buildActionType = BuildActionType.TOTORO_SANCTUARY;
                break;
            case 4:
                buildActionType = BuildActionType.TIGER_PLAYGROUND;
                break;
            case 5:
                buildActionType = BuildActionType.UNABLE_TO_BUILD;
                break;
        }

        this.gameBuildAction = new BuildAction(pid, buildActionType, gamePiece);
        this.gameBuildAction.setTerrainType(terrain);
    }


    public Tile getGameTile() {
        return this.gameTile;
    }

    public Point getGameTilePoint() {
        return this.gameTilePoint;
    }

    public BuildAction getGameBuildAction() {
        return this.gameBuildAction;
    }
}