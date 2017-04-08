/**
 * Created by gonzalonunez on 3/21/17.
 */

import javafx.geometry.Point3D;

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

    public BuildAction(String serverString) {
        String[] split = serverString.split("\\s+");
        int x, y, z;
        switch (split.length) {
            case 6:
                // FOUND SETTLEMENT
                this.type = BuildActionType.FOUND_SETTLEMENT;
                x = Integer.parseInt(split[3]);
                y = Integer.parseInt(split[4]);
                z = Integer.parseInt(split[5]);
                this.coordinates = Board.cubeToAxial(new Point3D(x, y, z));
                break;
            case 7:
                // EXPAND SETTLEMENT
                if (split[0].equals("EXPANDED")) {
                    this.type = BuildActionType.EXPAND_SETTLEMENT;
                    this.terrainType = getTerrainTypeFromString(serverString);
                    x = Integer.parseInt(split[3]);
                    y = Integer.parseInt(split[4]);
                    z = Integer.parseInt(split[5]);
                    this.coordinates = Board.cubeToAxial(new Point3D(x, y, z));
                    this.terrainType = getTerrainTypeFromString(split[6]);
                }
                // BUILD TOTORO SANCTUARY
                else if (split[1].equals("TOTORO")) {
                    this.type = BuildActionType.TOTORO_SANCTUARY;
                    x = Integer.parseInt(split[4]);
                    y = Integer.parseInt(split[5]);
                    z = Integer.parseInt(split[6]);
                    this.coordinates = Board.cubeToAxial(new Point3D(x, y, z));
                }
                // BUILD TIGER PLAYGROUND
                else if (split[1].equals("TIGER")) {
                    this.type = BuildActionType.TIGER_PLAYGROUND;
                    x = Integer.parseInt(split[4]);
                    y = Integer.parseInt(split[5]);
                    z = Integer.parseInt(split[6]);
                    this.coordinates = Board.cubeToAxial(new Point3D(x, y, z));
                }
                break;
            default:
                break;
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

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public TerrainType getTerrainTypeFromString(String terrainString) {
        if (terrainString.equals("GRASSLANDS"))
            return TerrainType.GRASSLANDS;
        if (terrainString.equals("JUNGLE"))
            return TerrainType.JUNGLE;
        if (terrainString.equals("LAKE"))
            return TerrainType.LAKE;
        if (terrainString.equals("ROCKY"))
            return TerrainType.ROCKY;
        if (terrainString.equals("VOLCANO"))
            return TerrainType.VOLCANO;
        return null;
    }

    public String coordinatesToString() {
        Point3D point = Board.axialToCube(this.coordinates);
        return (int)point.getX() + " " + (int)point.getY() + " " + (int)point.getZ();
    }

    public String createServerStringFromBuildAction() {
        switch(this.type) {
            case FOUND_SETTLEMENT:
                return "FOUND SETTLEMENT AT " + coordinatesToString();
            case EXPAND_SETTLEMENT:
                return "EXPAND SETTLEMENT AT " + coordinatesToString() + " " + this.terrainType;
            case TOTORO_SANCTUARY:
                return "BUILD TOTORO SANCTUARY AT " + coordinatesToString();
            case TIGER_PLAYGROUND:
                return "BUILD TIGER PLAYGROUND AT " + coordinatesToString();
            default:
                return "UNABLE TO BUILD";
        }
    }

}
