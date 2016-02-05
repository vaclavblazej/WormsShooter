package cz.spacks.worms.controller.comunication.client;

import cz.spacks.worms.controller.PlayerInfo;
import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.client.actions.impl.ConnectAction;
import cz.spacks.worms.controller.comunication.client.actions.impl.GetModelAction;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.communication.RegistrationForm;
import spacks.communication.SCommunication;
import spacks.communication.client.SCommunicationClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class ClientCommunicationInternet extends ClientCommunication {

    private static final Logger logger = Logger.getLogger(ClientCommunicationInternet.class.getName());

    public ClientCommunicationInternet() {
        super();
    }

    private PlayerInfo info;
    private SCommunicationClient connection;

    public boolean init(String ip, int port, RegistrationForm form) {
        logger.info("Client: starting");
        ActionServer.setWorldService(worldService);
        info = new PlayerInfo(-1); // temporal
        connection = SCommunication.createNewClient(new GetModelAction());
        try {
            connection.connect(ip, port);
            startConnection(ip);
            send(new ConnectAction(form));
            return true;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void send(ActionClient packet) {
        packet.setId(info.getId());
        try {
            connection.send(packet);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void startConnection(String ip) throws IOException {
        logger.info("Client: starting socket");
        connection.start();
    }
}
