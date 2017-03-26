public class Tile {
    private Hexagon middleHex;
    private Hexagon rightHex;
    private Hexagon leftHex;
    private TileOrientation tileOrientation;
    private HexagonPosition anchor;

    public Tile(TerrainType leftType, TerrainType rightType, TerrainType middleType) {
        middleHex = new Hexagon(middleType);
        rightHex = new Hexagon(rightType);
        leftHex = new Hexagon(leftType);
        tileOrientation = TileOrientation.TOPHEAVY;
        anchor = HexagonPosition.MIDDLE;    //Used to determine valid placement of tile
    }

    public TerrainType getTerrainTypeForPosition(HexagonPosition position) {
        switch (position) {
            case MIDDLE:
                return middleHex.getTerrainType();
            case RIGHT:
                return rightHex.getTerrainType();
            case LEFT:
                return leftHex.getTerrainType();
        }
        return null;
    }

    public void clockwiseRotation() {
        //L->R, R->M, M->L
        if(tileOrientation == TileOrientation.TOPHEAVY) {
            Hexagon swapHex = leftHex;
            leftHex = middleHex;
            middleHex = rightHex;
            rightHex = swapHex;
        }
        //L->M, M->R, R->L
        else if(tileOrientation == TileOrientation.BOTTOMHEAVY) {
            Hexagon swapHex = leftHex;
            leftHex = rightHex;
            rightHex = middleHex;
            middleHex = swapHex;
        }
    }

    public void changeOrientation() {
        //swap M and R hexagons
        Hexagon swapHex = rightHex;
        rightHex = middleHex;
        middleHex = swapHex;
        if(tileOrientation == TileOrientation.TOPHEAVY) {
            tileOrientation = TileOrientation.BOTTOMHEAVY;
        } else if (tileOrientation == TileOrientation.BOTTOMHEAVY) {
            tileOrientation = TileOrientation.TOPHEAVY;
        }
    }

    public void changeAnchorPosition(HexagonPosition newAnchor) {
        this.anchor = newAnchor;
    }

    public HexagonPosition getAnchorPosition() {
        return this.anchor;
    }

    public TileOrientation getOrientation() {
        return this.tileOrientation;
    }

}
