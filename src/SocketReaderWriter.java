import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketReaderWriter implements ReaderWriter {
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public SocketReaderWriter(String hostName, int portNumber) throws IOException {
        socket = new Socket(hostName, portNumber);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void println(String str) {
        writer.println(str);
    }
}