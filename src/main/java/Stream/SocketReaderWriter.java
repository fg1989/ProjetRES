package Stream;

import Prank.App;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketReaderWriter implements AutoCloseable {
    private final Socket s;
    private final InputStreamReader isr;
    private final BufferedInputStream bis;
    private final PrintStream ps;

    public SocketReaderWriter(String host, int port, int waitTime) throws IOException {
        s = new Socket(host, port);
        s.setSoTimeout(waitTime);
        bis = new BufferedInputStream(s.getInputStream());
        isr = new InputStreamReader(bis);
        ps = new PrintStream(s.getOutputStream());
    }

    public String readLine() throws IOException {
        StringBuilder result = new StringBuilder();
        boolean stop = false;
        while (true) {
            int character = isr.read();
            if (character != -1) {
                result.append((char) character);
                if ((char) character == '\r') {
                    stop = true;
                } else if ((char) character == '\n' && stop) {
                    App.getLogger().LogReceivedMessage(result.toString());
                    return result.toString();
                } else {
                    stop = false;
                }
            }
        }
    }

    public void writeLine(String s) {
        if (s.endsWith("\r\n")) {
            App.getLogger().LogSendMessage(s);
            ps.print(s);
        } else {
            App.getLogger().LogSendMessage(s + "\r\n");
            ps.println(s);
        }
    }

    @Override
    public void close() throws IOException {
        s.close();
        bis.close();
        isr.close();
        ps.close();
    }
}