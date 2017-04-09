import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by gonzalonunez on 4/8/17.
 */
public class ServerInteractionTests {

    class MockReaderWriter implements ReaderWriter {
        int idx = 0;
        int moveNumber;

        public MockReaderWriter(int moveNumber) {
            this.moveNumber = moveNumber;
        }

        public String readLine() throws IOException {
            String[] messages = new String[] {
                    "GAME A MOVE 1 PLAYER 0375 PLACED ROCKY+LAKE AT -1 1 0 5 FOUNDED SETTLEMENT AT -2 2 0",
                    "MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECOND: MOVE 2 PLACE LAKE+LAKE"
            };
            return idx < messages.length ? messages[idx++] : null;
        }

        public void println(String str) {
            String[] split = str.split("\\s+");
            Assert.assertEquals(Integer.parseInt(split[3]), moveNumber+1);
        }
    }


    @Test
    public void testMoveNumberValidity() {
        TournamentDriver driver = new TournamentDriver(new Tournament("1"));
        final ServerProtocol[] protocols = TigerIslandClient.serverProtocols(driver, "u", "p", "t");

        MockReaderWriter readerWriter = new MockReaderWriter(1);

        try {
            String messageFromServer;
            while ((messageFromServer = readerWriter.readLine()) != null) {
                Thread thread = new Thread(new WritingRunnable(readerWriter, protocols, messageFromServer));
                thread.start();
            }
        } catch (IOException e) { }
    }
}
