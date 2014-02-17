package communication.server;

import client.ClientCommunication;
import client.ClientView;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class DisconnectServerAction extends PerformablePacket {

    public DisconnectServerAction(int id) {
        super(id);
    }

    @Override
    public void perform(AbstractView view) {
        if (id == ClientCommunication.getInstance().getInfo().getId()) {
            ClientView.getInstance().reset();
        } else {
            ClientCommunication.getInstance().unbindBody(id);
        }
    }
}
