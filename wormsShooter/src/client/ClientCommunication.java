package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.ControlsEnum;
import server.ServerComm;
import utilities.PlayerInfo;
import utilities.RegistrationForm;
import utilities.SerializableBufferedImage;

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
    ServerSocket clientSocket;

    private ClientCommunication() {
    }

    public void init(String ip) throws NotBoundException, MalformedURLException, RemoteException {
        serverComm = (ServerComm) Naming.lookup("//" + ip + "/" + ServerComm.class.getSimpleName());
        // todo socket should be in settings
        int socketNumber = 4243;
        try {
            clientSocket = new ServerSocket(socketNumber);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        info = serverComm.register(new RegistrationForm(socketNumber));
        if (info == null) {
            GameWindow.getInstance().showError(new Exception("Server could not connect to you"));
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
