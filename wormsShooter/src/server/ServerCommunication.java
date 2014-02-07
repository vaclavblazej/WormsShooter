package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import utilities.communication.Action;
import utilities.communication.Packet;

/**
 *
 * @author plach_000
 */
public class ServerCommunication implements Runnable {

    private static ServerCommunication instance;

    public static ServerCommunication getInstance() {
        if (instance == null) {
            instance = new ServerCommunication();
        }
        return instance;
    }
    private ServerSocket ss;
    private boolean running;
    private Map<Action, Performable> performables;

    private ServerCommunication() {
    }

    public void init(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Socket s = ss.accept();
                new Thread(new CommunicationHandler(s)).start();
            } catch (IOException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void shutdown() {
        running = false;
    }

    private class CommunicationHandler implements Runnable {

        private Socket socket;

        public CommunicationHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                while (running) {
                    Packet p = ((Packet) ois.readObject());
                    performables.get(p.getAction()).perform(socket, p);
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void load() {
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            for (Action action : Action.values()) {
                Class<?> loader = classLoader.loadClass("dynamic.communication." + action.name().toLowerCase());
                Packet dc = (Packet) loader.newInstance();
                performables.put(action, dc);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
