package server;

import client.actions.ActionClient;
import communication.backend.server.SCommunicationServer;
import communication.frontend.utilities.Performable;
import communication.frontend.utilities.SListener;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Body;
import server.actions.impl.DisconnectServerAction;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class ServerCommunication extends SListener {

    private static ServerCommunication instance;

    public static ServerCommunication getInstance() {
        return instance;
    }
    private SCommunicationServer server;

    public ServerCommunication(int port) throws IOException {
        System.out.println("Server: starting");
        ActionClient.setView(ServerView.getInstance());
        server = new SCommunicationServer(port, this);
        server.start();
        instance = this;
    }

    public SCommunicationServer getServer() {
        return server;
    }

    public void setServer(SCommunicationServer server) {
        this.server = server;
    }

    public void broadcast(Performable action) {
        try {
            server.broadcast(action);
        } catch (IOException ex) {
        }
    }

    public void send(int id, Performable action) {
        try {
            server.send(id, action);
        } catch (IOException ex) {
        }
    }

    public void disconnect(int id) {
        try {
            server.disconnect(id);
        } catch (IOException ex) {
        }
    }

    @Override
    public void connectionCreated(int id) {
        Body body = ServerView.getInstance().newBody();
        Map<Integer, Body> controls = ServerView.getInstance().getModel().getControls();
        controls.put(id, body);
        System.out.println("bind body");
    }

    @Override
    public void connectionRemoved(int id) {
        Map<Integer, Body> controls = ServerView.getInstance().getModel().getControls();
        ServerView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }
}
