import java.awt.Point;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Game {
    String id;
    private Board board;

    public Game(String id) {
        this.id = id;
        board = new Board();
    }

    public void applyTileAction(TileAction tileAction) {
        tileAction.getTile().setOrientation(tileAction.getOrientation());
        board.placeTile(tileAction.getTile(), Board.axialToCube(tileAction.getOffset()));
    }

    public void applyBuildAction(BuildAction buildAction) {
        Point offset = buildAction.getCoordinates();
        String id = buildAction.getID();
        TerrainType terrainType = buildAction.getTerrainType();

        switch (buildAction.getType()) {
            case FOUND_SETTLEMENT:
                board.foundSettlementAtOffset(offset, id);
                break;
            case EXPAND_SETTLEMENT:
                board.expandSettlementAtOffset(offset, terrainType, id);
                break;
            case TIGER_PLAYGROUND:
                board.buildTigerPlaygroundAtOffset(offset, id);
                break;
            case TOTORO_SANCTUARY:
                board.buildTotoroSanctuaryAtOffset(offset, id);
                break;
            default:
                break;
        }
    }

    public Board getGameBoard() {
        return board;
    }
}