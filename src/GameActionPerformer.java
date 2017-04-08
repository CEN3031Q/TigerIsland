/**
 * Created by gonzalonunez on 3/16/17.
 */

public interface GameActionPerformer {
    TileAction tileAction(Tile tile, Board board);
    BuildAction buildAction(Board board);
}
