/**
 * Created by gonzalonunez on 4/5/17.
 */
import com.sun.org.apache.regexp.internal.RE;

import java.awt.Point;

public class RequirementsToStack {
    private Point offset;
    private int orientation;

    public RequirementsToStack(Point offset, int orientation) {
        this.offset = offset;
        this.orientation = orientation;
    }

    @Override
    public int hashCode() {
        return offset.hashCode() + orientation;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RequirementsToStack))
            return false;
        if (obj == this)
            return true;
        RequirementsToStack rhs = (RequirementsToStack) obj;
        return offset.equals(rhs.offset) &
                orientation == rhs.orientation;
    }

    public Point getOffset() {
        return offset;
    }

    public int getOrientation() {
        return orientation;
    }
}
