package server;

import client.actions.ActionClient;
import objects.Body;
import spacks.communication.SCommunication;
import spacks.communication.server.SCommunicationServer;
import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SListener;

import java.io.IOException;
import java.util.Map;

/**
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class ServerCommunication implements SListener {

    private static ServerCommunication instance;

    public static ServerCommunication getInstance() {
        return instance;
    }

    private SCommunicationServer server;

    public ServerCommunication(int port) throws IOException {
        System.out.println("Server: starting");
        ActionClient.setView(ServerView.getInstance());
        server = SCommunication.createNewServer(port, this); // todo this object listened to connections on server
        server.start();
        instance = this;
    }

    public SCommunicationServer getServer() {
        return server;
    }

    public void setServer(SCommunicationServer server) {
        this.server = server;
    }

    public void broadcast(SAction action) {
        try {
            server.broadcast(action);
        } catch (IOException ex) {
        }
    }

    public void send(int id, SAction action) {
        try {
            server.send(id, action);
        } catch (IOException ex) {
        }
    }

    public void disconnect(int id) {
        try {
            server.disconnectClient(id);
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
