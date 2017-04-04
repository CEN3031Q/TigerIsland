/**
 * Created by gonzalonunez on 3/21/17.
 */
public class TigerIsland {
    //TODO: Server listener, multithread the two games...
    static public void main(String... args) {
        Game firstGame = new Game();
        Player firstPlayer = new Player(1, firstGame);

        Game secondGame = new Game();
        Player secondPlayer = new Player(2, secondGame);
    }
}