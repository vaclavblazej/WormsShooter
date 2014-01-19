package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import server.ServerCommunication;

/**
 *
 * @author Skarab
 */
public class ClientCommunication {

    public static Dimension getSize() {
        return ServerCommunication.getInstance().getSize();
    }

    public static Color getPixel(int x, int y) {
        return ServerCommunication.getInstance().getPixel(new Point(x, y));
    }

    public static BufferedImage getMap() {
        return ServerCommunication.getInstance().getMap();
    }

    private ClientCommunication() {
    }
}
