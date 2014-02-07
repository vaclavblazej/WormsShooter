package dynamic.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.communication.Model;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class add_item extends Packet {

    @Override
    public void perform(ObjectOutputStream os, Packet packet, Model model) throws IOException {
        super.perform(os, packet, model);
        // todo
    }
}
