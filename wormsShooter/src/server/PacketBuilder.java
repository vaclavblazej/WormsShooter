package server;

import java.awt.Point;
import utilities.communication.Action;

/**
 *
 * @author Skarab
 */
public class PacketBuilder {

    private static Packet packet;

    public PacketBuilder(Action action, int id) {
        packet = new Packet(action, id);
    }

    public PacketBuilder setCount(int count) {
        packet.setCount(count);
        return this;
    }

    public PacketBuilder setBool(boolean bool) {
        packet.setBool(bool);
        return this;
    }

    public PacketBuilder addPoint(Point point) {
        packet.addPoint(point);
        return this;
    }

    public PacketBuilder addDoublePoint(Point.Double point) {
        packet.addDoublePoint(point);
        return this;
    }

    public Packet build() {
        return packet;
    }
}
