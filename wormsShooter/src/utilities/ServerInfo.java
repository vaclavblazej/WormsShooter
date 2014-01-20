/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.Serializable;

/**
 *
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
