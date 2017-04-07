import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * Created by taylo on 4/6/2017.
 */
public class TigerServerProtocols {
    private ServerSocket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TigerServerProtocols(ServerSocket socket, BufferedReader reader, PrintWriter writer) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    public String authenticate(String input) {
        String response = null;
        boolean authenticate = false;
        while (!authenticate) {
            if (input.startsWith("ENTER")) {
                response = "TWO SHALL ENTER, ONE SHALL LEAVE";
                writer.println(response);
            } else if (input.startsWith("I AM")) {
                response = "WAIT FOR THE TOURNAMENT TO BEGIN 123";
                authenticate = true;
                //our pid = 123 in this case
            }
        }
        return response;
    }

    public String challenge(int totalRounds, boolean endChallenges, boolean startChallenges) {
        String response = null;
        if (startChallenges) {
            response = "NEW CHALLENGE CID YOU WILL PLAY " + totalRounds + " MATCHES";
        } else if (!endChallenges) {
            response = "WAIT FOR THE NEXT CHALLENGE TO BEGIN";
        } else {
            response = "END OF CHALLENGES";
        }

        return response;
    }

    public String rounnd(int currentRound, int totalRounds, boolean startRound, boolean endRound) {
        String response = null;
        if (startRound) {
            response = "BEGIN ROUND " + currentRound + " OF " + totalRounds;
        } else if (endRound && currentRound != totalRounds) {
            response = "END OF ROUND " + currentRound + " OF " + totalRounds + " WAIT FOR THE NEXT MATCH";
        } else {
            response = "END OF ROUND " + currentRound + " OF " + totalRounds;
        }
        return response;
    }

    public String move(int gid, int time, int moveNumber, String tile) {
        String response = "MAKE YOUR MOVE IN GAME " + gid + " WITHIN " + time + " SECONDS: MOVE " + moveNumber + " PLACE " + tile;
        return response;
    }

}
