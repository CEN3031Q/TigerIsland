import javafx.geometry.Point3D;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public class TournamentDriver implements ServerProtocolInfoCommunicator {
    private HashMap<String, String> receivedInfo;

    public TournamentDriver() {

    }

    public void receiveInfo(ServerProtocol sender, String message, HashMap<String, String> info) {
        receivedInfo = info;

        String[] split = message.split("\\s+");

        if (sender instanceof AuthenticationProtocol) {
            String ourPID = info.get("pid");

            //TODO: do something with our pid

        } else if (sender instanceof ChallengeProtocol) {
            String cid = info.get("cid");
            String rounds = info.get("rounds");

            //TODO: do something with the new cid and # rounds

        } else if (sender instanceof RoundProtocol) {
            String rid = info.get("rid");
            String rounds = info.get("rounds");
            boolean waitForNextMatch = Boolean.valueOf(info.get("waitForNextMatch"));

            //TODO: do something with the new rid and waitForNextMatch

        } else if (sender instanceof MatchProtocol) {
            if (message.startsWith("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER")) {
                String pid = info.get("pid");

                //TODO: do something with our opponent's pid. It's a NEW MATCH

            } else if (split.length > 3 && split[0].equals("GAME") && split[2].equals("OVER")) {
                String gid = info.get("gid");

                String pid1 = info.get("pid1");
                String score1 = info.get("score1");

                String pid2 = info.get("pid2");
                String score2 = info.get("score2");

                //TODO: do something with the score info of the ENDED MATCH
            }
        } else if (sender instanceof MoveProtocol) {
            if (split[0].equals("GAME") && split[2].equals("MOVE")) {
                //TODO: apply the received move to the board

                String gid = info.get("gid");
                String moveNumber = info.get("moveNumber");
                String pid = info.get("pid");

                boolean gameEnded = Boolean.valueOf(info.get("gameEnded"));

                if (gameEnded) {
                    //TODO: end this game because somebody fucked up or was unable to build
                    return;
                }

                String tileString = info.get("tile");
                Integer orientation = Integer.parseInt(info.get("orientation"));

                Tile tile = new Tile(tileString);
                tile.setOrientation(orientation);

                Integer tileX = Integer.parseInt(info.get("tileX"));
                Integer tileY = Integer.parseInt(info.get("tileY"));
                Integer tileZ = Integer.parseInt(info.get("tileZ"));

                Point tileOffset = Board.cubeToAxial(new Point3D(tileX, tileY, tileZ));

                //TODO: TileAction tileAction = new TileAction(pid, tile, tileOffset, orientation);

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

                TerrainType terrainType = TerrainType.valueOf(info.get("terrain"));
                if (terrainType == null) { terrainType = TerrainType.EMPTY; }

                Integer buildX = Integer.parseInt(info.get("buildX"));
                Integer buildY = Integer.parseInt(info.get("buildY"));
                Integer buildZ = Integer.parseInt(info.get("buildZ"));

                Point buildOffset = Board.cubeToAxial(new Point3D(buildX, buildY, buildZ));

                //TODO: BuildAction buildAction = new BuildAction(pid, buildActionType, buildOffset, terrainType);

                //TODO: Apply tileAction and buildAction to the respective Game (by gid)
            }
        }
    }

    public String replyForMessage(ServerProtocol sender, String str) {
        if (sender instanceof MoveProtocol) {
            if (str.startsWith("MAKE YOUR MOVE IN GAME")) {
                String gid = receivedInfo.get("gid");
                String time = receivedInfo.get("time");
                String moveNumber = receivedInfo.get("moveNumber");
                String tile = receivedInfo.get("tile");

                //TODO: ask appropriate player and game for move
            }
        }

        return null;
    }
}
