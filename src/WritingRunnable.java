public class WritingRunnable implements Runnable {
    private MessageDispatcher messageDispatcher;

    private ReaderWriter readerWriter;
    private  String messageFromServer;

    public WritingRunnable(ReaderWriter socketReaderWriter, ServerProtocol[] protocols, String messageFromServer) {
        this.readerWriter = socketReaderWriter;
        this.messageFromServer = messageFromServer;
        this.messageDispatcher = new MessageDispatcher(protocols);
    }

    public void run() {
        System.out.println("SERVER: " + messageFromServer);
        String response = messageDispatcher.processInput(messageFromServer);
        if (response != null) {
            readerWriter.println(response);
            System.out.println("CLIENT: " + response);
        }
    }
}