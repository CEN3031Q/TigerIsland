import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by taylo on 4/4/2017.
 */
public class TigerServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {

            String inputLine;
            Deck gameDeck = new Deck();

            // Initiate conversation with client
            out.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
            in.readLine();
            out.println("TWO SHALL ENTER, ONE SHALL LEAVE");
            in.readLine();
            out.println("WAIT FOR THE TOURNAMENT TO BEGIN PLAYER1");
            int challenges = 1;
            out.println("NEW CHALLENGE CID YOU WILL PLAY " + challenges + " MATCHES");
            for (int ii = 0; ii < challenges; ii++) {
                int rounds = 2;
                out.println("BEGIN ROUND 1 OF " + rounds);
                for (int jj = 0; jj < rounds; jj++) {
                    out.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER PLAYER2");

                    boolean active = true;
                    for (int kk = 0; kk < 48; kk++) {
                        Tile tile = gameDeck.drawTile();
                        String nextTile = tile.toString(tile);
                        if (!active) {
                            out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECOND: MOVE " + (kk+1) + " PLACE " + nextTile);
                            inputLine = in.readLine();

                            if (inputLine.contains("FOUND")) {
                                inputLine.replace("FOUND", "FOUNDED");
                            } else if (inputLine.contains("EXPAND")) {
                                inputLine.replace("EXPAND", "EXPANDED");
                            } else if (inputLine.contains("BUILD")) {
                                inputLine.replace("BUILD", "BUILT");
                            }
                            out.println(inputLine.replace("PLACE", "PLAYER PLAYER1 PLACE"));
                        } else {
                            out.println(stdIn.readLine());
                        }
                        active = !active;
                    }


                    out.println("GAME A OVER PLAYER PLAYER1 SCORE1 PLAYER PLAYER2 SCORE2");
                    out.println("GAME B OVER PLAYER PLAYER1 SCORE1 PLAYER PLAYER2 SCORE2");

                    if (jj+1 == rounds) {
                        out.println("END OF ROUND " + jj + " OF " + rounds);
                    } else {
                        out.println("END OF ROUND " + rounds + " OF " + rounds + " WAIT FOR THE NEXT MATCH");
                    }
                }



                if (ii+1 != challenges) {
                    out.println("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
                }
            }

            out.println("END OF CHALLENGES");
            out.println("THANK YOU FOR PLAYING! GOODBYE");




        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
