import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hugh on 3/28/17.
 * Contains information about which spots on the board are considered a settlement
 * TODO: Find out what should be the entity should be in charge of settlements
 */

public class Settlement {
    // List of all hexagons in a particular settlement
    private Set<Point> offsets = new HashSet<>();

    // Constructor taking in a Point object
    public Settlement() { }

    @Override
    public int hashCode() {
        return offsets.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Settlement))
            return false;
        if (obj == this)
            return true;
        Settlement rhs = (Settlement) obj;
        return offsets.equals(rhs.offsets);
    }

    public Set<Point> getOffsets(){
        return offsets;
    }

    public void addOffset(Point pointToAdd){
        offsets.add(pointToAdd);
    }

    public int size() {
        return offsets.size();
    }

    public boolean containsOffset(Point offset) {
        return offsets.contains(offset);
    }

    public boolean containsTotoroForBoard(Board board) {
        for (Point offset : offsets) {
            Point boardPoint = board.boardPointForOffset(offset);
            Hexagon hex = board.hexagonAtPoint(boardPoint);
            if (hex.isTotoroOnTop()) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTigerForBoard(Board board) {
        for (Point offset : offsets) {
            Point boardPoint = board.boardPointForOffset(offset);
            Hexagon hex = board.hexagonAtPoint(boardPoint);
            if (hex.isTigerOnTop()) {
                return true;
            }
        }
        return false;
    }
}
