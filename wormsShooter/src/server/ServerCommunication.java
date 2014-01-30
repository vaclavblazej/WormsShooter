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
import objects.items.ComponentTableModel;
import objects.items.Recipe;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableModel;
import utilities.communication.ServerInfo;
import utilities.materials.Material;
import utilities.materials.MaterialEnum;

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
        ServerComService service = ServerComService.getInstance();
        Point pos;
        switch (action) {
            case MINE:
                Point p = (Point) packet.get(0);
                int x = p.x;
                int y = p.y;
                body = controls.get(id);
                pos = body.getPosition();
                int distance = Math.abs(pos.x - x) + Math.abs(pos.y - y);
                if (distance < 6) {
                    MaterialEnum to = MaterialEnum.AIR;
                    MaterialEnum mat = ServerView.getInstance().change(x, y, to);
                    body.getInventory().add(Material.getComponents(mat));
                    service.broadcast(new PacketBuilder(Action.OBTAIN, id).addInfo(mat));
                    service.broadcast(new PacketBuilder(Action.MINE, id)
                            .addInfo(new Point(x, y)).addInfo(to));
                }
                break;
            case MOVE_LEFT:
            case MOVE_RIGHT:
            case MOVE_STOP:
            case MOVE_JUMP:
                body = controls.get(id);
                if (body != null) {
                    pos = body.getPosition();
                    Point.Double vel = body.getVelocity();
                    service.broadcast(new PacketBuilder(action, id).addInfo(pos)
                            .addInfo(vel.x).addInfo(vel.y));
                    body.control(action);
                }
                break;
            case CRAFT:
                int idx = (Integer) packet.get(0);
                ComponentTableModel inventory = controls.get(id).getInventory();
                Recipe receipe = ServerView.getInstance().getModel().getFactory()
                        .getRecipes().getReceipe(idx);
                inventory.remove(receipe.getIngredients());
                inventory.add(receipe.getProducts());
                service.broadcast(new PacketBuilder(Action.CRAFT, id).addInfo(idx));
                break;
            /*case TAKE:
             body.addItem(ServerPanel.getInstance().getItemFactory().get(ItemEnum.));
             ServerComService.getInstance().broadcast(
             new PacketBuilder(Action.ADD_ITEM, id).addInfo(ItemEnum.BLOCK).build());
             break;*/
            case DISCONNECT:
                ServerComService.getInstance().disconnect(id);
                break;
        }
    }

    public void bindBody(int id, TestBody body) {
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        ServerView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
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
        return ServerView.getInstance().getModel().serialize();
    }
}
