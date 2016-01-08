package cz.spacks.worms.controller.comunication.server;

import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import spacks.communication.utilities.SAction;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ServerCommunicationLocal extends ServerCommunication {

    private static final Logger logger = Logger.getLogger(ServerCommunicationLocal.class.getName());

    public ServerCommunicationLocal() throws IOException {
        super();
        logger.info("Server: local starting");
    }

    public void broadcast(SAction action) {
        action.perform();
        logger.log(Level.INFO, action.toString());
    }

    public void broadcastExceptOne(int leftOut, SAction action) {
        action.perform();
        logger.log(Level.INFO, action.toString());
    }

    public void send(int id, ActionServer action) {
        action.perform();
        logger.log(Level.INFO, action.toString());
    }

    public void sendToGroup(Collection<Integer> ids, SAction action) {
        action.perform();
        logger.log(Level.INFO, action.toString());
    }

    public void disconnect(int id) {
    }
}
