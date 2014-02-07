package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import utilities.PlayerInfo;
import utilities.communication.Action;
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
    private Map<Action, Performable> performables;

    private ServerCommunication() {
        serverSocket = null;
        running = false;
        performables = null;
    }

    public void init(int port) {
        performables = new HashMap<>(Action.values().length);
        load();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        run();
    }

    public void load() {
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            for (Action action : Action.values()) {
                Class<?> loader = classLoader.loadClass("dynamic.communication." + action.name().toLowerCase());
                //System.out.println("Dynamic loading: " + loader.getCanonicalName());
                Packet dc = (Packet) loader.newInstance();
                performables.put(action, dc);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private class CommunicationHandler implements Runnable {

        private Socket socket;

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
                os.writeObject(registerPlayer);
                while (running) {
                    Packet p = ((Packet) is.readObject());
                    performables.get(p.getAction()).perform(os, p, ServerView.getInstance().getModel());
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
