import java.util.HashMap;

public class ChallengeProtocol implements ServerProtocol {
    ServerProtocolInfoCommunicator communicator;

    public ChallengeProtocol(ServerProtocolInfoCommunicator communicator) {
        this.communicator = communicator;
    }

    public boolean messageAppliesToProtocol(String str) {
        return str.startsWith("NEW CHALLENGE") ||
                str.equals("END OF CHALLENGES") ||
                str.equals("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
    }

    public String responseForMessage(String str) {
        if (str.startsWith("NEW CHALLENGE")) {
            String[] split = str.split("\\s+");
            if (split.length > 7) {
                HashMap<String, String> info = new HashMap<>();

                info.put("cid", split[2]);
                info.put("rounds", split[6]);

                communicator.receiveInfo(this, str, info);
            }
        }
        return null;
    }
}