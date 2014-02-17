package communication.client;

import communication.server.GetModelServerActionl;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class GetModelAction extends PerformablePacket {

    @Override
    public void perform(AbstractView view) {
        ServerComService.getInstance().send(id, new GetModelServerActionl(view.getModel().serialize()));
    }
}
