import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by taylo on 4/3/2017.
 */
public class TigerIslandClient {
    public static ServerProtocol[] serverProtocols(ServerProtocolInfoCommunicator comm,
                                                    String username,
                                                    String password,
                                                    String tournamentPassword) {
        return new ServerProtocol[] {
                new AuthenticationProtocol(comm, username, password, tournamentPassword),
                new ChallengeProtocol(comm),
                new RoundProtocol(comm),
                new MatchProtocol(comm),
                new MoveProtocol(comm)
        };
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.err.println("Usage: java TigerIslandClient <host name> <port number> <username> <password> <tournament password>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        String username = args[2];
        String password = args[3];
        String tournamentPassword = args[4];

        TournamentDriver driver = new TournamentDriver();

        try {
            final ServerProtocol[] protocols = TigerIslandClient.serverProtocols(driver, username, password, tournamentPassword);
            final SocketReaderWriter socketReaderWriter = new SocketReaderWriter(hostName, portNumber);

            String messageFromServer;
            while ((messageFromServer = socketReaderWriter.readLine()) != null) {
                Thread thread = new Thread(new WritingRunnable(socketReaderWriter, protocols, messageFromServer));
                thread.start();
            }
        } catch (UnknownHostException e) {
            System.err.println("[ERROR] Unknown host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to connect to host: " + hostName);
            System.exit(1);
        }
    }
}