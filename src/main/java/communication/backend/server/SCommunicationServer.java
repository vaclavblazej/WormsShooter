package communication.backend.server;

import communication.frontend.utilities.Performable;
import communication.frontend.utilities.SListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationServer {

    private Logger logger = Logger.getLogger(SCommunicationServer.class.getName());

    private SCommunicationServerCreateService connectionService;
    private Map<Integer, SCommunicationClientHandler> connections;
    private Integer connectionsCounter;
    private SListener listener;

    public SCommunicationServer(int port) throws IOException {
        this(port, new SListener());
    }

    public SCommunicationServer(int port, SListener listener) throws IOException {
        this.listener = listener;
        connectionService = new SCommunicationServerCreateService(port);
        connections = new HashMap<>(20);
        connectionsCounter = 0;
    }

    public boolean start() {
        return connectionService.start();
    }

    public void stop() {
        connectionService.stop();
    }

    public void send(int id, Performable action) throws IOException {
        connections.get(id).sendAsynchronous(action);
    }

    public void broadcast(Performable action) throws IOException {
        for (Integer connection : connections.keySet()) {
            send(connection, action);
        }
    }

    public int getNumberOfConnections() {
        return connections.size();
    }

    public void disconnect(int id) throws IOException {
        SCommunicationClientHandler c = connections.get(id);
        connections.remove(id);
        c.disconnect();
        listener.connectionRemoved(id);
    }

    private class SCommunicationServerCreateService implements Runnable {

        private int port;
        private ServerSocket serverSocket;
        private boolean running;

        SCommunicationServerCreateService(int port) throws IOException {
            this.port = port;
            serverSocket = null;
            running = false;
        }

        public boolean start() {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                Logger.getLogger(SCommunicationServer.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            new Thread(this).start();
            return true;
        }

        public void stop() {
            running = false;
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(SCommunicationServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            running = true;
            while (running) {
                try {
                    Socket s = serverSocket.accept();
                    logger.info("Server: new connection");
                    SCommunicationClientHandler h = new SCommunicationClientHandler(s);
                    new Thread(h).start();
                    while (connections.containsKey(connectionsCounter)) {
                        connectionsCounter = (connectionsCounter + 1) % 70000;
                    }
                    connections.put(connectionsCounter, h);
                    listener.connectionCreated(connectionsCounter);
                } catch (IOException ex) {
                    Logger.getLogger(SCommunicationServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
