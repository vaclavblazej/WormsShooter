package server;

import dynamic.communication.DynamicLoader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Body;
import utilities.PlayerInfo;
import utilities.communication.Packet;
import utilities.communication.RegistrationForm;

/**
 *
 * @author plach_000
 */
public class ServerCommunication {

    private static ServerCommunication instance;

    public static ServerCommunication getInstance() {
        if (instance == null) {
            instance = new ServerCommunication();
        }
        return instance;
    }
    private ServerSocket serverSocket;
    private boolean running;

    private ServerCommunication() {
        serverSocket = null;
        running = false;
    }

    public void init(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        run();
    }

    public void run() {
        System.out.println("Server: starting");
        new Thread(new Runnable() {
            @Override
            public void run() {
                running = true;
                while (running) {
                    try {
                        Socket s = serverSocket.accept();
                        new Thread(new CommunicationHandler(s)).start();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public void shutdown() {
        running = false;
    }

    public void bindBody(int id, Body body) {
        Map<Integer, Body> controls = ServerView.getInstance().getModel().getControls();
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        Map<Integer, Body> controls = ServerView.getInstance().getModel().getControls();
        ServerView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }

    private class CommunicationHandler implements Runnable {

        private Socket socket;
        private int id;

        CommunicationHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("Server: Client handler started");
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                PlayerInfo registerPlayer = ServerComService.getInstance()
                        .registerPlayer((RegistrationForm) ((Packet) is.readObject()).get(0));
                id = registerPlayer.getId();
                os.writeObject(registerPlayer);
                while (running) {
                    Packet p = ((Packet) is.readObject());
                    //System.out.println("Server: " + p.getId() + " " + p.getAction());
                    DynamicLoader.getInstance().get(p.getAction()).performServer(os, p, ServerView.getInstance());
                }
            } catch (IOException | ClassNotFoundException ex) {
                ServerComService.getInstance().disconnect(id);
                //Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
