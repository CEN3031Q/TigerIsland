import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public interface ServerProtocolInfoCommunicator {
    void receiveInfo(ServerProtocol sender, String message, HashMap<String, String> info);
    String replyForMessage(ServerProtocol sender, String message, HashMap<String, String> info);
}
