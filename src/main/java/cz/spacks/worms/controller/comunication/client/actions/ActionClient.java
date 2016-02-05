package cz.spacks.worms.controller.comunication.client.actions;

import cz.spacks.worms.controller.comunication.server.ServerCommunication;
import cz.spacks.worms.controller.services.WorldService;
import spacks.communication.utilities.SAction;

/**
 *
 */
public abstract class ActionClient implements SAction {

    protected static WorldService worldService;
    protected static ServerCommunication serverCommunication;
    protected int id;

    public static WorldService getWorldService() {
        return worldService;
    }

    public static void setWorldService(WorldService aView) {
        worldService = aView;
    }

    public static void setServerCommunication(ServerCommunication serverCommunication) {
        ActionClient.serverCommunication = serverCommunication;
    }

    public ActionClient() {
        this.id = 0;
    }

    public ActionClient(int id) {
        super();
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
