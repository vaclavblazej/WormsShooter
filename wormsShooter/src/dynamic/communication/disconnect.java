package dynamic.communication;

import client.ClientCommunication;
import client.ClientView;
import java.io.IOException;
import java.io.ObjectOutputStream;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class disconnect extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        int id = packet.getId();
        if (id == ClientCommunication.getInstance().getInfo().getId()) {
            ClientView.getInstance().reset();
        } else {
            ClientCommunication.getInstance().unbindBody(id);
        }
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        os.writeObject(packet);
        ServerComService.getInstance().disconnect(packet.getId());
    }
}
