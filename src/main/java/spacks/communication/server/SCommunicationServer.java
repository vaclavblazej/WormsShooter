package spacks.communication.server;

import spacks.communication.utilities.SAction;

import java.io.IOException;

/**
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public interface SCommunicationServer {

    boolean start();

    void stop();

    void send(int id, SAction action) throws IOException;

    void broadcast(SAction action) throws IOException;

    Boolean isRunning();

    int getNumberOfConnections();

    void disconnectClient(int id) throws IOException;
}
