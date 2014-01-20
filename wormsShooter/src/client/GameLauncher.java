package client;

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

    private ValidatedTF address;
    private ValidatedTF socket;
    private JCheckBox newServer;

    public GameLauncher(JFrame owner) {
        super(owner, Message.Launcher_window_title.cm());
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.exit();
            }
        };
        addWindowListener(exitListener);

        newServer = new JCheckBox();
        newServer.setSelected(true);
        address = new ValidatedTF(this);
        socket = new ValidatedTF(this);
        address.setText(Message.Address_initial.cm());
        socket.setText(Message.Socket_initial.cm());
        getContent().setLayout(new GridBagLayout());
        getContent().add(new JLabel(Message.Create_server.cm() + ": "), new GBCBuilder().setY(0).build());
        getContent().add(newServer, new GBCBuilder().setY(0).setXRel().build());
        getContent().add(new JLabel(Message.Address.cm() + ": "), new GBCBuilder().setY(1).build());
        getContent().add(address, new GBCBuilder().setY(1).setXRel().build());
        getContent().add(new JLabel(Message.Socket.cm() + ": "), new GBCBuilder().setY(2).build());
        getContent().add(socket, new GBCBuilder().setY(2).setXRel().build());
        pack();
        setResizable(false);
        setVisible(true); // dialog locks after setVisible(true) is called, so it must be last
    }

    @Override
    public void cancelAction() throws Exception {
        super.cancelAction();
        Main.exit();
    }

    @Override
    public void okAction() throws Exception {
        try {
            if (newServer.isSelected()) {
                Main.startServer();
            }
            ClientCommunication.getInstance().init(address.getText() + ":" + socket.getText());
            MainPanel.getInstance().init();
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
            error(Message.Address_error_message.cm());
            return false;
        }
        try {
            Integer.parseInt(socket.getText());
        } catch (NumberFormatException ex) {
            error(Message.Socket_error_message.cm());
            return false;
        }
        clearError();
        return true;
    }
}
