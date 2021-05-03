package SMTP;

import Prank.App;
import Stream.SocketReaderWriter;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SMTPServerConnexion implements AutoCloseable {
    private final SocketReaderWriter srw;

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
    }

    public boolean sendMail(SMTPMailInformation mailInformation) {
        try {
            srw.writeLine("MAIL FROM:<" + mailInformation.getRealSource() + ">");
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

    @Override
    public void close() {
        try {
            srw.close();
        } catch (IOException exception) {
            App.getLogger().LogError("Erreur dans la fermeture de la connexion, veuillez relancer l'application : \r\n"
                    + exception.getLocalizedMessage());
        }
    }
}