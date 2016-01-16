package cz.spacks.worms.controller.comunication.client;

import cz.spacks.worms.view.views.ClientView;
import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.client.actions.impl.ConnectAction;
import cz.spacks.worms.controller.comunication.server.ServerCommunication;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.controller.PlayerInfo;
import cz.spacks.worms.model.communication.RegistrationForm;

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
        ClientView.getInstance().icnit();
        send(new ConnectAction(form));
    }

    @Override
    public void send(ActionClient packet) {
        packet.setId(info.getId());
        packet.perform();
        logger.log(Level.INFO, packet.toString());
    }
}
