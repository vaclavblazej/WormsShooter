package utilities.communication;

/**
 *
 * @author Skarab
 */
public abstract class PerformablePacket extends Packet implements Performable {

    public PerformablePacket() {
    }

    public PerformablePacket(int id) {
        super(id);
    }
}
