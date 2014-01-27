package client;

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
import objects.ControlsEnum;
import objects.TestBody;
import server.Packet;
import server.ServerComService;
import server.ServerComm;
import utilities.Materials;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Model;
import utilities.communication.RegistrationForm;

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

    private ClientCommunication() {
        listening = false;
        controls = new HashMap<>(20);
    }

    public void init(String ip, String socket) throws NotBoundException, MalformedURLException, RemoteException {
        serverComm = (ServerComm) Naming.lookup("//" + ip + ":" + socket + "/" + ServerComm.class.getSimpleName());
        // todo socket should be in settings
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

    public void sendAction(Action action) throws RemoteException {
        serverComm.sendAction(info.getId(), action);
    }

    public void getModel() throws RemoteException {
        System.out.println("getModel call");
        Model model = serverComm.getMode(info.getId()).deserialize(MainPanel.getInstance());
        MainPanel.getInstance().setModel(model);
        controls = model.getControls();
        counter = model.getCounter();
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
                                    case Move_left:
                                    case Move_right:
                                    case Move_stop:
                                    case Move_jump:
                                        i = packet.getId();
                                        controls.get(i).setPosition(packet.getPoint(0));
                                        controls.get(i).setVelocity(packet.getDoublePoint(0));
                                        controls.get(i).control(type);
                                        break;
                                    case Mine:
                                        MainPanel.getInstance().change(packet.getPoint(0), Materials.Dirt);
                                        break;
                                    case Connect:
                                        i = packet.getId();
                                        if (i == 0) {
                                            createPlayer(info.getId());
                                        }
                                        break;
                                    case Confirm:
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
