package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.AbstractView;
import utilities.communication.Packet;

/**
 *
 * @author plach_000
 */
public interface Performable {

    public void perform(Packet packet, AbstractView view);

    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException;

    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException;
}
