package communication.client;

import communication.server.SendChatServerAction;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class SendChatAction extends PerformablePacket {

    private String str;

    public SendChatAction(String str) {
        this.str = str;
    }

    @Override
    public void perform(AbstractView view) {
        ServerComService.getInstance().broadcast(new SendChatServerAction(id, str));
    }
}
