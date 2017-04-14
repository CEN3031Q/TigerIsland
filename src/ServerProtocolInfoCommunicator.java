import java.util.HashMap;

public interface ServerProtocolInfoCommunicator {
    void receiveInfo(ServerProtocol sender, String message, HashMap<String, String> info);
    String replyForMessage(ServerProtocol sender, String message, HashMap<String, String> info);
}
