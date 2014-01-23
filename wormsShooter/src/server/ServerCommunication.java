package server;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.ControlsEnum;
import objects.TestBody;
import utilities.Materials;
import utilities.PlayerInfo;
import utilities.communication.Action;
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
    public void sendAction(int id, Action action, ControlsEnum cont, boolean on) {
        TestBody body;
        switch (action) {
            case Mine:
                body = controls.get(id);
                int x = body.getPosition().x;
                int y = body.getPosition().y + 1;
                //System.out.println("change " + point.x + " " + point.y + " to dirt");
                ServerPanel.getInstance().change(x, y, Materials.Dirt);
                ServerComService.getInstance().broadcast(Action.Mine, "" + x + " " + y);
                break;
            case Move:
                body = controls.get(id);
                if (body != null) {
                    Point pos = body.getPosition();
                    Point.Double vel = body.getVelocity();
                    ServerComService.getInstance().broadcast(Action.Move,
                            id + " "
                            + pos.x + " " + pos.y + " "
                            + vel.x + " " + vel.y + " "
                            + cont + " " + on);
                    if (on) {
                        body.controlOn(cont);
                    } else {
                        body.controlOff(cont);
                    }
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
