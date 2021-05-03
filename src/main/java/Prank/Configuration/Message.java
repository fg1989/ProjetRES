package Prank.Configuration;

public class Message {
    private final String Header;
    private final String Content;

    public Message(String header, String content) {
        Header = header;
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public String getHeader() {
        return Header;
    }
}