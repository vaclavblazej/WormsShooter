package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
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

    @Override
    public Dimension getSize() {
        return ServerPanel.SIZE;
    }

    @Override
    public Color getPixel(Point point) {
        return ServerPanel.getInstance().check(point.x, point.y);
    }

    @Override
    public BufferedImage getMap() {
        return ServerPanel.getInstance().getMap();
    }

    private ServerCommunication() throws RemoteException {
    }

    @Override
    public void sendAction(Action action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
