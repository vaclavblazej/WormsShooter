package cz.spacks.worms.controller.client.actions;

import cz.spacks.worms.view.server.ServerView;
import spacks.communication.utilities.SAction;

/**
 *
 */
public abstract class ActionClient implements SAction {

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
