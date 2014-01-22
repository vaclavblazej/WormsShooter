package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import server.ServerComService;
import server.ServerComm;
import utilities.PlayerInfo;
import utilities.RegistrationForm;

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
            clientSocket.getOutputStream().write(new String("" + info.getId() + "\n").getBytes());
        } catch (UnknownHostException ex) {
            GameWindow.getInstance().showError(new Exception("Could not connect to the Server"));
        } catch (IOException ex) {
            GameWindow.getInstance().showError(new Exception("IOException in ClientCommunication init"));
        }
        TestBody b = MainPanel.getInstance().newBody();
        bindBody(info.getId(), b);
        MainPanel.getInstance().setMyBody(b);
        startSocket();
    }

    public Dimension getSize() throws RemoteException {
        return serverComm.getSize();
    }

    public Color getPixel(int x, int y) throws RemoteException {
        return serverComm.getPixel(x, y);
    }

    public BufferedImage getMap() throws RemoteException {
        return serverComm.getMapSerializable().getImage();
    }

    public void sendAction(ControlsEnum action, boolean on) throws RemoteException {
        serverComm.sendAction(info.getId(), action, on);
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
                    InputStreamReader stream;
                    BufferedReader br = null;
                    try {
                        stream = new InputStreamReader(clientSocket.getInputStream());
                        br = new BufferedReader(stream);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    while (true) {
                        try {
                            String str = br.readLine();
                            String[] split = str.split(" ");
                            System.out.println(str);
                            //System.out.println(Integer.parseInt(split[0]) + " " + );
                            int i = Integer.parseInt(split[0]);
                            if (controls.containsKey(i)) {
                                if (new Boolean(split[2])) {
                                    controls.get(i).controlOn(ControlsEnum.valueOf(split[1]));
                                } else {
                                    controls.get(i).controlOff(ControlsEnum.valueOf(split[1]));
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        }
    }
}
