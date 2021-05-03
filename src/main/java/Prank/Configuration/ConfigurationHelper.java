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

    public static void ReadConfiguration() throws IOException, JDOMException {
        adresses = Files.readAllLines(Paths.get("adresses.config"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File("configuration.config"));
        numberOfGroups = Integer.parseInt(doc.getRootElement().getChild("NumberOfGroups").getText());
        numberOfTargetsInGroups = Integer.parseInt(doc.getRootElement().getChild("NumberOfTargetsInGroups").getText());
        messages = new ArrayList<>();
        for (Element message : doc.getRootElement().getChild("Messages").getChildren()) {
            messages.add(new Message(message.getChild("Subject").getText(), message.getChild("Content").getText()));
        }
    }

    public static List<String> readConfiguration() {
        return adresses;
    }

    public static List<Message> readMessages() {
        return messages;
    }

    public static int readNumberOfGroups() {
        return numberOfGroups;
    }

    public static int readNumberOfTargetInGroup() {
        return numberOfTargetsInGroups;
    }
}