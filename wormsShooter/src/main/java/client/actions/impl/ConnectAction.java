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

    public RegistrationForm getForm() {
        return form;
    }

    @Override
    public void perform() {
        /*Body b = ClientView.getInstance().newBody();
         view.getModel().getControls().put(getId(), b);*/
    }
}
