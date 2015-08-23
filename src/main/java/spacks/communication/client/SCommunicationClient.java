package spacks.communication.client;

import spacks.communication.utilities.SPacket;
import spacks.communication.utilities.SAction;

import java.io.IOException;

/**
 * Communication class on client side.
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public interface SCommunicationClient {

    void connect(String ip, int port) throws IOException;

    void send(SAction action) throws IOException;

    SPacket receive() throws IOException, ClassNotFoundException;

    void start();

    void stop();

    Boolean isRunning();
}
