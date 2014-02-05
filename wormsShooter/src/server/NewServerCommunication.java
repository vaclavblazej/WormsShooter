/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author plach_000
 */
public class NewServerCommunication implements Runnable {

    public static final int port = 4242;
    private static NewServerCommunication instance;
    private ServerSocket ss;

    public static NewServerCommunication getInstanace() {
        if (instance == null) {
            instance = new NewServerCommunication();
        }
        return instance;
    }

    public static void init() throws IOException {
        ServerSocket ss = new ServerSocket(port);
    }

    private NewServerCommunication() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket s = ss.accept();
                new Thread(new CommunicationHandler(s)).start();
            } catch (IOException ex) {
                Logger.getLogger(NewServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                ((Performable) ois.readObject()).perform(socket);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(NewServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
