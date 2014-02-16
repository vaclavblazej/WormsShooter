package communication.client;

import communication.server.DisconnectServerAction;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class DisconnectAction extends PerformablePacket {

    @Override
    public void perform(AbstractView view) {
        ServerComService.getInstance().send(id, new DisconnectServerAction(id));
        ServerComService.getInstance().disconnect(id);
    }
}
