package server.actions;

import client.ClientView;
import communication.frontend.utilities.Performable;

/**
 *
 * @author Skarab
 */
public abstract class ActionServer implements Performable{

    protected static ClientView view;
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
