package cz.spacks.worms.client.actions.impl;

import cz.spacks.worms.client.actions.ActionClient;
import cz.spacks.worms.utilities.communication.RegistrationForm;

/**
 * @author Václav Blažej
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
