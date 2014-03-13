package client;

import client.menu.GameWindowItemBar;
import communication.client.ConfirmAction;
import communication.client.ConnectAction;
import communication.client.GetModelAction;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import objects.Body;
import utilities.PlayerInfo;
import utilities.communication.PerformablePacket;
import utilities.communication.RegistrationForm;

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
    private int counter;
    private volatile boolean running;

    public void init(String ip, String port, String name) {
        System.out.println("Client: starting");
        info = new PlayerInfo(0, "");
        listening = false;
        try {
            System.out.println("Client: connecting to: " + ip + " " + port);
            Socket socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client: got server socket");
            register(name);
            startSocket(ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        info = new PlayerInfo(0, "");
        running = false;
        listening = false;
        is = null;
        os = null;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void bindBody(int id, Body body) {
        /*Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
         controls.put(id, body);*/
    }

    public void unbindBody(int id) {
        Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        ClientView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }

    public void send(PerformablePacket object) {
        try {
            os.writeObject(object.setId(info.getId()));
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

    public void register(String name) {
        send(new ConnectAction(new RegistrationForm(name)));
        info = (PlayerInfo) receive();
        System.out.println("Client: registration id received ("
                + "id: " + info.getId()
                + ", name: " + info.getName() + ")");
    }

    public void getModel() {
        System.out.println("Client: getting model");
        send(new GetModelAction());
        //((PerformablePacket) receive()).perform(ClientView.getInstance());
        //Model model = ((SerializableModel) receive()).deserialize(ClientView.getInstance());
    }

    public PlayerInfo getInfo() {
        return info;
    }

    private void startSocket(String ip) {
        System.out.println("Client: starting socket");
        try {
            inputSocket = new Socket(InetAddress.getByName(ip), 4243);
            ObjectOutputStream outs = new ObjectOutputStream(inputSocket.getOutputStream());
            outs.writeObject(new ConfirmAction(info.getId()));
        } catch (Exception ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (inputSocket != null && listening == false) {
            listening = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream objectInput = null;
                    PerformablePacket packet;
                    try {
                        objectInput = new ObjectInputStream(inputSocket.getInputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        PerformablePacket a = (PerformablePacket) objectInput.readObject();
                        Main.startClientView();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getModel();
                    running = true;
                    while (running) {
                        try {
                            packet = (PerformablePacket) objectInput.readObject();
                            int count = packet.getCount();
                            if (count - counter <= 1) {
                                System.out.println("Client: packet " + packet);
                                packet.perform(ClientView.getInstance());
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
