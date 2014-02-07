package server;

import java.net.Socket;
import utilities.communication.Packet;

/**
 *
 * @author plach_000
 */
public interface Performable {

    public void perform(Socket socket, Packet packet);
}
