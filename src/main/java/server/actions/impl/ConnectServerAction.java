package server.actions.impl;

import client.ClientView;
import objects.Body;
import server.actions.ActionServer;
import utilities.communication.RegistrationForm;

/**
 * @author Václav Blažej
 */
public class ConnectServerAction extends ActionServer {

    private RegistrationForm form;

    public ConnectServerAction(RegistrationForm form, int id) {
        super(id);
        this.form = form;
    }

    public void perform() {
        Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(getId(), b);
        System.out.println("client connect");
    }
}
