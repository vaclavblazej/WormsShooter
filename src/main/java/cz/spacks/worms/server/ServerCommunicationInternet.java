package cz.spacks.worms.server;

import cz.spacks.worms.server.actions.ActionServer;
import spacks.communication.SCommunication;
import spacks.communication.server.SCommunicationServer;
import spacks.communication.utilities.SAction;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ServerCommunicationInternet extends ServerCommunication {

    private static final Logger logger = Logger.getLogger(ServerCommunicationInternet.class.getName());

    private SCommunicationServer server;

    public ServerCommunicationInternet(int port) throws IOException {
        super();
        logger.info("Server: starting");
        server = SCommunication.createNewServer(port, this);
        server.start();
    }

    public void broadcast(SAction action) {
        serverDo(() -> server.broadcast(action));
    }

    public void broadcastExceptOne(int leftOut, SAction action) {
        serverDo(() -> server.broadcastExceptOne(leftOut, action));
    }

    public void send(int id, ActionServer action) {
        serverDo(() -> server.send(id, action));
    }

    public void sendToGroup(Collection<Integer> ids, SAction action) {
        serverDo(() -> server.sendToGroup(ids, action));
    }

    // to simplify adapter, catch exceptions
    private interface ServerRunnable {
        void run() throws IOException;
    }

    // to handle exception boilerplate
    private void serverDo(ServerRunnable runnable) {
        try {
            runnable.run();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString());
        }
    }

    public void disconnect(int id) {
        try {
            server.disconnectClient(id);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString());
        }
    }
}
