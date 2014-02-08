package dynamic.communication;

import client.ClientCommunication;
import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.AbstractView;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class confirm extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        ClientCommunication.getInstance().getModel();
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
    }
}
