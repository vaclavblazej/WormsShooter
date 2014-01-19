package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Action;

/**
 *
 * @author Skarab
 */
public class ServerCommunication extends UnicastRemoteObject implements ServerComm {

    private static ServerCommunication instance;
    private static final long serialVersionUID = 1L;

    public static ServerCommunication getInstance() {
        if (instance == null) {
            try {
                instance = new ServerCommunication();
            } catch (RemoteException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    private ServerCommunication() throws RemoteException {
        super(0);
    }
    
    public void init() throws RemoteException {
        LocateRegistry.createRegistry(4242);
        try {
            Naming.rebind("ServerComm", instance);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Dimension getSize() {
        return ServerPanel.SIZE;
    }

    @Override
    public Color getPixel(int x, int y) {
        return ServerPanel.getInstance().check(x, y);
    }

    @Override
    public BufferedImage getMap() {
        return ServerPanel.getInstance().getMap();
    }

    @Override
    public void sendAction(Action action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
