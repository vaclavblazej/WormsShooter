package utilities.communication;

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
     * Stores information. Get this information by calling get(number) on Packet
     *
     * @param itemEnum
     * @return
     */
    public PacketBuilder addInfo(Object object) {
        packet.addInfo(object);
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
