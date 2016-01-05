package cz.spacks.worms.server.actions;

import cz.spacks.worms.client.ClientView;
import spacks.communication.utilities.SAction;

/**
 *
 */
public abstract class ActionServer implements SAction {

    protected transient static ClientView view;
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
