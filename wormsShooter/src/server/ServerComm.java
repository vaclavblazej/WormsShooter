package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import utilities.ControlsEnum;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableModel;
import utilities.communication.ServerInfo;

/**
 *
 * @author plach_000
 */
public interface ServerComm extends Remote {

    SerializableModel getModel(int id) throws RemoteException;

    void sendAction(int id, Action action) throws RemoteException;

    PlayerInfo register(RegistrationForm from) throws RemoteException;

    ServerInfo getServerInfo() throws RemoteException;
}
