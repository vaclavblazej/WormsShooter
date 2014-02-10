package dynamic.communication;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import main.Main;
import objects.Body;
import objects.Bullet;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class use extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        
    }
}
