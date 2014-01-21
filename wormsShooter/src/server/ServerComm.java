package server;

import java.awt.Color;
import java.awt.Dimension;
import java.rmi.Remote;
import java.rmi.RemoteException;
import objects.ControlsEnum;
import utilities.PlayerInfo;
import utilities.RegistrationForm;
import utilities.SerializableBufferedImage;
import utilities.ServerInfo;

/**
 *
 * @author plach_000
 */
public interface ServerComm extends Remote {

    Color getPixel(int x, int y) throws RemoteException;

    Dimension getSize() throws RemoteException;

    SerializableBufferedImage getMapSerializable() throws RemoteException;

    void sendAction(int id, ControlsEnum action, boolean on) throws RemoteException;
    
    PlayerInfo register(RegistrationForm from) throws RemoteException;
    
    ServerInfo getServerInfo() throws RemoteException;
}
