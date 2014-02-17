package communication.server;

import client.ClientView;
import objects.Body;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;
import utilities.communication.RegistrationForm;

/**
 *
 * @author Skarab
 */
public class ConnectServerAction extends PerformablePacket {

    private RegistrationForm form;

    public ConnectServerAction(RegistrationForm form, int id) {
        super(id);
        this.form = form;
    }

    public void perform(AbstractView view) {
        Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(getId(), b);
        System.out.println("client connect");
    }
}
