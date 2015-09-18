package server.actions;

import client.ClientView;
import spacks.communication.utilities.SAction;

/**
 * @author Václav Blažej
 */
public abstract class ActionServer implements SAction {

    protected transient static ClientView view;
    protected final int id;

    public int getId() {
        return id;
    }

    public static ClientView getView() {
        return view;
    }

    public static void setView(ClientView aView) {
        view = aView;
    }

    public ActionServer() {
        id = 0;
    }

    public ActionServer(int id) {
        super();
        this.id = id;
    }
}
