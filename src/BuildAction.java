/**
 * Created by gonzalonunez on 3/21/17.
 */

import java.awt.*;

public class BuildAction {
    private int id;
    private BuildActionType type;
    private Point coordinates;
    private TerrainType terrainType = TerrainType.EMPTY;

    public BuildAction(int id, BuildActionType type, Point coordinates) {
        this.id = id;
        this.type = type;
        this.coordinates = coordinates;
    }

    public BuildAction(BuildActionType type, Point coordinates, TerrainType terrainType) throws IllegalArgumentException {
        this.type = type;
        this.coordinates = coordinates;
        this.terrainType = terrainType;

        if (type == BuildActionType.EXPAND_SETTLEMENT && (terrainType == TerrainType.EMPTY || terrainType == TerrainType.VOLCANO)) {
            throw new IllegalArgumentException("You cannot expand settlements via volcanoes or empty terrain");
        }
    }

    public Integer getID() {
        return id;
    }

    public BuildActionType getType() {
        return type;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public boolean isExpansionAction() {
        return terrainType != TerrainType.EMPTY;
    }
}
