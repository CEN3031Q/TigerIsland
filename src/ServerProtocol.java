import com.sun.corba.se.spi.activation.Server;

/**
 * Created by gonzalonunez on 4/7/17.
 */
public interface ServerProtocol {
    boolean messageAppliesToProtocol(String str);
    String responseForMessage(String str);
}