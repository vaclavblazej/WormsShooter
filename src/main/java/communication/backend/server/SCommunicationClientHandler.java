package communication.backend.server;

import communication.backend.utilities.SAsynchronousPacket;
import communication.backend.utilities.SPacket;
import communication.backend.utilities.SSynchronousPacket;
import communication.frontend.utilities.Performable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationClientHandler implements Runnable {

    private Logger logger = Logger.getLogger(SCommunicationClientHandler.class.getName());

    private boolean running;
    private Socket communicationSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private SAsynchronousPacket asynchronousPacket;
    private SSynchronousPacket synchronousPacket;

    public SCommunicationClientHandler(Socket socket) throws IOException {
        logger.info("Server: got client socket");
        this.communicationSocket = socket;
        this.running = true;
        is = new ObjectInputStream(communicationSocket.getInputStream());
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        asynchronousPacket = new SAsynchronousPacket(null);
        synchronousPacket = new SSynchronousPacket(null);
    }

    public void disconnect() throws IOException {
        communicationSocket.close();
    }

    public void sendAsynchronous(Performable action) throws IOException {
        asynchronousPacket.setAction(action);
        sendObject(asynchronousPacket);
    }

    public synchronized void sendSynchronous(Performable action) throws IOException {
        synchronousPacket.setAction(action);
        sendObject(synchronousPacket);
        synchronousPacket.incrementCount();
    }
    
    private synchronized void sendObject(SPacket o) throws IOException {
        os.reset();
        os.writeObject(o);
    }

    private SPacket receive() throws IOException, ClassNotFoundException {
        return (SPacket) is.readObject();
    }

    @Override
    public void run() {
        SPacket packet;
        while (running) {
            try {
                packet = receive();
                logger.info("Server: packet " + packet);
                packet.performAction();
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(SCommunicationClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
