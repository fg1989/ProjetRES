package Prank.Configuration;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de lire et de récupérer les données de configuration
 */
public class ConfigurationHelper {
    /**
     * La liste des adresses des victimes
     */
    private static List<String> adresses;

    /**
     * Le nombre de groupe a qui on envoie des mails
     */
    private static int numberOfGroups;

    /**
     * Le nombre de personnes qui vont recevoir le mail par groupe
     */
    private static int numberOfTargetsInGroups;

    /**
     * La liste des messages qui peuvent être envoyés
     */
    private static List<Message> messages;

    /**
     * Le numéro de port du serveur SMTP utilisé
     */
    private static int portNumber;

    /**
     * L'adresse du serveur SMTP utilisé
     */
    private static String targetHost;

    /**
     * Le nom d'utilisateur utilisé pour se connecter au serveur SMTP
     */
    private static String userName;

    /**
     * Lit la configuration depuis les fichiers de configuration
     *
     * @throws IOException   En cas de problème de lecture d'un des fichiers de configuration
     * @throws JDOMException En cas de problème de lecture d'un des fichiers de configuration
     */
    public static void ReadConfiguration() throws IOException, JDOMException {
        adresses = Files.readAllLines(Paths.get("adresses.config"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File("configuration.config"));
        numberOfGroups = Integer.parseInt(doc.getRootElement().getChild("NumberOfGroups").getText());
        numberOfTargetsInGroups = Integer.parseInt(doc.getRootElement().getChild("NumberOfTargetsInGroups").getText());
        portNumber = Integer.parseInt(doc.getRootElement().getChild("targetMailServerPort").getText());
        targetHost = doc.getRootElement().getChild("targetMailServer").getText();
        userName = doc.getRootElement().getChild("realUserName").getText();
        messages = new ArrayList<>();
        for (Element message : doc.getRootElement().getChild("Messages").getChildren()) {
            messages.add(new Message(message.getChild("Subject").getText(), message.getChild("Content").getText()));
        }
    }

    /**
     * Récupère la liste des adresses des victimes
     *
     * @return La liste des adresses des victimes
     */
    public static List<String> getTargetAdresses() {
        return adresses;
    }

    /**
     * Récupère la liste des messages
     *
     * @return La liste des messages
     */
    public static List<Message> getMessages() {
        return messages;
    }

    /**
     * Récupère le nombre de groupe a qui on envoie des mails
     *
     * @return Le nombre de groupe a qui on envoie des mails
     */
    public static int getNumberOfGroups() {
        return numberOfGroups;
    }

    /**
     * Récupère le nombre de personnes qui vont recevoir le mail par groupe
     *
     * @return Le nombre de personnes qui vont recevoir le mail par groupe
     */
    public static int getNumberOfTargetInGroup() {
        return numberOfTargetsInGroups;
    }

    /**
     * Récupère l'adresse du serveur SMTP utilisé
     *
     * @return L'adresse du serveur SMTP utilisé
     */
    public static String getTargetHost() {
        return targetHost;
    }

    /**
     * Récupère le nom d'utilisateur utilisé pour se connecter au serveur SMTP
     *
     * @return Le nom d'utilisateur utilisé pour se connecter au serveur SMTP
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Récupère le numéro de port du serveur SMTP utilisé
     *
     * @return Le numéro de port du serveur SMTP utilisé
     */
    public static int getPortNumber() {
        return portNumber;
    }
}