import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by gonzalonunez on 4/7/17.
 */

class AuthenticatorReaderWriter implements ReaderWriter {
        private String[] messages;
        private int idx;

        public AuthenticatorReaderWriter() {
            messages = new String[] {
                    "WELCOME TO ANOTHER EDITION OF THUNDERDOME!",
                    "TWO SHALL ENTER, ONE SHALL LEAVE",
                    "WAIT FOR THE TOURNAMENT TO BEGIN 1"
            };
        }

        public String readLine() throws IOException {
            return messages[idx];
        }

        public void println(String str) {
            String[] split = str.split("\\s+");
            switch (idx) {
                case 0:
                    Assert.assertTrue(split.length == 3);
                    Assert.assertTrue(split[0].equals("ENTER"));
                    Assert.assertTrue(split[1].equals("THUNDERDOME"));
                    break;
                case 1:
                    Assert.assertTrue(split.length == 4);
                    Assert.assertTrue(split[0].equals("I"));
                    Assert.assertTrue(split[1].equals("AM"));
                    break;
                default:
                    Assert.assertTrue(false); // wtf are we even responding to?
            }
            idx++;
        }
}

class ChallengeAndRoundReaderWriter implements ReaderWriter {
    private String[] messages;
    private int idx;

    public int roundCount;

    public ChallengeAndRoundReaderWriter(int count) {
        roundCount = count;

        messages = new String[] {
                "NEW CHALLENGE 1 YOU WILL PLAY " + roundCount + " MATCHES",
                "END OF CHALLENGES"
        };
    }

    public String readLine() throws IOException {
        return messages[idx++];
    }

    public void println(String str) {
        Assert.assertTrue(false); // wtf are we even responding to?
    }
}

class MatchStartReaderWriter implements ReaderWriter {
    private String[] messages;
    private int idx;

    public String opponentPID;

    public MatchStartReaderWriter(String pid) {
        opponentPID = pid;
        messages = new String[] { "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + opponentPID };
    }

    public String readLine() throws IOException {
        if (idx > 0) {
            Assert.assertTrue(false); // why are we reading twice?
        }
        return messages[idx++];
    }

    public void println(String str) {
        Assert.assertTrue(false); // wtf are we even responding to?
    }
}

public class TigerClientProtocolTests {
    @Test
    public void testAuthenticationProtocol() {
        AuthenticatorReaderWriter readerWriter = new AuthenticatorReaderWriter();
        TigerClientProtocols protocols = new TigerClientProtocols(readerWriter);
        try {
            String pid = protocols.authenticator("username", "password", "password");
            Assert.assertTrue("1".equals(pid));
        } catch (IOException e) {
            Assert.assertTrue(false); // we fucked up
        }
    }

    @Test
    public void testChallengeAndRoundProtocol() {
        int roundCount = 5;

        ChallengeAndRoundReaderWriter readerWriter = new ChallengeAndRoundReaderWriter(roundCount);
        TigerClientProtocols protocols = new TigerClientProtocols(readerWriter);
        try {
            int rounds = protocols.challengeProtocol();
            Assert.assertEquals(roundCount, rounds);

            int end = protocols.challengeProtocol();
            Assert.assertEquals(-1, end);
        } catch (IOException e) {
            Assert.assertTrue(false); // we fucked up
        }
    }

    @Test
    public void testMatchStartProtocol() {
        String expectedPID = "5";

        MatchStartReaderWriter readerWriter = new MatchStartReaderWriter(expectedPID);
        TigerClientProtocols protocols = new TigerClientProtocols(readerWriter);
        try {
            String pid = protocols.matchStartProtocol();
            Assert.assertEquals(expectedPID, pid);
        } catch (IOException e) {
            Assert.assertTrue(false); // we fucked up
        }
    }
}