package Logger;

public interface ILogger {
    void LogSendMessage(String message);

    void LogReceivedMessage(String message);

    void LogError(String message);
}