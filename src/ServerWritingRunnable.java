/**
 * Created by gonzalonunez on 4/8/17.
 */
public class ServerWritingRunnable implements Runnable {
    private MessageDispatcher messageDispatcher;

    private SocketReaderWriter socketReaderWriter;
    private  String messageFromServer;

    public ServerWritingRunnable(SocketReaderWriter socketReaderWriter, ServerProtocol[] protocols, String messageFromServer) {
        this.socketReaderWriter = socketReaderWriter;
        this.messageFromServer = messageFromServer;
        this.messageDispatcher = new MessageDispatcher(protocols);
    }

    public void run() {
        String response = messageDispatcher.processInput(messageFromServer);
        if (response != null) {
            socketReaderWriter.println(response);
        }
    }
}