import javafx.geometry.Point3D;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/7/17.
 */

//TODO: Do all of these TODOs!!
public class TournamentDriver implements ServerProtocolInfoCommunicator {
    private HashMap<String, String> receivedInfo;

    private Tournament tournament;

    public TournamentDriver() { }

    public void receiveInfo(ServerProtocol sender, String message, HashMap<String, String> info) {
        receivedInfo = info;

        String[] split = message.split("\\s+");

        if (sender instanceof AuthenticationProtocol) {
            String ourPID = info.get("pid");

            synchronized (this) {
                tournament = new Tournament(ourPID);
            }

        } else if (sender instanceof ChallengeProtocol) {
            String cid = info.get("cid");
            String rounds = info.get("rounds");

            //TODO: do something with the new cid and # rounds?

        } else if (sender instanceof RoundProtocol) {
            String rid = info.get("rid");
            String rounds = info.get("rounds");

            boolean roundBeginning =  Boolean.valueOf(info.get("beginRound"));
            //boolean waitForNextMatch = Boolean.valueOf(info.get("waitForNextMatch"));

            if (roundBeginning) {
                synchronized (this) {
                    if (tournament == null) {
                        return;
                    }
                    synchronized (tournament) {
                        tournament.startNewRound(rid, rounds);
                    }
                }
            }
        } else if (sender instanceof MatchProtocol) {
            if (message.startsWith("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER")) {
                String pid = info.get("pid");

                synchronized (this) {
                    if (tournament == null) {
                        return;
                    }
                    synchronized (tournament) {
                        tournament.startNewMatch(pid);
                    }
                }
            } else if (split.length > 3 && split[0].equals("GAME") && split[2].equals("OVER")) {
                String gid = info.get("gid");

                String pid1 = info.get("pid1");
                String score1 = info.get("score1");

                String pid2 = info.get("pid2");
                String score2 = info.get("score2");

                synchronized (this) {
                    if (tournament == null) {
                        return;
                    }
                    synchronized (tournament) {
                        tournament.endMatch(gid, pid1, score1, pid2, score2);
                    }
                }
            }
        } else if (sender instanceof MoveProtocol) {
            if (split[0].equals("GAME") && split[2].equals("MOVE")) {

                String gid = info.get("gid");
                String moveNumber = info.get("moveNumber");
                String pid = info.get("pid");

                boolean gameEnded = Boolean.valueOf(info.get("gameEnded"));

                if (gameEnded) {
                    tournament.endGame(gid);
                    return;
                }

                TileAction tileAction = tileActionFromInfo(pid, info);
                BuildAction buildAction = buildActionFromInfo(pid, info);

                tournament.applyMoveForGame(gid, tileAction, buildAction);
            }
        }
    }

    public String replyForMessage(ServerProtocol sender, String str) {
        if (sender instanceof MoveProtocol) {
            if (str.startsWith("MAKE YOUR MOVE IN GAME")) {
                String gid = receivedInfo.get("gid");
                String time = receivedInfo.get("time");
                String moveNumber = receivedInfo.get("moveNumber");

                String tileString = receivedInfo.get("tile");
                Tile tile = new Tile(tileString);

                return tournament.makeMoveForGame(gid, time, moveNumber, tile);
            }
        }
        return null;
    }

    /** HELPERS **/

    private TileAction tileActionFromInfo(String pid, HashMap<String, String> info) {
        String tileString = info.get("tile");
        Integer orientation = Integer.parseInt(info.get("orientation"));

        Tile tile = new Tile(tileString);
        tile.setOrientation(orientation);

        Integer tileX = Integer.parseInt(info.get("tileX"));
        Integer tileY = Integer.parseInt(info.get("tileY"));
        Integer tileZ = Integer.parseInt(info.get("tileZ"));

        Point tileOffset = Board.cubeToAxial(new Point3D(tileX, tileY, tileZ));

        return new TileAction(pid, tile, tileOffset, orientation);
    }

    private BuildAction buildActionFromInfo(String pid, HashMap<String, String> info) {
        boolean expanded = Boolean.valueOf(info.get("expanded"));
        boolean totoro = Boolean.valueOf(info.get("totoro"));
        boolean tiger = Boolean.valueOf(info.get("tiger"));

        BuildActionType buildActionType = BuildActionType.FOUND_SETTLEMENT;

        if (expanded) {
            buildActionType = BuildActionType.EXPAND_SETTLEMENT;
        } else if (totoro) {
            buildActionType = BuildActionType.TOTORO_SANCTUARY;
        } else if (tiger) {
            buildActionType = BuildActionType.TIGER_PLAYGROUND;
        }

        TerrainType terrainType = TerrainType.EMPTY;
        if (expanded) {
            terrainType = TerrainType.valueOf(info.get("terrain"));
        }

        Integer buildX = Integer.parseInt(info.get("buildX"));
        Integer buildY = Integer.parseInt(info.get("buildY"));
        Integer buildZ = Integer.parseInt(info.get("buildZ"));

        Point buildOffset = Board.cubeToAxial(new Point3D(buildX, buildY, buildZ));

        return new BuildAction(pid, buildActionType, buildOffset, terrainType);
    }
}