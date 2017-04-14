public interface ServerProtocol {
    boolean messageAppliesToProtocol(String str);
    String responseForMessage(String str);
}