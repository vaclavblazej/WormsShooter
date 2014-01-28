package server;

import java.awt.Point;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.TestBody;
import objects.items.ItemEnum;
import utilities.Materials;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableModel;
import utilities.communication.ServerInfo;

/**
 *
 * @author Skarab
 */
public class ServerCommunication implements Remote, ServerComm {

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
        controls = new HashMap<>(20);
    }

    public Map<Integer, TestBody> getControls() {
        return controls;
    }

    public void init(int port) throws RemoteException {
        String local = null;
        try {
            local = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.setProperty("java.rmi.server.hostname", local);
        UnicastRemoteObject.exportObject(this, port);
        LocateRegistry.createRegistry(port);
        try {
            Naming.rebind("//" + local + ":" + port + "/"
                    + ServerComm.class.getSimpleName(), instance);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendAction(Packet packet) {
        TestBody body;
        Action action = packet.getAction();
        int id = packet.getId();
        switch (action) {
            case MINE:
                body = controls.get(id);
                int x = body.getPosition().x;
                int y = body.getPosition().y + 2;
                ServerPanel.getInstance().change(x, y, Materials.AIR);
                body.addItem(ServerPanel.getInstance().getItemFactory().get(ItemEnum.METAL));
                ServerComService.getInstance().broadcast(
                        new PacketBuilder(Action.ADD_ITEM, id).addInfo(ItemEnum.METAL).build());
                ServerComService.getInstance().broadcast(
                        new PacketBuilder(Action.MINE, id).addInfo(new Point(x, y)).build());
                break;
            case MOVE_LEFT:
            case MOVE_RIGHT:
            case MOVE_STOP:
            case MOVE_JUMP:
                body = controls.get(id);
                if (body != null) {
                    Point pos = body.getPosition();
                    Point.Double vel = body.getVelocity();
                    ServerComService.getInstance().broadcast(
                            new PacketBuilder(action, id).addInfo(pos)
                            .addInfo(vel.x).addInfo(vel.y).build());
                    body.control(action);
                }
                break;
            /*case TAKE:
             body.addItem(ServerPanel.getInstance().getItemFactory().get(ItemEnum.));
             ServerComService.getInstance().broadcast(
             new PacketBuilder(Action.ADD_ITEM, id).addInfo(ItemEnum.BLOCK).build());
             break;*/
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
    public SerializableModel getModel(int id) throws RemoteException {
        return ServerPanel.getInstance().getModel().serialize();
    }
}
