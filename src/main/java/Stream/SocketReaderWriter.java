package Stream;

import Prank.App;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Classe permettant de lire et d'ecrire des lignes dans un socket
 */
public class SocketReaderWriter implements AutoCloseable {
    // Le champs intemediaires sont conserves pour pouvoir les fermer dans le close
    private final Socket s;
    private final InputStreamReader isr;
    private final BufferedInputStream bis;
    private final PrintStream ps;

    /**
     * Constructeur de la classe SocketReaderWriter
     *
     * @param host     L'addresse cible du socket
     * @param port     Le port cible du socket
     * @param waitTime Le temps au bout duquel il faut interrompre la lecture
     * @throws IOException En cas de problème pour ouvir le socket
     */
    public SocketReaderWriter(String host, int port, int waitTime) throws IOException {
        s = new Socket(host, port);
        s.setSoTimeout(waitTime);
        bis = new BufferedInputStream(s.getInputStream());
        isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        ps = new PrintStream(s.getOutputStream(), true, StandardCharsets.UTF_8);
    }

    /**
     * Lit une ligne depuis le socket
     *
     * @return La ligne qui est lue
     * @throws IOException                     En cas de problème pour lire le socket
     * @throws java.net.SocketTimeoutException Si on arrive pas a lire une ligne après le tempos donné
     */
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

    /**
     * Ecris une ligne dans le socket
     *
     * @param s La ligne a écrire
     */
    public void writeLine(String s) {
        if (s.endsWith("\r\n")) {
            App.getLogger().LogSendMessage(s);
            ps.print(s);
        } else {
            App.getLogger().LogSendMessage(s + "\r\n");
            ps.println(s);
        }
    }

    /**
     * Ferme le socket et tout les stream et les readers
     *
     * @throws IOException En cas de problème pour fermer l'un des éléments
     */
    @Override
    public void close() throws IOException {
        s.close();
        bis.close();
        isr.close();
        ps.close();
    }
}