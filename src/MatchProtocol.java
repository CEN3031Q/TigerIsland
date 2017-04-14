import java.util.HashMap;

public class MatchProtocol implements ServerProtocol {
    ServerProtocolInfoCommunicator communicator;

    public MatchProtocol(ServerProtocolInfoCommunicator communicator) {
        this.communicator = communicator;
    }

    public boolean messageAppliesToProtocol(String str) {
        String[] split = str.split("\\s+");
        return str.startsWith("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER") ||
                (split.length > 3 && split[0].equals("GAME") && split[2].equals("OVER"));
    }

    public String responseForMessage(String str) {
        String[] split = str.split("\\s+");

        HashMap<String, String> info = new HashMap<>();

        if (str.startsWith("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER")) {
            if (split.length > 8) {
                info.put("pid", split[8]);
            }
        } else if (split.length > 3 && split[0].equals("GAME") && split[2].equals("OVER")) {
            if (split.length > 8) {
                info.put("gid", split[1]);

                info.put("pid1", split[4]);
                info.put("score1", split[5]);

                info.put("pid2", split[7]);
                info.put("score2", split[8]);
            }
        }

        communicator.receiveInfo(this, str, info);
        return null;
    }
}
