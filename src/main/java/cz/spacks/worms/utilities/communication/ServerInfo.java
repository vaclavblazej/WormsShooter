package cz.spacks.worms.utilities.communication;

import java.io.Serializable;

/**
 * @author �t�p�n Plach�
 */
public class ServerInfo implements Serializable {
    public final String name;
    public final int numberOfPlayers;

    public ServerInfo(String name, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
    }
}
