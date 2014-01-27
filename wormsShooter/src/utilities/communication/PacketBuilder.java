package utilities.communication;

import java.awt.Point;

/**
 * Use to build packets.
 *
 * @author Skarab
 */
public class PacketBuilder {

    private static Packet packet;

    public PacketBuilder(Action action, int id) {
        packet = new Packet(action, id);
    }

    /**
     * Sets count which is used to check if any packet was lost in the
     * transmission.
     *
     * @param count
     * @return
     */
    public PacketBuilder setCount(int count) {
        packet.setCount(count);
        return this;
    }

    /**
     * Stores point. Get this point by calling getPoint(number) on Packet
     *
     * @param point
     * @return
     */
    public PacketBuilder addPoint(Point point) {
        packet.addPoint(point);
        return this;
    }

    /**
     * Stores double point. Get this point by calling getDoublePoint(number) on
     * Packet
     *
     * @param point
     * @return
     */
    public PacketBuilder addDoublePoint(Point.Double point) {
        packet.addDoublePoint(point);
        return this;
    }

    /**
     * Creates Packet objects with desired properties. Set these properties
     * before you call build()
     *
     * @return
     */
    public Packet build() {
        return packet;
    }
}
