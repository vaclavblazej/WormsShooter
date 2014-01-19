package client;

import java.awt.Dimension;
import java.awt.Point;
import server.ServerCommunication;

/**
 *
 * @author Skarab
 */
public class ClientCommunication {

    public static Dimension getSize() {
        return ServerCommunication.getSize();
    }
    
    public static int getPixel(int x, int y){
        return ServerCommunication.getPixel(new Point(x, y));
    }

    private ClientCommunication() {
    }
}
