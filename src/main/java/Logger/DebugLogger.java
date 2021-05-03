package Logger;

public class DebugLogger implements ILogger {
    @Override
    public void LogSendMessage(String message) {
        System.out.print("SEND     : " + message);
    }

    @Override
    public void LogReceivedMessage(String message) {
        System.out.print("RECEIVED : " + message);
    }

    @Override
    public void LogError(String message) {
        System.out.print("ERROR : " + message);
    }
}