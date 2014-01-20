package server;

import java.awt.Color;
import java.awt.Dimension;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.ControlsEnum;
import objects.TestBody;
import utilities.PlayerInfo;
import utilities.RegistrationForm;
import utilities.SerializableBufferedImage;
import utilities.ServerInfo;

/**
 *
 * @author Skarab
 */
public class ServerCommunication extends UnicastRemoteObject implements ServerComm {

    private static ServerCommunication instance;
    private static final long serialVersionUID = 1L;
    private static TestBody body;

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

    public void setBody(TestBody body) {
        this.body = body;
    }

    public void init() throws RemoteException {
        LocateRegistry.createRegistry(4242).rebind("ServerComm", instance);
    }

    @Override
    public Dimension getSize() {
        return ServerPanel.SIZE;
    }

    @Override
    public Color getPixel(int x, int y) {
        return ServerPanel.getInstance().getPixel(x, y);
    }

    @Override
    public SerializableBufferedImage getMapSerializable() {
        return getMap();
    }

    public SerializableBufferedImage getMap() {
        return new SerializableBufferedImage(ServerPanel.getInstance().getMap());
    }

    @Override
    public void sendAction(ControlsEnum action, boolean on) {
        if (body != null) {
            if (on) {
                body.controlOn(action);
            } else {
                body.controlOff(action);
            }
        }
    }

    @Override
    public PlayerInfo register(RegistrationForm form) {
        return ServerComService.getInstance().registerPlayer(form);
    }

    @Override
    public ServerInfo getServerInfo() {
        return ServerComService.getInstance().getServerInfo();
    }

}
