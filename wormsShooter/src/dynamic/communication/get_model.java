package dynamic.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.AbstractView;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class get_model extends Packet {

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        super.performServer(os, packet, view);
        os.writeObject(view.getModel().serialize());
    }
}
