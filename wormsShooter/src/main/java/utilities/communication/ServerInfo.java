package utilities.communication;

import java.io.Serializable;

/**
 * @author plach_000
 */
public class ServerInfo implements Serializable {
    public final String name;
    public final int numberOfPlayers;

    public ServerInfo(String name, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
    }

}
