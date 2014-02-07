package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.communication.Model;
import utilities.communication.Packet;

/**
 *
 * @author plach_000
 */
public interface Performable {

    public void perform(ObjectOutputStream os, Packet packet, Model model) throws IOException;
}
