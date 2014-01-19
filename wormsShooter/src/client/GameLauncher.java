/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JButton;
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
    private JButton ok;

    public GameLauncher() {
        super(Message.Launcher_window_title.cm());
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.exit();
            }
        };
        addWindowListener(exitListener);
        setVisible(true);
        setResizable(false);

        address = new ValidatedTF(this);
        socket = new ValidatedTF(this);
        address.setText(Message.Address_initial.cm());
        socket.setText(Message.Socket_initial.cm());
        setLayout(new GridBagLayout());
        getContent().add(new JLabel(Message.Address.cm() + ": "), new GBCBuilder().setY(1).build());
        getContent().add(address, new GBCBuilder().setY(1).setXRel().build());
        getContent().add(new JLabel(Message.Socket.cm() + ": "), new GBCBuilder().setY(2).build());
        getContent().add(socket, new GBCBuilder().setY(2).setXRel().build());
        pack();

        setLocationRelativeTo(null);
    }

    @Override
    public void cancelAction() throws Exception {
        super.cancelAction();
        Main.exit();
    }

    @Override
    public void okAction() throws Exception {

        try {
            if (!validateDialog()) {
                throw new Exception("Input data are not valid");
            }
            try {
                ClientCommunication.getInstance().init(address.getText() + ":" + socket.getText());
                Main.startGame();
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(GameLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception le) {
            GameWindow.getInstance().showError(le.toString());
        }
    }

    @Override
    public boolean validateDialog() {
        blockOk();
        try {
            InetAddress.getByName(address.getText());
        } catch (UnknownHostException ex) {
            error("Address format error");
            return false;
        }
        try {
            Integer.parseInt(socket.getText());
        } catch (NumberFormatException ex) {
            error("Socket format error");
            return false;
        }
        clearError();
        return true;
    }
}
