package SMTP;

import Prank.App;
import Stream.SocketReaderWriter;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Cette classe représente une connexion a un serveur SMTP
 */
public class SMTPServerConnexion implements AutoCloseable {
    /**
     * Le socket par lequel passe les données
     */
    private final SocketReaderWriter srw;

    /**
     * L'utilisateur connecté au serveur SMTP
     */
    private final String user;

    /**
     * Constructeur de la classe SMTPServerConnexion
     *
     * @param servConfig La configuration du serveur sur lequel on souhaite se connecter
     * @throws IOException En cas de problème lors de la création du socket ou lors de la connexion au serveur
     */
    public SMTPServerConnexion(SMTPConnexionConfiguration servConfig) throws IOException {
        srw = new SocketReaderWriter(servConfig.getHost(), servConfig.getPort(), 1000);

        srw.readLine();
        srw.writeLine("EHLO " + servConfig.getSourceMailService());
        String line = "";
        try {
            while (true) {
                line = srw.readLine();
            }
        } catch (SocketTimeoutException ignored) {
        }

        if (!line.startsWith("250")) {
            throw new IOException(line);
        }
        user = servConfig.getSourceMailService();
    }

    /**
     * Envoie un mail
     *
     * @param mailInformation Les informations du mail a envoyer
     * @return True si le mail a été envoyé, false sinon
     */
    public boolean sendMail(SMTPMailInformation mailInformation) {
        try {
            srw.writeLine("MAIL FROM:<" + user + ">");
            String line = srw.readLine();
            if (!line.startsWith("250")) {
                App.getLogger().LogError("Erreur dans l'envoi du mail : " + line);
                reset();
                return false;
            }

            srw.writeLine("RCPT TO:<" + mailInformation.getTarget() + ">");
            line = srw.readLine();
            if (!line.startsWith("250")) {
                App.getLogger().LogError("Erreur dans l'envoi du mail : " + line);
                reset();
                return false;
            }

            srw.writeLine("DATA");
            line = srw.readLine();
            if (!line.startsWith("354")) {
                App.getLogger().LogError("Erreur dans l'envoi du mail : " + line);
                reset();
                return false;
            }

            srw.writeLine("From: " + mailInformation.getFakeSource());
            srw.writeLine("To: " + mailInformation.getTarget());
            srw.writeLine("Subject: =?utf-8?B?" +
                    Base64.getEncoder().encodeToString(mailInformation.getSubject().getBytes(StandardCharsets.UTF_8))
                    + "?=");
            srw.writeLine("Content-Type: text/plain; charset=utf-8");
            srw.writeLine("");
            srw.writeLine(mailInformation.getContent());
            srw.writeLine(".");
            line = srw.readLine();
            if (!line.startsWith("250")) {
                App.getLogger().LogError("Erreur dans l'envoi du mail : " + line);
                reset();
                return false;
            }

        } catch (IOException exception) {
            App.getLogger().LogError("Erreur dans l'envoi du mail : " + exception.getLocalizedMessage());
            reset();
            return false;
        }
        return true;
    }

    /**
     * Réinitialise l'envoi du mail en cours, a utiliser en cas d'erreur ou après un envoi réussi de mail
     */
    private void reset() {

        try {
            srw.writeLine("RSET");
            String line = srw.readLine();
            if (!line.startsWith("250")) {
                App.getLogger().LogError(
                        "Erreur dans la reinitialisation du serveur mail, veuillez relancer l'application : \r\n"
                                + line);
                close();
            }
        } catch (IOException exception) {
            App.getLogger().LogError(
                    "Erreur dans la reinitialisation du serveur mail, veuillez relancer l'application : \r\n"
                            + exception.getLocalizedMessage());
            close();
        }
    }

    /**
     * Ferme la connexion au serveur SMTP
     */
    @Override
    public void close() {
        try {
            srw.writeLine("quit");
            String line = srw.readLine();
            if (!line.startsWith("221")) {
                App.getLogger().LogError("Erreur dans la fermeture de la connexion, veuillez relancer l'application :"
                        + line);
            }
            srw.close();
        } catch (IOException exception) {
            App.getLogger().LogError("Erreur dans la fermeture de la connexion, veuillez relancer l'application : \r\n"
                    + exception.getLocalizedMessage());
        }
    }
}