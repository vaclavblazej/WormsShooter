package client.actions.impl;

import client.actions.ActionClient;
import utilities.communication.RegistrationForm;

/**
 * @author V�clav Bla�ej
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
