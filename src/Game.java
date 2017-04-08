import java.awt.Point;

/**
 * Created by gonzalonunez on 3/16/17.
 */

public class Game {
    private Board board;
    private Deck tileDeck = new Deck();

    public Game(Deck deck) {
        board = new Board();
        tileDeck = deck;
    }

    public Game() {
        board = new Board();
    }

    public void applyTileAction(TileAction tileAction) {
        tileAction.getTile().setOrientation(tileAction.getOrientation());
        board.placeTile(tileAction.getTile(), Board.axialToCube(tileAction.getOffset()));
        board.getSettlementManager().updateSettlements();
    }

    public void applyBuildAction(BuildAction buildAction) {
        Point offset = buildAction.getCoordinates();
        Integer id = buildAction.getID();
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

        board.getSettlementManager().updateSettlements();
    }

    public Board getGameBoard() {
        return board;
    }
}