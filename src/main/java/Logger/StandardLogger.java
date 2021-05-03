package Logger;

/**
 * Ce logger logge uniquement les erreurs
 */
public class StandardLogger implements ILogger {
    /**
     * Indique que l'application souhoite logger un message qui a été envoyé au serveur
     *
     * @param message Le message
     */
    @Override
    public void LogSendMessage(String message) {
    }

    /**
     * Indique que l'application souhoite logger un message qui a été recu du serveur
     *
     * @param message Le message
     */
    @Override
    public void LogReceivedMessage(String message) {
    }

    /**
     * Indique que l'application souhoite logger une erreur
     *
     * @param message L'erreur
     */
    @Override
    public void LogError(String message) {
        System.out.print("ERROR : " + message);
    }
}