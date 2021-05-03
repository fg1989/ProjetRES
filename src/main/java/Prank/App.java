package Prank;

import Logger.DebugLogger;
import Logger.ILogger;
import Logger.StandardLogger;
import SMTP.SMTPConnexionConfiguration;
import SMTP.SMTPMailInformation;
import SMTP.SMTPServerConnexion;

import java.io.IOException;

public class App {
    private static ILogger logger = new StandardLogger();

    public static void main(String[] args) {
        setLogger(new DebugLogger());
        SMTPConnexionConfiguration connexionConfiguration = new SMTPConnexionConfiguration("localhost", 25, "prank.com");

        try (SMTPServerConnexion serverConnexion = new SMTPServerConnexion(connexionConfiguration)) {
            SMTPMailInformation mailInformation = new SMTPMailInformation("charlie@criminel.com", "alice@victime.com", "bob@victime.com", "Vous avez gagné un concours", "Bravo pour votre victoire, vous avez gagné");
            serverConnexion.sendMail(mailInformation);
        } catch (IOException e) {
            getLogger().LogError("Erreur dans la connexion au serveur : " + e.getLocalizedMessage());
        }
    }

    public static ILogger getLogger() {
        return logger;
    }

    public static void setLogger(ILogger logger) {
        App.logger = logger;
    }
}