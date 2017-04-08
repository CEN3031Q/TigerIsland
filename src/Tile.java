public class Tile {
    private Hexagon aHex;
    private Hexagon bHex;

    private Integer orientation;

    public Tile(TerrainType aType, TerrainType bType) {
        aHex = new Hexagon(aType);
        bHex = new Hexagon(bType);
        orientation = 1;
    }

    public Tile(String serverString) {
        String[] split = serverString.split("\\+");

        TerrainType aType = TerrainType.valueOf(split[0]);
        TerrainType bType = TerrainType.valueOf(split[1]);

        aHex = new Hexagon(aType);
        bHex = new Hexagon(bType);
    }

    public TerrainType getTerrainTypeForPosition(HexagonPosition position) {
        switch (position) {
            case A:
                return aHex.getTerrainType();
            case B:
                return bHex.getTerrainType();

        }
        return null;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public String toString(Tile tile) {
        return String.valueOf(tile.getTerrainTypeForPosition(HexagonPosition.A)) + "+" + String.valueOf(tile.getTerrainTypeForPosition(HexagonPosition.B));
    }
}
