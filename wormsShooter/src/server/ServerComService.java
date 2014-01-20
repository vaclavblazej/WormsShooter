/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.PlayerInfo;
import utilities.RegistrationForm;
import utilities.ServerInfo;

/**
 *
 * @author plach_000
 */
public class ServerComService {
    
    private static ServerComService instance;
    
    private String serverName = "My Server";
    private Map<Integer, PlayerComInfo> players;
    private Map<Integer, RegistrationForm> waitingRegistrations;
    
    public static ServerComService getInstance() {
        if (instance == null) instance = new ServerComService();
        return instance;
    }
    
    public void init() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
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
    }

    private ServerComService() {
        players = new HashMap<>();
        waitingRegistrations = new HashMap<>();
    }
    
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
        Random r = new Random();
        int id = r.nextInt();
        waitingRegistrations.put(id, form);
        PlayerInfo playerInfo = new PlayerInfo(id);
        return playerInfo;
    }
    
    public void completeRegistration(int id, PlayerComInfo pci) {
        players.put(id, pci);
    }
    
    private class PlayerComInfo {
        private Socket socket;

        public PlayerComInfo(Socket socket) {
            this.socket = socket;
        }

        
    }
    

}
