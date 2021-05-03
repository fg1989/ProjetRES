package Logger;

/**
 * Représente un logger
 */
public interface ILogger {
    /**
     * Indique que l'application souhoite logger un message qui a été envoyé au serveur
     *
     * @param message Le message
     */
    void LogSendMessage(String message);

    /**
     * Indique que l'application souhoite logger un message qui a été recu du serveur
     *
     * @param message Le message
     */
    void LogReceivedMessage(String message);

    /**
     * Indique que l'application souhoite logger une erreur
     *
     * @param message L'erreur
     */
    void LogError(String message);
}