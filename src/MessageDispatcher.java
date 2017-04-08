import com.sun.corba.se.spi.activation.Server;

import java.util.ArrayList;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public class MessageDispatcher {
    private ServerProtocol[] protocols;

    public MessageDispatcher(ServerProtocol... protocols) {
        this.protocols = protocols;
    }

    public String processInput(String input) {
        for (ServerProtocol protocol : protocols) {
            if (protocol.messageAppliesToProtocol(input)) {
                return protocol.responseForMessage(input);
            }
        }
        return null;
    }
}