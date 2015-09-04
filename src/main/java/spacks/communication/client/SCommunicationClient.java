package spacks.communication.client;

import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SPacket;

import java.io.IOException;

/**
 * Communication class on client side.
 *
 * @author Stepan Plachy
 * @author Vaclav Blazej
 */
public interface SCommunicationClient {

    void connect(String ip, int port) throws IOException;

    void send(SAction action) throws IOException;

    SPacket receive() throws IOException, ClassNotFoundException;

    /**
     * Starts listening on connected socket for a packets sent from server.
     * This method also executes all packet code.
     */
    void start();

    void stop();

    Boolean isRunning();
}
