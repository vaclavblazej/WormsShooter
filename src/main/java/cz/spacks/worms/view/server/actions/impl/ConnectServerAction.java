package cz.spacks.worms.view.server.actions.impl;

import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.view.server.actions.ActionServer;
import cz.spacks.worms.controller.communication.RegistrationForm;

/**
 *
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
        System.out.println("cz.spacks.worms.client connect");
    }
}
