package Prank.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationHelper {
    public static void ReadConfiguration() {
    }

    public static List<String> readConfiguration() {
        List<String> list = new ArrayList<>();
        list.add("alice@victime.com");
        list.add("bob@victime.com");
        list.add("paul@victime.com");
        list.add("jean@victime.com");
        return list;
    }

    public static List<Message> readMessages() {
        List<Message> list = new ArrayList<>();
        list.add(new Message("Vous avez gagné", "Bravo, vous avez gagné un concours\r\nVenez chez nous pour le récupérer"));
        list.add(new Message("Rendez vous urgent", "Demain, je dois absolument te voir pour parler de quelque chose d'important"));
        return list;
    }

    public static int readNumberOfGroups() {
        return 2;
    }

    public static int readNumberOfTargetInGroup() {
        return 2;
    }
}