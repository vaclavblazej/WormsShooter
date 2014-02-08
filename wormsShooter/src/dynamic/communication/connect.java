package dynamic.communication;

import client.ClientView;
import java.io.IOException;
import java.io.ObjectOutputStream;
import objects.Body;
import utilities.AbstractView;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class connect extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(packet.getId(), b);
        System.out.println("client connect");
    }
}
