package cz.spacks.worms.client;

import cz.spacks.worms.client.actions.ActionClient;
import cz.spacks.worms.client.actions.impl.ConnectAction;
import cz.spacks.worms.client.actions.impl.GetModelAction;
import cz.spacks.worms.server.ServerCommunication;
import cz.spacks.worms.server.ServerCommunicationLocal;
import cz.spacks.worms.server.actions.ActionServer;
import cz.spacks.worms.utilities.PlayerInfo;
import cz.spacks.worms.utilities.communication.RegistrationForm;
import spacks.communication.SCommunication;
import spacks.communication.client.SCommunicationClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class ClientCommunicationLocal extends ClientCommunication{

    private static final Logger logger = Logger.getLogger(ClientCommunicationLocal.class.getName());

    public ClientCommunicationLocal() {
        super();
    }

    private PlayerInfo info;

    public void init(RegistrationForm form) {
        logger.info("Client: local starting");
        ActionServer.setView(ClientView.getInstance());
        info = new PlayerInfo(0); // local
        ServerCommunication.getInstance().connectionCreated(info.getId());
        ClientView.getInstance().init();
        send(new ConnectAction(form));
    }

    @Override
    public void send(ActionClient packet) {
        packet.setId(info.getId());
        packet.perform();
        logger.log(Level.INFO, packet.toString());
    }
}
