package Prank;

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
    /**
     * Le generateur de nombre aleatoire de l'application
     */
    private static final Random r = new Random();

    /**
     * Le logger utilise par l'application
     */
    private static ILogger logger = new StandardLogger();

    /**
     * La methode principale de l'application
     *
     * @param args
     */
    public static void main(String[] args) {
        //Activer pour passer en mode debug
        //setLogger(new DebugLogger());

        try {
            ConfigurationHelper.ReadConfiguration();
        } catch (IOException | JDOMException | NumberFormatException exception) {
            getLogger().LogError("Erreur dans la lecture de la configuration : " + exception.getLocalizedMessage());
            return;
        }

        SMTPConnexionConfiguration connexionConfiguration =
                new SMTPConnexionConfiguration(
                        ConfigurationHelper.getTargetHost(),
                        ConfigurationHelper.getPortNumber(),
                        ConfigurationHelper.getUserName());

        try (SMTPServerConnexion serverConnexion = new SMTPServerConnexion(connexionConfiguration)) {
            for (int i = 0; i < ConfigurationHelper.getNumberOfGroups(); i++) {
                List<String> adresses = new ArrayList<>(ConfigurationHelper.getTargetAdresses());
                String sender = removeRandomElemInList(adresses);
                Message message =
                        ConfigurationHelper.getMessages().get(r.nextInt(ConfigurationHelper.getMessages().size()));
                for (int j = 0; j < ConfigurationHelper.getNumberOfTargetInGroup(); j++) {
                    SMTPMailInformation mailInformation = new SMTPMailInformation(
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

    /**
     * Recupère le logger courant de l'application
     *
     * @return Le logger courant de l'application
     */
    public static ILogger getLogger() {
        return logger;
    }

    /**
     * Change le logger courant de l'application
     *
     * @param logger Le nouveau logger
     */
    public static void setLogger(ILogger logger) {
        App.logger = logger;
    }

    /**
     * Methode helper qui enlève un élément au hasard d'une liste
     *
     * @param list La liste dont on souhaite enlever l'élément
     * @return L'élément qui a été enlevé
     */
    public static String removeRandomElemInList(List<String> list) {
        int index = r.nextInt(list.size());
        String value = list.get(index);
        list.remove(index);
        return value;
    }
}