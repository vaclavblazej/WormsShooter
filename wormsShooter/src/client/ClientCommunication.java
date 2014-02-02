package client;

import client.menu.GameWindowItemBar;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.TestBody;
import objects.items.ComponentTableModel;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import objects.items.Recipe;
import server.ServerComService;
import server.ServerComm;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Model;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.communication.RegistrationForm;
import utilities.materials.Material;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class ClientCommunication {

    private static ClientCommunication instance;

    public static ClientCommunication getInstance() {
        if (instance == null) {
            instance = new ClientCommunication();
        }
        return instance;
    }
    private ServerComm serverComm;
    private PlayerInfo info;
    private boolean listening;
    private Socket clientSocket;
    private Map<Integer, TestBody> controls;
    private int counter;
    private ItemFactory factory;
    private volatile boolean running;

    private ClientCommunication() {
    }

    public void init(String ip, String port) throws NotBoundException, MalformedURLException, RemoteException {
        serverComm = (ServerComm) Naming.lookup("//" + ip + ":"
                + port + "/" + ServerComm.class.getSimpleName());
        info = serverComm.register(new RegistrationForm());
        startSocket(ip);
        listening = false;
        controls = new HashMap<>(20);
    }

    private TestBody createPlayer(int id) {
        TestBody b = ClientView.getInstance().newBody();
        bindBody(id, b);
        return b;
    }

    public void sendAction(PacketBuilder builder) throws RemoteException {
        serverComm.sendAction(builder.setId(info.getId()).build());
    }

    public void getModel() throws RemoteException {
        System.out.println("Client: getting model");
        Model model = serverComm.getModel(info.getId()).deserialize(ClientView.getInstance());
        ClientView.getInstance().setModel(model);
        controls = model.getControls();
        counter = model.getCounter();
        factory = model.getFactory();
        ClientView.getInstance().setMyBody(controls.get(info.getId()));
    }

    public void bindBody(int id, TestBody body) {
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        ClientView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }

    private void startSocket(String ip) {
        System.out.println("Client: starting socket");
        try {
            clientSocket = new Socket(InetAddress.getByName(ip), 4243);
            new ObjectOutputStream(clientSocket.getOutputStream())
                    .writeObject(new PacketBuilder(Action.CONFIRM).setId(info.getId()).build());
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (clientSocket != null && listening == false) {
            listening = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream objectInput = null;
                    Packet packet;
                    try {
                        objectInput = new ObjectInputStream(clientSocket.getInputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    running = true;
                    while (running) {
                        try {
                            packet = (Packet) objectInput.readObject();
                            Action type = packet.getAction();
                            int count = packet.getCount();
                            int id = packet.getId();;
                            if (count - counter <= 1) {
                                switch (type) {
                                    case MOVE_LEFT:
                                    case MOVE_RIGHT:
                                    case MOVE_STOP:
                                    case MOVE_JUMP:
                                        controls.get(id).setPosition((Point) packet.get(0));
                                        controls.get(id).setVelocity(
                                                new Point.Double((Double) packet.get(1), (Double) packet.get(2)));
                                        controls.get(id).control(type);
                                        break;
                                    case MINE:
                                        Point p = (Point) packet.get(0);
                                        ClientView.getInstance().change(p.x, p.y, (MaterialEnum) packet.get(1));
                                        ClientView.getInstance().getModel().getMap().recalculateShadows(p);
                                        break;
                                    case CRAFT:
                                        ComponentTableModel inventory = controls.get(packet.getId()).getInventory();
                                        int idx = (Integer) packet.get(0);
                                        Recipe receipe = factory.getRecipes().getReceipe(idx);
                                        inventory.remove(receipe.getIngredients());
                                        inventory.add(receipe.getProducts());
                                        inventory.fireTableDataChanged();
                                        GameWindowItemBar.getInstance().refreshBar(inventory);
                                        break;
                                    case OBTAIN:
                                        ComponentTableModel inv = controls.get(packet.getId()).getInventory();
                                        MaterialEnum en = (MaterialEnum) packet.get(0);
                                        inv.add(Material.getComponents(en));
                                        GameWindowItemBar.getInstance().refreshBar(inv);
                                        break;
                                    case ADD_ITEM:
                                        ClientView.getInstance().getMyBody().addItem(factory.get((ItemEnum) packet.get(0)));
                                        break;
                                    case CONNECT:
                                        if (id == 0) {
                                            createPlayer(info.getId());
                                        }
                                        break;
                                    case CONFIRM:
                                        getModel();
                                        break;
                                    case DISCONNECT:
                                        if (id == info.getId()) {
                                            serverComm = null;
                                            ClientView.getInstance().reset();
                                            running = false;
                                        } else {
                                            unbindBody(id);
                                        }
                                        break;
                                }
                            } else {
                                getModel();
                            }
                            counter = count;
                        } catch (IOException ex) {
                            Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    GameWindowItemBar.getInstance().clearBar();
                    System.out.println("Client: terminating socket");
                    listening = false;
                }
            }).start();
        }
    }
}
