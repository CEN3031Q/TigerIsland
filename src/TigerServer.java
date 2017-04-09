import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
                Scanner stdIn = new Scanner(System.in);
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

                    //True means server goes first in 1 at a time
                    //False means AI goes first in 1 at a time
                    boolean active = false;

                    for (int kk = 0; kk < 48; kk++) {
                        Tile tile = gameDeck.drawTile();
                        String nextTile = tile.toString(tile);
                        System.out.println(">>>Next Tile: " + nextTile);

                        //1 Game test: WORKS
                        //Comment out the Multiple game handling test to run this and uncomment the kk loop header
                        try {
                            if (!active) {
                                int moveNumber = kk + 1;
                                out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE " + moveNumber + " PLACE " + nextTile);
                                System.out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE " + moveNumber + " PLACE " + nextTile);
                                Thread.sleep(500);

                                inputLine = in.readLine();

                                if (inputLine.contains("FOUND")) {
                                    inputLine = inputLine.replaceAll("FOUND", "FOUNDED");
                                } else if (inputLine.contains("EXPAND")) {
                                    inputLine = inputLine.replaceAll("EXPAND", "EXPANDED");
                                } else if (inputLine.contains("BUILD")) {
                                    inputLine = inputLine.replaceAll("BUILD", "BUILT");
                                }

                                out.println(inputLine.replaceAll("PLACE", "PLAYER PLAYER1 PLACED"));
                                System.out.println(inputLine.replaceAll("PLACE", "PLAYER PLAYER1 PLACED"));
                                Thread.sleep(500);

                            } else {
                                String outMove = stdIn.nextLine();
                                out.println(outMove);
                                System.out.println(outMove);
                                Thread.sleep(500);

                            }
                            active = !active;

                        } catch (InterruptedException e) {
                        }
                    }

                    //Multiple game handling test: WORKS
                    //Comment out kk loop header and the 1 game at a time try/catch
                    /*try {
                        String makeMove = "MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE " + 1 + " PLACE JUNGLE+ROCKY";
                        out.println(makeMove);
                        Thread.sleep(500);

                        String response = in.readLine();
                        if (response.contains("FOUND")) {
                            response = response.replaceAll("FOUND", "FOUNDED");
                        } else if (response.contains("EXPAND")) {
                            response = response.replaceAll("EXPAND", "EXPANDED");
                        } else if (response.contains("BUILD")) {
                            response = response.replaceAll("BUILD", "BUILT");
                        }

                        out.println(response.replaceAll("PLACE", "PLAYER PLAYER1 PLACED"));
                        out.println("GAME B MOVE 1 PLAYER PLAYER2 PLACED JUNGLE+ROCKY AT 2 0 -2 3 FOUNDED SETTLEMENT AT -1 0 1");
                        Thread.sleep(500);

                        makeMove = "MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE " + 2 + " PLACE GRASSLANDS+LAKE";
                        out.println(makeMove);
                        Thread.sleep(500);

                        response = in.readLine();
                        if (response.contains("FOUND")) {
                            response = response.replaceAll("FOUND", "FOUNDED");
                        } else if (response.contains("EXPAND")) {
                            response = response.replaceAll("EXPAND", "EXPANDED");
                        } else if (response.contains("BUILD")) {
                            response = response.replaceAll("BUILD", "BUILT");
                        }

                        out.println("GAME A MOVE 2 PLAYER PLAYER2 PLACED LAKE+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT -1 0 1");
                        out.println(response.replaceAll("PLACE", "PLAYER PLAYER1 PLACED"));

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }

                    out.println("GAME A OVER PLAYER PLAYER1 SCORE1 PLAYER PLAYER2 SCORE2");
                    out.println("GAME B OVER PLAYER PLAYER1 SCORE1 PLAYER PLAYER2 SCORE2");
                    */

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
