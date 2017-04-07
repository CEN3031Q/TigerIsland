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
    private boolean containsTotoro;
    private boolean containsTiger;

    // Constructor taking in a Point object
    public Settlement() {
        containsTotoro = false;
        containsTiger = false;
    }

    @Override
    public int hashCode() {
        return offsets.hashCode() + (containsTotoro ? 1 : 0) + (containsTiger ? 1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Settlement))
            return false;
        if (obj == this)
            return true;
        Settlement rhs = (Settlement) obj;
        return offsets.equals(rhs.offsets) &&
                containsTotoro == rhs.containsTotoro &&
                containsTiger == rhs.containsTiger;
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

    public void setSettlementContainsTotoro() {
        containsTotoro = true;
    }

    public boolean containsTotoro() {
        return containsTotoro;
    }

    public void setSettlementContainsTiger() {
        containsTiger = true;
    }

    public boolean containsTiger() {
        return containsTiger;
    }
}
