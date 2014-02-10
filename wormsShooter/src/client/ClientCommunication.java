package client;

import client.menu.GameWindowItemBar;
import dynamic.communication.DynamicLoader;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Body;
import objects.items.ItemFactory;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Model;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.communication.RegistrationForm;
import utilities.communication.SerializableModel;

/**
 *
 * @author plach_000
 */
public class ClientCommunication {

    private static ClientCommunication instance;

    public static ClientCommunication getInstance() {
        if (instance == null) {
            instance = new ClientCommunication();
        }
        return instance;
    }
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private PlayerInfo info;
    private boolean listening;
    private Socket inputSocket;
    private Map<Integer, Body> controls;
    private int counter;
    private ItemFactory factory;
    private volatile boolean running;

    public void init(String ip, String port) {
        info = new PlayerInfo(0);
        listening = false;
        try {
            System.out.println("Client: connecting to: " + InetAddress.getByName(ip)
                    + " " + Integer.parseInt(port));
            Socket socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client: socket obtained");
            register();
            startSocket(ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        info = new PlayerInfo(0);
        running = false;
        listening = false;
        is = null;
        os = null;
    }

    public void bindBody(int id, Body body) {
        //Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        //Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        ClientView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }

    public void send(PacketBuilder object) {
        try {
            os.writeObject(object.setId(info.getId()).build());
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object receive() {
        try {
            return is.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void register() {
        send(new PacketBuilder(Action.CONNECT).addInfo(new RegistrationForm()));
        info = (PlayerInfo) receive();
        System.out.println("Client: registration complete (id: " + info.getId() + ")");
    }

    public void getModel() {
        System.out.println("Client: getting model");
        send(new PacketBuilder(Action.GET_MODEL));
        Model model = ((SerializableModel) receive()).deserialize(ClientView.getInstance());
        System.out.println("model received");
        ClientView.getInstance().setModel(model);
        controls = model.getControls();
        counter = model.getCounter();
        factory = model.getFactory();
        System.out.println("Controls: " + controls);
        ClientView.getInstance().setMyBody(controls.get(info.getId()));
    }

    public PlayerInfo getInfo() {
        return info;
    }

    private void startSocket(String ip) {
        System.out.println("Client: starting socket");
        try {
            inputSocket = new Socket(InetAddress.getByName(ip), 4243);
            ObjectOutputStream outs = new ObjectOutputStream(inputSocket.getOutputStream());
            outs.writeObject(new PacketBuilder(Action.CONFIRM).setId(info.getId()).build());
        } catch (Exception ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (inputSocket != null && listening == false) {
            listening = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream objectInput = null;
                    Packet packet;
                    try {
                        objectInput = new ObjectInputStream(inputSocket.getInputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    running = true;
                    Point p;
                    while (running) {
                        try {
                            packet = (Packet) objectInput.readObject();
                            Action type = packet.getAction();
                            int count = packet.getCount();
                            int id = packet.getId();
                            //System.out.println("Client: " + type);
                            if (count - counter <= 1) {
                                DynamicLoader.getInstance().get(packet.getAction()).
                                        performClient(os, packet, ClientView.getInstance());
                            } else {
                                getModel();
                            }
                            counter = count;
                        } catch (ClassNotFoundException | IOException ex) {
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
