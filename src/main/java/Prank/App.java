package Prank;

import Logger.DebugLogger;
import Logger.ILogger;
import Logger.StandardLogger;
import Prank.Configuration.ConfigurationHelper;
import Prank.Configuration.Message;
import SMTP.SMTPConnexionConfiguration;
import SMTP.SMTPMailInformation;
import SMTP.SMTPServerConnexion;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    private static final Random r = new Random();
    private static ILogger logger = new StandardLogger();

    public static void main(String[] args) {
        setLogger(new DebugLogger());
        try {
            ConfigurationHelper.ReadConfiguration();
        } catch (IOException | JDOMException | NumberFormatException exception) {
            getLogger().LogError("Erreur dans la lecture de la configuration : " + exception.getLocalizedMessage());
            return;
        }
        SMTPConnexionConfiguration connexionConfiguration =
                new SMTPConnexionConfiguration("localhost", 25, "prank.com");
        try (SMTPServerConnexion serverConnexion = new SMTPServerConnexion(connexionConfiguration)) {
            for (int i = 0; i < ConfigurationHelper.readNumberOfGroups(); i++) {
                List<String> adresses = new ArrayList<>(ConfigurationHelper.readConfiguration());
                String sender = removeRandomElemInList(adresses);
                Message message =
                        ConfigurationHelper.readMessages().get(r.nextInt(ConfigurationHelper.readMessages().size()));
                for (int j = 0; j < ConfigurationHelper.readNumberOfTargetInGroup(); j++) {
                    SMTPMailInformation mailInformation = new SMTPMailInformation(
                            "prank@prank.com",
                            sender,
                            removeRandomElemInList(adresses),
                            message.getHeader(),
                            message.getContent());
                    serverConnexion.sendMail(mailInformation);
                }
            }
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

    public static String removeRandomElemInList(List<String> list) {
        int index = r.nextInt(list.size());
        String value = list.get(index);
        list.remove(index);
        return value;
    }
}