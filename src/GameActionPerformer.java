public interface GameActionPerformer {
    TileAction tileAction(Tile tile, Board board);
    BuildAction buildAction(Board board);
}
