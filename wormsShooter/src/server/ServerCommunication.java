package server;

import communication.client.ConnectAction;
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
import utilities.communication.PerformablePacket;
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

        private boolean run;
        private Socket socket;
        private int id;
        private ObjectInputStream is;
        private ObjectOutputStream os;

        CommunicationHandler(Socket socket) {
            System.out.println("Server: got client socket");
            this.socket = socket;
            this.run = true;
        }

        public Object receive() {
            try {
                return is.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                run = false;
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        public void run() {
            try {
                is = new ObjectInputStream(socket.getInputStream());
                os = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            ConnectAction action = ((ConnectAction) receive());
            action.perform(ServerView.getInstance());
            RegistrationForm form = action.getForm();
            PlayerInfo info = ServerComService.getInstance().registerPlayer(form);
            id = info.getId();
            try {
                os.writeObject(info);
            } catch (IOException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (run) {
                PerformablePacket packet = ((PerformablePacket) receive());
                System.out.println("Server: packet " + packet);
                packet.perform(ServerView.getInstance());
            }
        }
    }
}
