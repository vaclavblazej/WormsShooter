package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientView;
import cz.spacks.worms.objects.Body;
import cz.spacks.worms.server.actions.ActionServer;
import cz.spacks.worms.utilities.communication.RegistrationForm;

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
