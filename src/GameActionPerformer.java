/**
 * Created by gonzalonunez on 3/16/17.
 */
import java.awt.Point;

public interface GameActionPerformer {
    Point tileAction(Tile tile, Board board);
    BuildAction buildAction(Board board);
}
