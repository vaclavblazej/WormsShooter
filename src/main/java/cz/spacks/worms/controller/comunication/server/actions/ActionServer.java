package cz.spacks.worms.controller.comunication.server.actions;

import cz.spacks.worms.controller.comunication.client.ClientCommunication;
import cz.spacks.worms.controller.services.WorldService;
import spacks.communication.utilities.SAction;

/**
 *
 */
public abstract class ActionServer implements SAction {

    protected transient static WorldService worldService;
    protected transient static ClientCommunication clientCommunication;
    protected int id;

    public static void setClientCommunication(ClientCommunication clientCommunication) {
        ActionServer.clientCommunication = clientCommunication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static WorldService getWorldService() {
        return worldService;
    }

    public static void setWorldService(WorldService worldServicePar) {
        worldService = worldServicePar;
    }

    public ActionServer() {
        id = 0;
    }

    public ActionServer(int id) {
        super();
        this.id = id;
    }
}
