package utilities;

import java.io.Serializable;

/**
 *
 * @author plach_000
 */
public class PlayerInfo implements Serializable {

    private final int id;
    private String name;

    public PlayerInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
