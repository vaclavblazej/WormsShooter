package cz.spacks.worms.view.client;

import cz.spacks.worms.view.client.actions.ActionClient;
import cz.spacks.worms.view.client.actions.impl.ConnectAction;
import cz.spacks.worms.view.client.actions.impl.GetModelAction;
import cz.spacks.worms.view.server.actions.ActionServer;
import cz.spacks.worms.controller.PlayerInfo;
import cz.spacks.worms.controller.communication.RegistrationForm;
import spacks.communication.SCommunication;
import spacks.communication.client.SCommunicationClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class ClientCommunicationInternet extends ClientCommunication{

    private static final Logger logger = Logger.getLogger(ClientCommunicationInternet.class.getName());

    public ClientCommunicationInternet() {
        super();
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
            send(new ConnectAction(form));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
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
