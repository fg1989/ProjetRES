package SMTP;

/**
 * Classe contenant les informations permettant de mettre en place une connexion vers un serveur SMTP
 */
public class SMTPConnexionConfiguration {
    /**
     * L'adresse du serveur SMTP
     */
    private final String host;

    /**
     * Le numéro de port du serveur SMTP
     */
    private final int port;

    /**
     * Le nom d'utilisateur pour se connecter au serveur SMTP
     */
    private final String sourceMailService;

    /**
     * Constructeur de la classe SMTPConnexionConfiguration
     *
     * @param host              L'adresse du serveur SMTP
     * @param port              Le numéro de port du serveur SMTP
     * @param sourceMailService Le nom d'utilisateur pour se connecter au serveur SMTP
     */
    public SMTPConnexionConfiguration(String host, int port, String sourceMailService) {
        this.host = host;
        this.port = port;
        this.sourceMailService = sourceMailService;
    }

    /**
     * Récupére l'adresse du serveur SMTP
     *
     * @return L'adresse du serveur SMTP
     */
    public String getHost() {
        return host;
    }

    /**
     * Récupère le numéro de port du serveur SMTP
     *
     * @return Le numéro de port du serveur SMTP
     */
    public int getPort() {
        return port;
    }

    /**
     * Récupère le nom d'utilisateur pour se connecter au serveur SMTP
     *
     * @return Le nom d'utilisateur pour se connecter au serveur SMTP
     */
    public String getSourceMailService() {
        return sourceMailService;
    }
}