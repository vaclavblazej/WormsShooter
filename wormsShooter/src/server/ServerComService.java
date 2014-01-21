package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Message;
import utilities.PlayerInfo;
import utilities.RegistrationForm;
import utilities.ServerInfo;

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
    private String serverName = Message.Server_name.cm();
    private Map<Integer, PlayerComInfo> players;
    //private Map<Integer, RegistrationForm> waitingRegistrations;

    private ServerComService() {
        players = new HashMap<>(20);
        //waitingRegistrations = new HashMap<>(30);
    }

    /*public void init() {
     new Thread(new Runnable() {
     @Override
     public void run() {
     while (true) {
     try {
     ServerSocket ss = new ServerSocket(4243);
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
     }*/
    public void setName(String name) {
        serverName = name;
    }

    public ServerInfo getServerInfo() {
        return new ServerInfo(serverName, getNumberOfPlayers());
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public PlayerInfo registerPlayer(RegistrationForm form) {
        int id = new Random().nextInt();
        InetAddress clientIp = getClientIp();
        // test if(clientIp != null) is possible
        PlayerComInfo com;
        try {
            com = new PlayerComInfo(clientIp, form.getSocket());
        } catch (IOException ex) {
            return null;
        }
        completeRegistration(id, com);
        //waitingRegistrations.put(id, form);
        return new PlayerInfo(id);
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

    public void completeRegistration(int id, PlayerComInfo pci) {
        players.put(id, pci);
        ServerCommunication.getInstance().bindBody(id, ServerPanel.getInstance().newBody());
    }

    private class PlayerComInfo {

        private Socket socket;

        public PlayerComInfo(InetAddress ip, int socket) throws IOException {
            System.out.println("player registered");
            System.out.println("IP: " + ip);
            System.out.println("socket: " + socket);
            this.socket = new Socket(ip, socket);
        }
    }
}
