package communication.backend.server;

import communication.backend.utilities.SAsynchronousPacket;
import communication.backend.utilities.SPacket;
import communication.backend.utilities.SSynchronousPacket;
import communication.frontend.utilities.SAction;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A SCommunicationClientHandler deals with communication with a client from a server side.
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationClientHandler implements Runnable {

    private static final transient Logger logger = Logger.getLogger(SCommunicationClientHandler.class.getName());

    private boolean running;
    private Socket communicationSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private SAsynchronousPacket asynchronousPacket;
    private SSynchronousPacket synchronousPacket;

    public SCommunicationClientHandler(Socket socket) throws IOException {
        this.communicationSocket = socket;
        this.running = true;
        is = new ObjectInputStream(communicationSocket.getInputStream());
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        asynchronousPacket = new SAsynchronousPacket(null);
        synchronousPacket = new SSynchronousPacket(null);
        logger.info("Server: connection to a new client");
    }

    public void disconnect() throws IOException {
        communicationSocket.close();
        running = false;
    }

    public void sendAsynchronous(SAction action) throws IOException {
        asynchronousPacket.setAction(action);
        sendObject(asynchronousPacket);
    }

    public synchronized void sendSynchronous(SAction action) throws IOException {
        synchronousPacket.setAction(action);
        sendObject(synchronousPacket);
        synchronousPacket.incrementCount();
    }

    private synchronized void sendObject(SPacket o) throws IOException {
        os.reset();
        os.writeObject(o);
    }

    private SPacket receive() throws IOException, ClassNotFoundException {
        while (true) {
            final Object o = is.readObject();
            if (o instanceof SPacket) {
                return (SPacket) o;
            }
            logger.log(Level.WARNING, "The server received faulty data: " + o.toString());
        }
    }

    @Override
    public void run() {
        SPacket packet;
        while (running) {
            try {
                packet = receive();
                logger.info("Server: packet " + packet + toString());
                packet.performAction();
            } catch (EOFException ex) {
                logger.info("Server: closing communicatino with a client");
                break;
            } catch (ClassNotFoundException | IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
