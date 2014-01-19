package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import server.ServerComm;
import server.ServerCommunication;
import utilities.Action;
import utilities.SerializableBufferedImage;

/**
 *
 * @author Skarab
 */
public class ClientCommunication implements ServerComm {
    
    private static ClientCommunication instance;
    private ServerComm serverComm;
    
    
    public static ClientCommunication getInstance() {
        if (instance == null) instance = new ClientCommunication();
        return instance;
    }
    
    private ClientCommunication() {
    }
    
    public void init(String ip) throws NotBoundException, MalformedURLException, RemoteException {
        serverComm = (ServerComm) Naming.lookup("//" + ip + "/" + ServerComm.class.getSimpleName());
    }

    @Override
    public Dimension getSize() throws RemoteException {
        //return ServerCommunication.getInstance().getSize();
        return serverComm.getSize();
    }

    @Override
    public Color getPixel(int x, int y) throws RemoteException {
        //return ServerCommunication.getInstance().getPixel(x, y);
        return serverComm.getPixel(x, y);
    }
    
    public BufferedImage getMap() throws RemoteException {
        return getMapSerializable().getImage();
    }

    @Override
    public SerializableBufferedImage getMapSerializable() throws RemoteException {
        //return ServerCommunication.getInstance().getMap();
        return serverComm.getMapSerializable();
    }

    @Override
    public void sendAction(Action action) throws RemoteException {
        serverComm.sendAction(action);
    }
}
