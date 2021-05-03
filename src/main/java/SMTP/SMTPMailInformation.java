package SMTP;

public class SMTPMailInformation {
    private final String realSource;
    private final String fakeSource;
    private final String target;
    private final String subject;
    private final String content;

    public SMTPMailInformation(String realSource, String fakeSource, String target, String subject, String content) {
        this.realSource = realSource;
        this.target = target;
        this.fakeSource = fakeSource;
        this.subject = subject;
        this.content = content;
    }

    public String getRealSource() {
        return realSource;
    }

    public String getFakeSource() {
        return fakeSource;
    }

    public String getTarget() {
        return target;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}