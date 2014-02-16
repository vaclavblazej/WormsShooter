package communication.client;

import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class ConfirmAction extends PerformablePacket {

    public ConfirmAction(int id) {
        super(id);
    }

    public void perform(AbstractView view) {
    }
}
