public class Player {
    private int playerID;
    private Inventory inventory;
    private GameActionPerformer gameActionPerformer;

    // The tile the player has just drawn
    // Currently set to public for testing purposes
    // Maybe move this responsibility elsewhere
    Tile tileInHand;

    public Player(int playerID, GameActionPerformer actionPerformer) {
        this.playerID = playerID;
        inventory = new Inventory(playerID);
        gameActionPerformer = actionPerformer;

        tileInHand = null;
    }

    public int getPlayerID() {return playerID;}
    public GameActionPerformer getGameActionPerformer() {
        return gameActionPerformer;
    }
    public int getMeepleSize(){return inventory.getMeepleSize();}
    public int getTotoroSize(){return inventory.getTotoroSize();}
    public int getTigerSize(){return inventory.getTigerSize();}
    public Meeple placeMeeplePiece(){return inventory.removeMeeplePiece();}
    public Totoro placeTotoroPiece(){return inventory.removeTotoroPiece();}
    public Tiger placeTigerPiece(){return inventory.removeTigerPiece();}
}