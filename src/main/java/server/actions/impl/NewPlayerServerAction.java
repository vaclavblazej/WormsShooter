package server.actions.impl;

import client.ClientView;
import objects.Body;
import server.actions.ActionServer;

/**
 * @author Václav Blažej
 */
public class NewPlayerServerAction extends ActionServer {

    public NewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(getId(), b);
    }
}
