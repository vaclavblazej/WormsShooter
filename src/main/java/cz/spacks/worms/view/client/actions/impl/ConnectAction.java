package cz.spacks.worms.view.client.actions.impl;

import cz.spacks.worms.view.client.actions.ActionClient;
import cz.spacks.worms.controller.communication.RegistrationForm;

/**
 *
 */
public class ConnectAction extends ActionClient {

    private RegistrationForm form;

    public ConnectAction(RegistrationForm form) {
        this.form = form;
    }

    @Override
    public void perform() {
    }
}
