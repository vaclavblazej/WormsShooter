package communication.server;

import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class SendChatServerAction extends PerformablePacket {

    private String str;

    public SendChatServerAction(int id, String str) {
        super(id);
        this.str = str;
    }

    public void perform(AbstractView view) {
        view.logChat(id + " " + str);
    }
}
