package communication.backend.server;

import communication.frontend.utilities.SAction;
import communication.frontend.utilities.SListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationServer {

    private static final transient Logger logger = Logger.getLogger(SCommunicationServer.class.getName());

    private static Integer MAXIMUM_CONNECTIONS = 70007;
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

    public void send(int id, SAction action) throws IOException {
        final SCommunicationClientHandler handler = connections.get(id);
        if (handler != null) {
            handler.sendAsynchronous(action);
        } else {
            throw new RuntimeException("Asking for handler for client with invalid id=" + id);
        }
    }

    public void broadcast(SAction action) throws IOException {
        for (Integer connection : connections.keySet()) {
            send(connection, action);
        }
    }

    public Boolean isRunning(){
        return connectionService.running;
    }

    public int getNumberOfConnections() {
        return connections.size();
    }

    public void disconnectClient(int id) throws IOException {
        SCommunicationClientHandler handler = connections.get(id);
        connections.remove(id);
        handler.disconnect();
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

        /**
         * Start accepting connections from clients.
         */
        public boolean start() {
            running = true;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
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
                logger.log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Socket s = serverSocket.accept();
                    logger.info("Server: new connection");
                    SCommunicationClientHandler h = new SCommunicationClientHandler(s);
                    new Thread(h).start();
                    if (connections.size() >= MAXIMUM_CONNECTIONS) {
                        logger.info("Server: server is full");
                        continue;
                    }
                    while (connections.containsKey(connectionsCounter)) {
                        connectionsCounter = (connectionsCounter + 1) % MAXIMUM_CONNECTIONS;
                    }
                    connections.put(connectionsCounter, h);
                    listener.connectionCreated(connectionsCounter);
                } catch (SocketException ex) {
                    logger.info("Server: connection closed");
                    break;
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
