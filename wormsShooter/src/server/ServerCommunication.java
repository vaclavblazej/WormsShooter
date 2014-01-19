package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Skarab
 */
public class ServerCommunication {

    public static Dimension getSize() {
        return ServerPanel.SIZE;
    }

    public static Color getPixel(Point point) {
        return ServerPanel.check(point.x, point.y);
    }

    private ServerCommunication() {
    }
}
