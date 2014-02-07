package client;

import client.menu.GameWindowItemBar;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Body;
import objects.items.ItemFactory;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

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
    private Socket serverSocket;
    private PlayerInfo info;
    private boolean listening;
    private Socket clientSocket;
    private Map<Integer, Body> controls;
    private int counter;
    private ItemFactory factory;
    private volatile boolean running;

    public void init(String ip, String port) {
        System.out.println("strarting client");
    }

    public void sendAction(PacketBuilder builder) {
        try {
            new ObjectOutputStream(serverSocket.getOutputStream()).writeObject(builder.build());
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    Point p;
                    while (running) {
                        try {
                            packet = (Packet) objectInput.readObject();
                            Action type = packet.getAction();
                            int count = packet.getCount();
                            int id = packet.getId();;
                            if (count - counter <= 1) {
                                switch (type) {

                                }
                            }/* else {
                             getModel();
                             }*/
                            counter = count;
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
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
