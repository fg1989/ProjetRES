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

public class ConfigurationHelper {
    private static List<String> adresses;
    private static int numberOfGroups;
    private static int numberOfTargetsInGroups;
    private static List<Message> messages;
    private static int portNumber;
    private static String targetHost;
    private static String userName;

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


    public static List<String> getTargetAdresses() {
        return adresses;
    }

    public static List<Message> getMessages() {
        return messages;
    }

    public static int getNumberOfGroups() {
        return numberOfGroups;
    }

    public static int getNumberOfTargetInGroup() {
        return numberOfTargetsInGroups;
    }

    public static String getTargetHost() {
        return targetHost;
    }

    public static String getUserName() {
        return userName;
    }

    public static int getPortNumber() {
        return portNumber;
    }
}