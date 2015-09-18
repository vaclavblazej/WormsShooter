package client.actions.impl;

import client.actions.ActionClient;
import utilities.communication.RegistrationForm;

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
