package dynamic.communication;

import java.net.Socket;
import server.Performable;
import server.ServerComService;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class disconnect implements Performable {

    @Override
    public void perform(Socket socket, Packet packet) {
        /*ServerComService.getInstance().disconnect(id);*/
    }
}
