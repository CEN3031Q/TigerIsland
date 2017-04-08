import javax.management.StringValueExp;
import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public class RoundProtocol implements ServerProtocol {
    ServerProtocolInfoCommunicator communicator;

    public RoundProtocol(ServerProtocolInfoCommunicator communicator) {
        this.communicator = communicator;
    }

    public boolean messageAppliesToProtocol(String str) {
        return str.startsWith("BEGIN ROUND") ||
                str.startsWith("END OF ROUND");
    }

    public String responseForMessage(String str) {
        String[] split = str.split("\\s+");

        HashMap<String, String> info = new HashMap<>();

        if (str.startsWith("BEGIN ROUND")) {
            if (split.length > 4) {
                info.put("rid", split[2]);
                info.put("rounds", split[4]);
            }
        } else if (str.startsWith("END OF ROUND")) {
            if (split.length > 5) {
                info.put("rid", split[3]);
                info.put("rounds", split[5]);
            }
        }

        info.put("beginRound", String.valueOf(str.startsWith("BEGIN ROUND")));
        info.put("waitForNextMatch", String.valueOf(split.length > 6));

        communicator.receiveInfo(this, str, info);

        return null;
    }
}