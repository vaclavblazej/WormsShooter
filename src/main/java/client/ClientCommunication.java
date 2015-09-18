package client;

import client.actions.ActionClient;
import client.actions.impl.ConnectAction;
import client.actions.impl.GetModelAction;
import objects.Body;
import server.actions.ActionServer;
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
        if (instance == null) instance = new ClientCommunication();
        return instance;
    }

    public ClientCommunication() {
    }

    private PlayerInfo info;
    private SCommunicationClient connection;

    public void init(String ip, int port, RegistrationForm form) {
        logger.info("Client: starting");
        ActionServer.setView(ClientView.getInstance());
        info = new PlayerInfo(-1); // temporal
        connection = SCommunication.createNewClient(new GetModelAction());
        try {
            connection.connect(ip, port);
            ClientView.getInstance().init();
            startConnection(ip);
            connection.send(new ConnectAction(form));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
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
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void getModel() {
        logger.info("Client: getting model");
        send(new GetModelAction());
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public void setInfo(PlayerInfo info) {
        this.info = info;
    }

    public void startConnection(String ip) throws IOException {
        logger.info("Client: starting socket");
        connection.start();
    }
}
