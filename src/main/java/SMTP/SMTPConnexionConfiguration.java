package SMTP;

public class SMTPConnexionConfiguration {
    private final String host;
    private final int port;
    private final String sourceMailService;

    public SMTPConnexionConfiguration(String host, int port, String sourceMailService) {
        this.host = host;
        this.port = port;
        this.sourceMailService = sourceMailService;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getSourceMailService() {
        return sourceMailService;
    }
}