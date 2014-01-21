package utilities;

import java.io.Serializable;

/**
 *
 * @author plach_000
 */
public class PlayerInfo implements Serializable {

    private final int id;

    public PlayerInfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
