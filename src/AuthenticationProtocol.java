import java.util.HashMap;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public class AuthenticationProtocol implements ServerProtocol {
    private String username;
    private String password;
    private String tournamentPassword;

    ServerProtocolInfoCommunicator communicator;

    public AuthenticationProtocol(ServerProtocolInfoCommunicator communicator, String username, String password, String tournamentPassword) {
        this.username = username;
        this.password = password;
        this.tournamentPassword = tournamentPassword;

        this.communicator = communicator;
    }

    public boolean messageAppliesToProtocol(String str) {
        return str.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!") ||
                str.equals("TWO SHALL ENTER, ONE SHALL LEAVE") ||
                str.startsWith("WAIT FOR THE TOURNAMENT TO BEGIN");
    }

    public String responseForMessage(String str) {
        if (str.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")) {
            return "ENTER THUNDERDOME " + tournamentPassword;
        } else if (str.equals("TWO SHALL ENTER, ONE SHALL LEAVE")) {
            return "I AM " + username + " " + password;
        } else if (str.startsWith("WAIT FOR THE TOURNAMENT TO BEGIN")) {
            String[] split = str.split("\\s+");
            if (split.length > 6) {
                HashMap<String, String> info = new HashMap<>();

                info.put("pid", split[6]);

                communicator.receiveInfo(this, str, info);
            }
        }
        return null;
    }
}