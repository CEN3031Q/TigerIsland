/**
 * Created by gonzalonunez on 3/16/17.
 */
import javafx.geometry.Point3D;

public interface GameActionPerformer {
    Point3D tileAction(Tile tile, Board board);
    BuildAction buildAction(Board board);
}
