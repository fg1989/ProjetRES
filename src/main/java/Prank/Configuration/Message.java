package Prank.Configuration;

/**
 * Represente un message qui peut etre envoyé
 */
public class Message {
    /**
     * Le sujet du message
     */
    private final String Header;

    /**
     * Le contenu du message
     */
    private final String Content;

    /**
     * Constructeur de la Message
     *
     * @param header  Le sujet du message
     * @param content Le contenu du message
     */
    public Message(String header, String content) {
        Header = header;
        Content = content;
    }

    /**
     * Récupère le contenu du message
     *
     * @return Le contenu du message
     */
    public String getContent() {
        return Content;
    }

    /**
     * Récupère le sujet du message
     *
     * @return Le sujet du message
     */
    public String getHeader() {
        return Header;
    }
}