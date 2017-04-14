import javafx.geometry.Point3D;
import java.awt.*;

public class TileAction {
    private String id;
    private Tile tile;
    private Point offset;
    private int orientation;

    public TileAction(String id, Tile tile, Point offset, int orientation) {
        this.id = id;
        this.tile = tile;
        this.offset = offset;
        this.orientation = orientation;

        tile.setOrientation(orientation);
    }

    public String getIDString() {
        return id;
    }

    //converts Tile's terrains A+B into String
    public String tileToString() {
        TerrainType hexATerrain = tile.getTerrainTypeForPosition(HexagonPosition.A);
        TerrainType hexBTerrain = tile.getTerrainTypeForPosition(HexagonPosition.B);
        return String.valueOf(hexATerrain).concat("+").concat(String.valueOf(hexBTerrain));
    }

    public Point getOffset() {
        return offset;
    }

    public String orientationToString() {
        return Integer.toString(orientation);
    }

    //converts Point offset x,z into x,y,z (also casted int from double)
    public String offsetToString() {
        Point3D point = Board.axialToCube(this.offset);
        return (int)point.getX() + " " + (int)point.getY() + " " + (int)point.getZ();
    }

    //combines all toString methods into one method
    public String tileActionToString() {
        return "PLACE " + tileToString() + " AT " + offsetToString() + " " + orientationToString();
    }

    public Tile getTile() { return tile; }
    public int getOrientation(){ return orientation;}
}
