package client.actions;

import communication.frontend.utilities.Performable;
import server.ServerView;

/**
 *
 * @author Skarab
 */
public abstract class ActionClient implements Performable {

    protected static ServerView view;
    protected int id;

    public static ServerView getView() {
        return view;
    }

    public static void setView(ServerView aView) {
        view = aView;
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
