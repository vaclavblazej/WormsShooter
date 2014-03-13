package server;

import communication.client.ConfirmAction;
import communication.server.ConnectServerAction;
import communication.server.DisconnectServerAction;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.PlayerInfo;
import utilities.communication.PerformablePacket;
import utilities.communication.RegistrationForm;
import utilities.communication.ServerInfo;
import utilities.properties.Message;

/**
 *
 * @author plach_000
 */
public final class ServerComService {

    private static ServerComService instance;

    public static ServerComService getInstance() {
        if (instance == null) {
            instance = new ServerComService();
        }
        return instance;
    }
    private String serverName = Message.SERVER_NAME.cm();
    private Map<Integer, PlayerComInfo> players;
    private Map<Integer, RegistrationForm> waitingRegistrations;
    private int counter;

    private ServerComService() {
        players = new HashMap<>(20);
        waitingRegistrations = new HashMap<>(30);
        init();
        counter = 0;
    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket ss = null;
                ObjectInputStream objectInput;
                PerformablePacket packet;
                int id;
                try {
                    ss = new ServerSocket(4243);
                } catch (IOException ex) {
                    Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    try {
                        Socket socket = ss.accept();
                        objectInput = new ObjectInputStream(socket.getInputStream());
                        packet = (PerformablePacket) objectInput.readObject();
                        id = packet.getId();
                        if (ServerComService.getInstance().waitingRegistrations.containsKey(id)) {
                            ServerComService.getInstance().completeRegistration(
                                    new PlayerComInfo(
                                    id,
                                    waitingRegistrations.get(id).getName(),
                                    socket));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public void send(int id, PerformablePacket pb) {
        try {
            pb.setCount(counter);
            players.get(id).objectOutput.writeObject(pb);
        } catch (IOException ex) {
            Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void broadcast(PerformablePacket pp) {
        counter++;
        for (Integer player : players.keySet()) {
            send(player, pp);
        }
    }

    public void setName(String name) {
        serverName = name;
    }

    public int getCounter() {
        return counter;
    }

    public ServerInfo getServerInfo() {
        return new ServerInfo(serverName, getNumberOfPlayers());
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public PlayerInfo registerPlayer(RegistrationForm form) {
        int id = new Random().nextInt();
        System.out.println("Server: registering player: " + id);
        waitingRegistrations.put(id, form);
        return new PlayerInfo(id, form.getName());
    }

    public Collection<Integer> getPlayers() {
        Collection<Integer> res = new ArrayList<>();
        for (Integer integer : players.keySet()) {
            res.add(integer);
        }
        return res;
    }

    private InetAddress getClientIp() {
        try {
            return Inet4Address.getByName(RemoteServer.getClientHost());
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void completeRegistration(PlayerComInfo pci) {
        int id = pci.getId();
        System.out.println("Server: client " + id + " registered");
        broadcast(new ConnectServerAction((PlayerInfo) pci));
        players.put(id, pci);
        ServerCommunication.getInstance().bindBody(id, ServerView.getInstance().newBody());
        System.out.println(ServerView.getInstance().getModel().getControls());
        send(id, new ConfirmAction(id));
    }

    public void disconnect(int id) {
        System.out.println("Server: client " + id + " disconnected");
        players.remove(id);
        ServerCommunication.getInstance().unbindBody(id);
        broadcast(new DisconnectServerAction(id));
    }

    private class PlayerComInfo extends PlayerInfo {

        public Socket socket;
        public ObjectOutputStream objectOutput;

        private PlayerComInfo(int id, String name, Socket socket) throws IOException {
            super(id, name);
            this.socket = socket;
            OutputStream os = socket.getOutputStream();
            objectOutput = new ObjectOutputStream(os);
        }
    }
}
