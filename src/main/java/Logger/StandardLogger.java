package Logger;

public class StandardLogger implements ILogger {
    @Override
    public void LogSendMessage(String message) {

    }

    @Override
    public void LogReceivedMessage(String message) {

    }

    @Override
    public void LogError(String message) {
        System.out.print("ERROR : " + message);
    }
}