package client.menu;

import main.Application;
import utilities.AbstractDialog;
import utilities.GridBagConstraintsBuilder;
import utilities.ValidatedTextField;
import utilities.properties.Message;
import utilities.properties.Paths;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Václav Blažej
 */
public class GameLauncher extends AbstractDialog {

    private JCheckBox newServer;
    private ValidatedTextField serverPort;
    private ValidatedTextField address;
    private ValidatedTextField clientPort;

    public GameLauncher(JFrame owner) {
        super(owner, Message.LAUNCHER_WINDOW_TITLE.value());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newServer = new JCheckBox();
        newServer.setSelected(true);
        serverPort = new ValidatedTextField(this);
        address = new ValidatedTextField(this);
        clientPort = new ValidatedTextField(this);
        serverPort.setText(Paths.SERVER_PORT_INITIAL.value());
        address.setText(Paths.ADDRESS_INITIAL.value());
        clientPort.setText(Paths.CLIENT_PORT_INITIAL.value());
        getContent().setLayout(new GridBagLayout());
        int i = 0;
        getContent().add(new JLabel(Message.CREATE_SERVER.value() + ": "), new GridBagConstraintsBuilder().setY(i).build());
        getContent().add(newServer, new GridBagConstraintsBuilder().setY(i).setXRel().build());
        i++;
        getContent().add(new JLabel(Message.SERVER_PORT.value() + ": "), new GridBagConstraintsBuilder().setY(i).build());
        getContent().add(serverPort, new GridBagConstraintsBuilder().setY(i).setXRel().build());
        i++;
        getContent().add(new JLabel(Message.ADDRESS.value() + ": "), new GridBagConstraintsBuilder().setY(i).build());
        getContent().add(address, new GridBagConstraintsBuilder().setY(i).setXRel().build());
        i++;
        getContent().add(new JLabel(Message.CLIENT_PORT.value() + ": "), new GridBagConstraintsBuilder().setY(i).build());
        getContent().add(clientPort, new GridBagConstraintsBuilder().setY(i).setXRel().build());
        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void okAction() throws Exception {
        if (newServer.isSelected()) {
            Application.startServer(Integer.parseInt(serverPort.getText()));
        }
        Application.startClient(address.getText(), clientPort.getText());
    }

    @Override
    public boolean validateDialog() {
        blockOk();
        try {
            InetAddress.getByName(address.getText());
        } catch (UnknownHostException ex) {
            error(Message.ADDRESS_ERROR_MESSAGE.value());
            return false;
        }
        try {
            Integer.parseInt(clientPort.getText());
        } catch (NumberFormatException ex) {
            error(Message.SOCKET_ERROR_MESSAGE.value());
            return false;
        }
        clearError();
        return true;
    }
}
