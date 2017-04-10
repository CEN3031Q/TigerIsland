import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public class MoveProtocol implements ServerProtocol {
    ServerProtocolInfoCommunicator communicator;

    public MoveProtocol(ServerProtocolInfoCommunicator communicator) {
        this.communicator = communicator;
    }

    public boolean messageAppliesToProtocol(String str) {
        String[] split = str.split("\\s+");
        return str.startsWith("MAKE YOUR MOVE IN GAME") ||
                (split.length > 3 && split[0].equals("GAME") && split[2].equals("MOVE"));
    }

    public String responseForMessage(String str) {
        String[] split = str.split("\\s+");

        HashMap<String, String> info = new HashMap<>();

        if (str.startsWith("MAKE YOUR MOVE IN GAME")) {
            if (split.length > 12) {
                info.put("gid", split[5]);
                info.put("time", split[7]);
                info.put("moveNumber", split[10]);
                info.put("tile", split[12]);
            }
        } else if (split.length > 3 && split[0].equals("GAME") && split[2].equals("MOVE")) {
            if (split.length > 5) {
                info.put("gid", split[1]);
                info.put("moveNumber", split[3]);
                info.put("pid", split[5]);
            }

            info.put("gameEnded", String.valueOf(split.length > 6 && (split[6].equals("FORFEITED:") || split[6].equals("LOST:"))));

            if (split.length > 18) {
                info.put("tile", split[7]);

                info.put("tileX", split[9]);
                info.put("tileY", split[10]);
                info.put("tileZ", split[11]);

                info.put("orientation", split[12]);

                boolean founded = split[13].equals("FOUNDED");
                boolean expanded = split[13].equals("EXPANDED");
                boolean totoro = split[14].equals("TOTORO");
                boolean tiger = split[14].equals("TIGER");

                info.put("founded", String.valueOf(founded));
                info.put("expanded", String.valueOf(expanded));

                info.put("totoro", String.valueOf(totoro));
                info.put("tiger", String.valueOf(tiger));

                if (founded || expanded) {
                    info.put("buildX", split[16]);
                    info.put("buildY", split[17]);
                    info.put("buildZ", split[18]);

                    if (expanded) {
                        info.put("terrain", split[19]);
                    }

                } else if (totoro || tiger) {
                    info.put("buildX", split[17]);
                    info.put("buildY", split[18]);
                    info.put("buildZ", split[19]);
                }
            }
        }

        communicator.receiveInfo(this, str, info);
        return communicator.replyForMessage(this, str, info);
    };

}
