package server;

import java.awt.Color;
import java.awt.Dimension;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import objects.ControlsEnum;
import utilities.PlayerInfo;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableBufferedImage;
import utilities.communication.ServerInfo;

/**
 *
 * @author plach_000
 */
public interface ServerComm extends Remote {
    
    Collection<Integer> getPlayers()throws RemoteException;

    Color getPixel(int x, int y) throws RemoteException;

    Dimension getSize() throws RemoteException;

    SerializableBufferedImage getMapSerializable() throws RemoteException;

    void sendAction(int id, ControlsEnum action, boolean on) throws RemoteException;
    
    PlayerInfo register(RegistrationForm from) throws RemoteException;
    
    ServerInfo getServerInfo() throws RemoteException;
}
