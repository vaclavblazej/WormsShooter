package spacks.communication.server.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Štìpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationServerCreateService implements Runnable {

    private static final Logger logger = Logger.getLogger(SCommunicationServerCreateService.class.getName());

    private static final Integer MAXIMUM_CONNECTIONS = 70007;
    private Integer connectionsCounter;
    private int port;
    private ServerSocket serverSocket;
    private boolean running;
    private Map<Integer, SCommunicationClientHandler> connections;

    public SCommunicationServerCreateService(int port, Map<Integer, SCommunicationClientHandler> connections) throws IOException {
        this.port = port;
        this.connections = connections;
        serverSocket = null;
        running = false;
        connectionsCounter = 0;
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

    public boolean isRunning() {
        return running;
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
            } catch (SocketException ex) {
                logger.info("Server: connection closed");
                break;
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}