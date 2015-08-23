package communication.backend.client;

import communication.backend.utilities.SPacket;
import communication.frontend.utilities.SAction;

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
