package communication.server;

import client.ClientView;
import objects.Body;
import utilities.AbstractView;
import utilities.PlayerInfo;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class ConnectServerAction extends PerformablePacket {

    private PlayerInfo info;

    public ConnectServerAction(PlayerInfo info) {
        this.info = info;
    }

    public void perform(AbstractView view) {
        Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(info.getId(), b);
        System.out.println("client connect");
    }
}
