package client;

import client.actions.ActionClient;
import client.actions.impl.GetModelAction;
import main.Application;
import objects.Body;
import server.actions.ActionServer;
import spacks.communication.SCommunication;
import spacks.communication.client.SCommunicationClient;
import utilities.PlayerInfo;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class ClientCommunication {

    private static ClientCommunication instance;

    public static ClientCommunication getInstance() {
        if (instance == null) {
            instance = new ClientCommunication();
        }
        return instance;
    }

    private PlayerInfo info;
    private SCommunicationClient connection;
    private boolean listening;
    private volatile boolean running;

    public void init(String ip, String port) {
        System.out.println("Client: starting");
        ActionServer.setView(ClientView.getInstance());
        info = new PlayerInfo(0);
        listening = false;

        connection = SCommunication.createNewClient();
        //new SCommunicationClient(new GetModelAction());
        try {
            connection.connect(ip, Integer.parseInt(port));
            Application.startClientView();
            startSocket(ip);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        getModel();
    }

    public void reset() {
        info = new PlayerInfo(0);
        running = false;
        listening = false;
//        connection.reset();
    }

    public void bindBody(int id, Body body) {
        Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        ClientView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }

    public void send(ActionClient packet) {
        packet.setId(info.getId());
        try {
            connection.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getModel() {
        System.out.println("Client: getting model");
        send(new GetModelAction());
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public void startSocket(String ip) throws IOException {
        System.out.println("Client: starting socket");

        connection.start();
    }
}
