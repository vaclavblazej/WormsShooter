package dynamic.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.communication.Model;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class get_model extends Packet {

    @Override
    public void perform(ObjectOutputStream os, Packet packet, Model model) throws IOException {
        super.perform(os, packet, model);
        os.writeObject(model.serialize());
    }
}
