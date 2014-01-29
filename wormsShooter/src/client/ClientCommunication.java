package client;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
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

    private ClientCommunication() {
        listening = false;
        controls = new HashMap<>(20);
    }

    public void init(String ip, String port) throws NotBoundException, MalformedURLException, RemoteException {
        serverComm = (ServerComm) Naming.lookup("//" + ip + ":"
                + port + "/" + ServerComm.class.getSimpleName());
        info = serverComm.register(new RegistrationForm());
        try {
            clientSocket = new Socket(InetAddress.getByName(ip), 4243);
            clientSocket.getOutputStream().write(("" + info.getId() + "\n").getBytes());
            startSocket();
        } catch (UnknownHostException ex) {
            GameWindow.getInstance().showError(new Exception("Could not connect to the Server"));
        } catch (IOException ex) {
            GameWindow.getInstance().showError(new Exception("IOException in ClientCommunication init"));
        }
    }

    private TestBody createPlayer(int id) {
        TestBody b = MainPanel.getInstance().newBody();
        bindBody(id, b);
        return b;
    }

    public void sendAction(PacketBuilder builder) throws RemoteException {
        serverComm.sendAction(builder.setId(info.getId()).build());
    }

    public void getModel() throws RemoteException {
        System.out.println("Transmission: model");
        Model model = serverComm.getModel(info.getId()).deserialize(MainPanel.getInstance());
        MainPanel.getInstance().setModel(model);
        controls = model.getControls();
        counter = model.getCounter();
        factory = model.getFactory();
        MainPanel.getInstance().setMyBody(controls.get(info.getId()));
    }

    public void bindBody(int id, TestBody body) {
        controls.put(id, body);
    }

    private void startSocket() {
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
                    while (true) {
                        try {
                            packet = (Packet) objectInput.readObject();
                            Action type = packet.getAction();
                            int count = packet.getCount();
                            int i;
                            if (count - counter <= 1) {
                                switch (type) {
                                    case MOVE_LEFT:
                                    case MOVE_RIGHT:
                                    case MOVE_STOP:
                                    case MOVE_JUMP:
                                        i = packet.getId();
                                        controls.get(i).setPosition((Point) packet.get(0));
                                        controls.get(i).setVelocity(
                                                new Point.Double((Double) packet.get(1), (Double) packet.get(2)));
                                        controls.get(i).control(type);
                                        break;
                                    case MINE:
                                        MainPanel.getInstance().change((Point) packet.get(0), MaterialEnum.AIR);
                                        break;
                                    case CRAFT:
                                        ComponentTableModel inventory = controls.get(packet.getId()).getInventory();
                                        int idx = (Integer) packet.get(0);
                                        Recipe receipe = factory.getRecipes().getReceipe(idx);
                                        inventory.remove(receipe.getIngredients());
                                        inventory.add(receipe.getProducts());
                                        inventory.fireTableDataChanged();
                                        break;
                                    case OBTAIN:
                                        ComponentTableModel inv = controls.get(packet.getId()).getInventory();
                                        MaterialEnum en = (MaterialEnum) packet.get(0);
                                        inv.add(Material.getComponents(en));
                                        break;
                                    case CONNECT:
                                        i = packet.getId();
                                        if (i == 0) {
                                            createPlayer(info.getId());
                                        }
                                        break;
                                    case ADD_ITEM:
                                        MainPanel.getInstance().getMyBody().addItem(factory.get((ItemEnum) packet.get(0)));
                                        break;
                                    case CONFIRM:
                                        getModel();
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

                }
            }).start();
        }
    }
}
