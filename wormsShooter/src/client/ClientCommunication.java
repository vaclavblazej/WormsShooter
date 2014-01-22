package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import objects.ControlsEnum;
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
    Socket clientSocket;

    private ClientCommunication() {
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
}
