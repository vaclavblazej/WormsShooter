package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.communication.RegistrationForm;
import cz.spacks.worms.model.objects.Body;

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
        Body b = worldService.newBody();
        worldService.getWorldModel().getControls().put(getId(), b);
        System.out.println("cz.spacks.worms.views connect");
    }
}
