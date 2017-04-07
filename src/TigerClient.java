import javafx.geometry.Point3D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by taylo on 4/3/2017.
 */
public class TigerClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter writer = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            TigerClientProtocols client = new TigerClientProtocols(kkSocket, reader, writer);

            while ((fromServer = reader.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                String ourPlayerID = client.authenticator("A", "B", "C");

                int numOfRounds;
                while ((numOfRounds = client.challengeProtocol()) != -1) {
                    for (int ii = 1; ii <= numOfRounds; ii++) {
                        String opponentPID = client.matchStartProtocol();

                        //TODO: MultiThread this AKA Dave's suggestion
                        Game game1 = new Game();
                        Game game2 = new Game();
                        Player p1 = new Player(Integer.parseInt(ourPlayerID), game1);
                        Player p2 = new Player(Integer.parseInt(opponentPID), game2);
                        ArrayList<Player> playerList = new ArrayList<>();
                        playerList.add(p1);
                        playerList.add(p2);
                        int activePlayer = 0;
                        int inactivePlayer = 1;

                        int gid = Integer.MIN_VALUE;
                        while ((gid = client.matchEndProtocol()) == Integer.MIN_VALUE) {
                            String[] moveConditions = client.getMoveConditions();

                            String strGID = moveConditions[0]; //What game are we active in
                            if (strGID != " ") {   //If we are not in an active game, then do not do a move
                                int moveNumber = Integer.parseInt(moveConditions[2]);
                                String tile = moveConditions[3];
                                String delims = "[+]";
                                String[] tokens = tile.split(delims);
                                Tile tileToPlace = new Tile(TerrainType.valueOf(tokens[0]), TerrainType.valueOf(tokens[1]));

                                Point3D pointTile = Board.axialToCube(playerList.get(activePlayer).performTileAction(tileToPlace));
                                BuildAction buildAction = playerList.get(activePlayer).performBuildAction();

                                String placeString = "PLACED " + tile + " AT " + pointTile.getX() + " " + pointTile.getY() + " " + pointTile.getZ() + " " + tileToPlace.getOrientation();
                                String buildType = String.valueOf(buildAction.getType());
                                String buildString = "";
                                if (buildType.startsWith("UNABLE")) {
                                    buildString = "UNABLE TO BUILD";
                                } else {
                                    if (buildType.startsWith("TIGER") || buildType.startsWith("TOTORO")) {
                                        buildString += "BUILD ";
                                    }
                                    buildString += buildType.replace('_', ' ') + " AT " + buildAction.coordinatesToString();
                                    if (buildType.startsWith("EXPAND")) {
                                        buildString += " " + buildAction.getTerrainType();
                                    }
                                }
                                client.moveSender(strGID, moveNumber, placeString, buildString);
                            }

                            MoveInterpreter opponentMove = client.moveReceiver(strGID);

                            playerList.get(inactivePlayer).applyOtherTileAction(opponentMove.getGameTile(), opponentMove.getGameTilePoint());
                            playerList.get(inactivePlayer).applyOtherBuildAction(opponentMove.getGameBuildAction());

                            int swap = activePlayer;
                            activePlayer = inactivePlayer;
                            inactivePlayer = swap;
                        }
                    }
                }

                fromServer = reader.readLine();
                if (fromServer.startsWith("THANK")) {
                    return;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}