package cz.spacks.worms.controller.comunication.client.actions;

import cz.spacks.worms.controller.comunication.server.ServerCommunication;
import cz.spacks.worms.view.views.ServerView;
import spacks.communication.utilities.SAction;

/**
 *
 */
public abstract class ActionClient implements SAction {

    protected static ServerView view;
    protected static ServerCommunication serverCommunication;
    protected int id;

    public static ServerView getView() {
        return view;
    }

    public static void setView(ServerView aView) {
        view = aView;
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
