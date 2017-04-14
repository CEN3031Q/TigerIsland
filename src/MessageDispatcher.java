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