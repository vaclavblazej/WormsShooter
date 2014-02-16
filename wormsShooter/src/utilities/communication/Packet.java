package utilities.communication;

import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class Packet implements Serializable {

    private int count;
    protected int id;

    public Packet() {
        this(0);
    }

    public Packet(int id) {
        this.id = id;
    }

    public Packet setCount(int count) {
        this.count = count;
        return this;
    }

    public Packet setId(int id) {
        this.id = id;
        return this;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }
}
