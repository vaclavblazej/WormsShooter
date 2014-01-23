package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import objects.ControlsEnum;
import utilities.PlayerInfo;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableModel;
import utilities.communication.ServerInfo;

/**
 *
 * @author plach_000
 */
public interface ServerComm extends Remote {

    SerializableModel getMode(int id) throws RemoteException;

    void sendAction(int id, ControlsEnum action, boolean on) throws RemoteException;

    PlayerInfo register(RegistrationForm from) throws RemoteException;

    ServerInfo getServerInfo() throws RemoteException;
}
