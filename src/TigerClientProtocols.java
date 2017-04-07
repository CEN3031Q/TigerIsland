import javafx.geometry.Point3D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by taylo on 4/6/2017.
 */
public class TigerClientProtocols {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TigerClientProtocols(Socket socket, BufferedReader reader, PrintWriter writer) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    //Authentication Protocol
    public String authenticator(String username, String password, String tournamentPassword) throws IOException {
        String pid = null;

        String response;
        boolean authenticated = false;

        while (!authenticated) {
            response = reader.readLine();
            System.out.println(response);

            if (response.startsWith("WELCOME")) {
                System.out.println("ENTER THUNDERDOME " + tournamentPassword);
                writer.println("ENTER THUNDERDOME " + tournamentPassword);
            } else if (response.startsWith("TWO")) {
                System.out.println("I AM " + username + " " + password);
                writer.println("I AM " + username + " " + password);
            } else if (response.startsWith("WAIT")) {
                String delims = " ";
                String[] tokens = response.split(delims);
                pid = tokens[6];

                authenticated = true;
            } else if (response != null) {
                return null;
            }
        }

        return pid;
    }

    //Challenge protocol returns # of rounds
    public int challengeProtocol() throws IOException {
        String response;
        int rounds = 0;
        int cid = 0;
        boolean challenged = false;

        while (!challenged) {
            response = reader.readLine();
            System.out.println(response);

            if (response.startsWith("NEW CHALLENGE ")) {
                String delims = " ";
                String[] tokens = response.split(delims);
                rounds = Integer.parseInt(tokens[6]);

                challenged = true;
            } else if (response.startsWith("END")) {
                return -1;
            } else if (response.startsWith("WAIT")) {
                continue;
            }
        }
        return rounds;
    }

    //Get opponentPID
    public String matchStartProtocol() throws IOException {
        String response;
        String opponentPID = null;
        boolean newMatch = false;

        while (!newMatch) {
            response = reader.readLine();
            System.out.println(response);

            if (response.startsWith("NEW")) {
                String delims = " ";
                String[] tokens = response.split(delims);

                opponentPID = tokens[8];


                newMatch = true;
            }
        }

        return opponentPID;
    }

    //Get results of both matches
    public int matchEndProtocol() throws IOException {
        String response;
        Integer gameID = Integer.MIN_VALUE;

        response = reader.readLine();
        System.out.println(response);

        if (response.startsWith("GAME")) {
            String delims = " ";
            String[] tokens = response.split(delims);

            gameID = Integer.parseInt(tokens[1]);
        }

        return gameID;
    }

    //Get move conditions
    public String[] getMoveConditions() throws IOException {  //Gets info for the move
        String response;
        String info[] = new String[4];
        boolean moveCondits = false;

        while(!moveCondits) {
            response = reader.readLine();

            if (response.startsWith("MAKE ")) {
                System.out.println(response);

                String delims = " ";
                String[] tokens = response.split(delims);
                info[0] = tokens[5];    //gid
                info[1] = tokens[7];    //time
                info[2] = tokens[10];   //move#
                info[3] = tokens[12];   //tile
                moveCondits = true;
            }
            else {
                String empty[] = {" "};
                return empty;
            }
        }

        return info;
    }

    //Send server our move
    public void moveSender(String gid, int moveNumber, String place, String build) {
        System.out.println("GAME " + gid + " MOVE " + moveNumber + " " + place + " " + build);
        writer.println("GAME " + gid + " MOVE " + moveNumber + " " + place + " " + build);
    }

    //Receive moves
    public MoveInterpreter moveReceiver(String gid) throws IOException {
        MoveInterpreter received = new MoveInterpreter();
        String response;

        boolean receivedMove = false;


        while (!receivedMove) {
            response = reader.readLine();

            if (response.startsWith("GAME ")) {
                System.out.println(response);

                String delims = " ";
                String[] tokens = response.split(delims);

                String gidNew = tokens[1];
                if (gidNew == gid) {
                    continue;
                }

                int moveNumber = Integer.parseInt(tokens[3]);
                String pid = tokens[5];
                String tile = tokens[7];
                Point3D tileCoord = new Point3D(Integer.parseInt(tokens[9]),
                        Integer.parseInt(tokens[10]),
                        Integer.parseInt(tokens[11]));
                int orientation = Integer.parseInt(tokens[12]);

                Point3D pieceCoord = null;
                TerrainType terrain = TerrainType.EMPTY;
                int moveType = 0;

                if (tokens[13].equals("FOUNDED")) {
                    moveType = 1;
                    pieceCoord = new Point3D(Integer.parseInt(tokens[16]),
                            Integer.parseInt(tokens[17]),
                            Integer.parseInt(tokens[18]));
                } else if (tokens[13].equals("EXPANDED")) {
                    moveType = 2;
                    pieceCoord = new Point3D(Integer.parseInt(tokens[16]),
                            Integer.parseInt(tokens[17]),
                            Integer.parseInt(tokens[18]));
                    terrain = TerrainType.valueOf(tokens[19]);
                } else if (tokens[14].equals("TOTORO")) {
                    moveType = 3;
                    pieceCoord = new Point3D(Integer.parseInt(tokens[17]),
                            Integer.parseInt(tokens[18]),
                            Integer.parseInt(tokens[19]));
                } else if (tokens[14].equals("TIGER")) {
                    moveType = 4;
                    pieceCoord = new Point3D(Integer.parseInt(tokens[17]),
                            Integer.parseInt(tokens[18]),
                            Integer.parseInt(tokens[19]));
                } else if (tokens[13].equals("UNABLE")) {
                    return null;
                }

                received.serverMoveToGameFormat(pid, moveType, tile, tileCoord, pieceCoord, orientation, terrain);
            }
        }

        return received;
    }
}