package SMTP;

/**
 * Représente les informations contenues dans un mail
 */
public class SMTPMailInformation {
    /**
     * L'auteur du mail
     */
    private final String fakeSource;

    /**
     * Le destinataire du mail
     */
    private final String target;

    /**
     * Le sujet du mail
     */
    private final String subject;

    /**
     * Le contenu du mail
     */
    private final String content;

    /**
     * Constructeur de la classe SMTPMailInformation
     *
     * @param fakeSource L'auteur du mail
     * @param target     Le destinataire du mail
     * @param subject    Le sujet du mail
     * @param content    Le contenu du mail
     */
    public SMTPMailInformation(String fakeSource, String target, String subject, String content) {
        this.target = target;
        this.fakeSource = fakeSource;
        this.subject = subject;
        this.content = content;
    }

    /**
     * Récupére l'auteur du mail
     *
     * @return L'auteur du mail
     */
    public String getFakeSource() {
        return fakeSource;
    }

    /**
     * Récupére le destinataire du mail
     *
     * @return Le destinataire du mail
     */
    public String getTarget() {
        return target;
    }

    /**
     * Récupére le sujet du mail
     *
     * @return Le sujet du mail
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Récupére le contenu du mail
     *
     * @return Le contenu du mail
     */
    public String getContent() {
        return content;
    }
}