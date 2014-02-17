package communication.client;

import client.ClientView;
import objects.Body;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;
import utilities.communication.RegistrationForm;

/**
 *
 * @author Skarab
 */
public class ConnectAction extends PerformablePacket {

    private RegistrationForm form;

    public ConnectAction(RegistrationForm form) {
        this.form = form;
    }

    public RegistrationForm getForm() {
        return form;
    }

    @Override
    public void perform(AbstractView view) {
        /*Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(getId(), b);*/
    }
}
