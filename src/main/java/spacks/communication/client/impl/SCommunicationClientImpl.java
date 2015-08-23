package spacks.communication.client.impl;

import spacks.communication.client.SCommunicationClient;
import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SAsynchronousPacket;
import spacks.communication.utilities.SPacket;
import spacks.communication.utilities.SSynchronousPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationClientImpl implements SCommunicationClient {

    private static final Logger logger = Logger.getLogger(SCommunicationClientImpl.class.getName());

    public Socket communicationSocket;
    public ObjectOutputStream os;
    public ObjectInputStream is;
    public Boolean running;

    public SCommunicationClientImpl() {
        communicationSocket = new Socket();
        os = null;
        is = null;
        running = false;
    }

    @Override
    public void connect(String ip, int port) throws IOException {
        logger.info("Client: connecting to: ip=" + ip + ", port=" + port);
        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
        bind(socket);
        logger.info("Client: connection established");

    }

    public void bind(Socket socket) throws IOException {
        communicationSocket = socket;
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        is = new ObjectInputStream(communicationSocket.getInputStream());
    }

    @Override
    public synchronized void send(SAction action) throws IOException {
        SAsynchronousPacket asynchronousPacket = new SAsynchronousPacket(action);
        os.reset();
        os.writeObject(asynchronousPacket);
    }

    @Override
    public SPacket receive() throws IOException, ClassNotFoundException {
        Serializable o = (Serializable) is.readObject();
        if (o instanceof SPacket) {
            return (SPacket) o;
        } else {
            throw new RuntimeException("Received packet is not of expected class");
        }
    }

    @Override
    public void start() {
        running = true;
        Runnable startup = new Runnable() {
            @Override
            public void run() {
                SPacket packet;
                int currentCount = 0;
                while (running) {
                    try {
                        packet = receive();
                        if (packet.isAsynchronous() || ((SSynchronousPacket) packet).checkSynchronization(currentCount)) {
                            logger.info("Client: packet " + packet);
                            packet.performAction();
                        }
                        if (!packet.isAsynchronous()) {
                            currentCount = ((SSynchronousPacket) packet).getCount();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
                logger.info("Client: terminating socket");
            }
        };

        new Thread(startup).start();
    }

    @Override
    public void stop() {
        running = false;
        try {
            communicationSocket.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Boolean isRunning() {
        return running;
    }
}
