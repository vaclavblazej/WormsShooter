package spacks.communication.server.impl;


import spacks.communication.server.SCommunicationServer;
import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Štìpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationServerImpl implements SCommunicationServer {

    private static final Logger logger = Logger.getLogger(SCommunicationServer.class.getName());

    private SCommunicationServerCreateService connectionService;
    private Map<Integer, SCommunicationClientHandler> connections;
    private SListener sListener;

    public SCommunicationServerImpl(int port, SListener sListener) throws IOException {
        connections = new HashMap<>(20);
        connectionService = new SCommunicationServerCreateService(port, connections, sListener);
        this.sListener = sListener;
    }

    @Override
    public boolean start() {
        return connectionService.start();
    }

    @Override
    public void stop() {
        connectionService.stop();
    }

    @Override
    public void send(int id, SAction action) throws IOException {
        final SCommunicationClientHandler handler = connections.get(id);
        if (handler != null) {
            handler.sendAsynchronous(action);
        } else {
            throw new RuntimeException("Asking for handler for client with invalid id=" + id);
        }
    }

    @Override
    public void broadcast(SAction action) throws IOException {
        for (Integer connection : connections.keySet()) {
            send(connection, action);
        }
    }

    @Override
    public Boolean isRunning() {
        return connectionService.isRunning();
    }

    @Override
    public int getNumberOfConnections() {
        return connections.size();
    }

    @Override
    public void disconnectClient(int id) throws IOException {
        sListener.connectionRemoved(id);
        SCommunicationClientHandler handler = connections.get(id);
        connections.remove(id);
        handler.disconnect();
    }

}
