/**
 * Created by gonzalonunez on 3/21/17.
 */
import javafx.geometry.Point3D;

public class BuildAction {
    private BuildActionType type;
    private Point3D coordinates;
    private TerrainType terrainType = TerrainType.EMPTY;

    public BuildAction(BuildActionType type, Point3D coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    // This is a very questionable design, it banks off of us never choosing or doing this option so just don't lmao
    public BuildAction(BuildActionType type, Point3D coordinates, TerrainType terrainType) throws IllegalArgumentException {
        this.type = type;
        this.coordinates = coordinates;
        this.terrainType = terrainType;

        if (type == BuildActionType.EXPAND_SETTLEMENT && (terrainType == TerrainType.EMPTY || terrainType == TerrainType.VOLCANO)) {
            throw new IllegalArgumentException("You cannot expand settlements via volcanoes or empty terrain");
        }
    }

    public boolean isExpansionAction() {
        return terrainType != TerrainType.EMPTY;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public Point3D getCoordinates() {
        return coordinates;
    }
}
