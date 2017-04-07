import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by taylo on 4/4/2017.
 */
public class TigerServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String inputLine, outputLine;

            // Initiate conversation with client
            TigerServerProtocols kkp = new TigerServerProtocols(serverSocket, in, out);
            out.println("WELCOME TO ANOTHER EDITION OF THE THUNDERDOME!");
            outputLine = kkp.authenticate(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.authenticate(inputLine);
                out.println(outputLine);
                for (int round = 0; round < 3; round++) {
                    outputLine = kkp.challenge(23, false, true);
                    out.println(outputLine);





                    outputLine = kkp.challenge(23, round == 2, false);
                    out.println(outputLine);
                }



                out.println("THANK YOU FOR PLAYING! GOODBYE");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
