package dynamic.communication;

import java.net.Socket;
import utilities.communication.Action;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class add_item extends Packet {

    public add_item(Action action, int id) {
        super(action, id);
    }

    @Override
    public void perform(Socket socket, Packet packet) {
    }
}
