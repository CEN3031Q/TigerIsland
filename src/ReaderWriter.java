import java.io.IOException;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public interface ReaderWriter {
    public String readLine() throws IOException;
    public void println(String str);
}