package cz.spacks.worms.model.communication;

import java.io.Serializable;

/**
 *
 */
public class ServerInfo implements Serializable {
    public final String name;
    public final int numberOfPlayers;

    public ServerInfo(String name, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
    }
}
