package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    private Map<Integer, TestBody> controls;

    private ServerCommunication() throws RemoteException {
        super(0);
        controls = new HashMap<>(20);
    }

    public void init() throws RemoteException {
        LocateRegistry.createRegistry(4242).rebind(ServerComm.class.getSimpleName(), instance);
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
    public void sendAction(int id, ControlsEnum action, boolean on) {
        TestBody body = controls.get(id);
        if (body != null) {
            Point pos = body.getPosition();
            Point.Double vel = body.getVelocity();
            ServerComService.getInstance().broadcast(
                    0 + " " + id + " "
                    + pos.x + " " + pos.y + " "
                    + vel.x + " " + vel.y + " "
                    + action + " " + on);
            if (on) {
                body.controlOn(action);
            } else {
                body.controlOff(action);
            }
        }
    }

    public void bindBody(int id, TestBody body) {
        controls.put(id, body);
    }

    @Override
    public PlayerInfo register(RegistrationForm form) {
        return ServerComService.getInstance().registerPlayer(form);
    }

    @Override
    public ServerInfo getServerInfo() {
        return ServerComService.getInstance().getServerInfo();
    }

    @Override
    public Collection<Integer> getPlayers() throws RemoteException {
        return ServerComService.getInstance().getPlayers();
    }
}
