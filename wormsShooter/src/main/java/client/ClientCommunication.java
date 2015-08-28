package client;

import client.actions.ActionClient;
import client.actions.impl.GetModelAction;
import main.Application;
import objects.Body;
import server.actions.ActionServer;
import server.actions.impl.ConnectServerAction;
import spacks.communication.SCommunication;
import spacks.communication.client.SCommunicationClient;
import utilities.PlayerInfo;
import utilities.communication.RegistrationForm;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class ClientCommunication {

    private static final Logger logger = Logger.getLogger(ClientCommunication.class.getName());

    private static ClientCommunication instance;

    public static ClientCommunication getInstance() {
        if (instance == null) {
            instance = new ClientCommunication();
        }
        return instance;
    }

    private PlayerInfo info;
    private SCommunicationClient connection;

    public void init(String ip, String port) {
        logger.info("Client: starting");
        ActionServer.setView(ClientView.getInstance());
        info = new PlayerInfo(0);
        connection = SCommunication.createNewClient(new GetModelAction());
        try {
            connection.connect(ip, Integer.parseInt(port));
            ClientView.getInstance().init();
            startConnection(ip);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        getModel();
    }

    public void reset() {
        info = new PlayerInfo(0);
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
        logger .info("Client: getting model");
        send(new GetModelAction());
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public void startConnection(String ip) throws IOException {
        logger .info("Client: starting socket");
        connection.start();
    }
}
