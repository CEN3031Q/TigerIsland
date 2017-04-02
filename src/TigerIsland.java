/**
 * Created by gonzalonunez on 3/21/17.
 */
public class TigerIsland {
    //TODO: use args to indicate number of players for now? We're gonna need args later for connecting to a server, etc...
    static public void main(String... args) {
        UserInteractor sharedInteractor = new UserInteractor();

        Player firstPlayer = new Player(1, sharedInteractor);
        Player secondPlayer = new Player(2, sharedInteractor);

        Game game = new Game(firstPlayer, secondPlayer);
        game.runGameLoop();

        sharedInteractor.close();
    }
}