package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import utilities.Message;
import utilities.PlayerInfo;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.communication.RegistrationForm;
import utilities.communication.ServerInfo;

/**
 *
 * @author plach_000
 */
public class ServerComService {

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
                try {
                    ss = new ServerSocket(4243);
                } catch (IOException ex) {
                    Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    try {
                        Socket socket = ss.accept();
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        int id = Integer.parseInt(br.readLine());
                        if (ServerComService.getInstance().waitingRegistrations.containsKey(id)) {
                            ServerComService.getInstance().completeRegistration(id, new PlayerComInfo(socket));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public void send(int id, Packet packet) {
        try {
            packet.setCount(counter);
            players.get(id).objectOutput.writeObject(packet);
        } catch (IOException ex) {
            Logger.getLogger(ServerComService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void broadcast(Packet packet) {
        counter++;
        for (Integer player : players.keySet()) {
            send(player, packet);
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
        waitingRegistrations.put(id, form);
        return new PlayerInfo(id);
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

    private void completeRegistration(int id, PlayerComInfo pci) {
        broadcast(new PacketBuilder(Action.CONNECT, id).build());
        players.put(id, pci);
        ServerCommunication.getInstance().bindBody(id, ServerView.getInstance().newBody());
        send(id, new PacketBuilder(Action.CONFIRM, id).build());
    }

    private class PlayerComInfo {

        public Socket socket;
        public ObjectOutputStream objectOutput;

        private PlayerComInfo(Socket socket) throws IOException {
            this.socket = socket;
            OutputStream os = socket.getOutputStream();
            objectOutput = new ObjectOutputStream(os);
        }
    }
}
