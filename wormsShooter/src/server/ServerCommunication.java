package server;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.TestBody;
import utilities.Materials;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.PacketBuilder;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableModel;
import utilities.communication.ServerInfo;

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

    public Map<Integer, TestBody> getControls() {
        return controls;
    }

    public void init() throws RemoteException {
        LocateRegistry.createRegistry(4242).rebind(ServerComm.class.getSimpleName(), instance);
    }

    @Override
    public void sendAction(int id, Action action) {
        TestBody body;
        switch (action) {
            case Mine:
                body = controls.get(id);
                int x = body.getPosition().x;
                int y = body.getPosition().y + 1;
                ServerPanel.getInstance().change(x, y, Materials.Dirt);
                ServerComService.getInstance().broadcast(
                        new PacketBuilder(action, id).addPoint(new Point(x, y)).build());
                break;
            case Move_left:
            case Move_right:
            case Move_stop:
            case Move_jump:
                body = controls.get(id);
                if (body != null) {
                    Point pos = body.getPosition();
                    Point.Double vel = body.getVelocity();
                    ServerComService.getInstance().broadcast(
                            new PacketBuilder(action, id).addPoint(pos).addDoublePoint(vel).build());
                    body.control(action);
                }
                break;
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
    public SerializableModel getMode(int id) throws RemoteException {
        return ServerPanel.getInstance().getModel().serialize();
    }
}
