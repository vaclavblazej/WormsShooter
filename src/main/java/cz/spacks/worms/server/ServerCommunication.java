package cz.spacks.worms.server;

import cz.spacks.worms.client.actions.ActionClient;
import cz.spacks.worms.objects.Body;
import cz.spacks.worms.server.actions.impl.GetModelServerAction;
import cz.spacks.worms.server.actions.impl.NewPlayerServerAction;
import cz.spacks.worms.server.actions.impl.SetIdNewPlayerServerAction;
import spacks.communication.SCommunication;
import spacks.communication.server.SCommunicationServer;
import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SListener;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class ServerCommunication implements SListener {

    private static final Logger logger = Logger.getLogger(ServerCommunication.class.getName());

    private static ServerCommunication instance;

    public static ServerCommunication getInstance() {
        return instance;
    }

    private SCommunicationServer server;

    public ServerCommunication(int port) throws IOException {
        logger.info("Server: starting");
        ActionClient.setView(ServerView.getInstance());
        server = SCommunication.createNewServer(port, this);
        server.start();
        instance = this;
    }

    public void broadcast(SAction action) {
        serverDo(() -> server.broadcast(action));
    }

    public void broadcastExceptOne(int leftOut, SAction action) {
        serverDo(() -> server.broadcastExceptOne(leftOut, action));
    }

    public void send(int id, SAction action) {
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

    @Override
    public void connectionCreated(int id) {
        final ServerView serverView = ServerView.getInstance();
        Body body = serverView.newBody();
        Map<Integer, Body> controls = serverView.getModel().getControls();
        controls.put(id, body);
        final ServerCommunication serverCommunication = ServerCommunication.getInstance();
        serverCommunication.send(id, new SetIdNewPlayerServerAction(id));
        serverCommunication.send(id, new GetModelServerAction(serverView.getModel().serialize()));
        serverCommunication.broadcastExceptOne(id, new NewPlayerServerAction(id));
    }

    @Override
    public void connectionRemoved(int id) {
        Map<Integer, Body> controls = ServerView.getInstance().getModel().getControls();
        ServerView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }
}
