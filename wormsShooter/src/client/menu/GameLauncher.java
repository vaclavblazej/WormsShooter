package client.menu;

import client.ClientCommunication;
import client.ClientView;
import java.awt.GridBagLayout;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import main.Main;
import utilities.AbstractDialog;
import utilities.GBCBuilder;
import utilities.Message;
import utilities.ValidatedTF;

/**
 *
 * @author Skarab
 */
public class GameLauncher extends AbstractDialog {

    private JCheckBox newServer;
    private ValidatedTF serverSocket;
    private ValidatedTF address;
    private ValidatedTF clientSocket;

    public GameLauncher(JFrame owner) {
        super(owner, Message.LAUNCHER_WINDOW_TITLE.cm());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newServer = new JCheckBox();
        newServer.setSelected(true);
        serverSocket = new ValidatedTF(this);
        address = new ValidatedTF(this);
        clientSocket = new ValidatedTF(this);
        serverSocket.setText(Message.SERVER_PORT_INITIAL.cm());
        address.setText(Message.ADDRESS_INITIAL.cm());
        clientSocket.setText(Message.CLIENT_PORT_INITIAL.cm());
        getContent().setLayout(new GridBagLayout());
        int i = 0;
        getContent().add(new JLabel(Message.CREATE_SERVER.cm() + ": "), new GBCBuilder().setY(i).build());
        getContent().add(newServer, new GBCBuilder().setY(i).setXRel().build());
        i++;
        getContent().add(new JLabel(Message.SERVER_PORT.cm() + ": "), new GBCBuilder().setY(i).build());
        getContent().add(serverSocket, new GBCBuilder().setY(i).setXRel().build());
        i++;
        getContent().add(new JLabel(Message.ADDRESS.cm() + ": "), new GBCBuilder().setY(i).build());
        getContent().add(address, new GBCBuilder().setY(i).setXRel().build());
        i++;
        getContent().add(new JLabel(Message.CLIENT_PORT.cm() + ": "), new GBCBuilder().setY(i).build());
        getContent().add(clientSocket, new GBCBuilder().setY(i).setXRel().build());
        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void okAction() throws Exception {
        try {
            if (newServer.isSelected()) {
                Main.startServer(Integer.parseInt(serverSocket.getText()));
            }
            ClientCommunication.getInstance().init(address.getText(), clientSocket.getText());
            ClientView.getInstance().init();
        } catch (NotBoundException ex) {
            Logger.getLogger(GameLauncher.class.getName()).log(Level.SEVERE, null, ex);
            //todo exception dialog
        } catch (MalformedURLException ex) {
            Logger.getLogger(GameLauncher.class.getName()).log(Level.SEVERE, null, ex);
            //todo exception dialog
        } catch (RemoteException ex) {
            Logger.getLogger(GameLauncher.class.getName()).log(Level.SEVERE, null, ex);
            //todo exception dialog
        }
    }

    @Override
    public boolean validateDialog() {
        blockOk();
        try {
            InetAddress.getByName(address.getText());
        } catch (UnknownHostException ex) {
            error(Message.ADDRESS_ERROR_MESSAGE.cm());
            return false;
        }
        try {
            Integer.parseInt(clientSocket.getText());
        } catch (NumberFormatException ex) {
            error(Message.SOCKET_ERROR_MESSAGE.cm());
            return false;
        }
        clearError();
        return true;
    }
}
