package main.windows;

import client.ClientCommunication;
import client.ClientView;
import main.Application;
import server.ServerCommunication;
import utilities.properties.Paths;
import utilities.spritesheets.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Custom menu class for a project.
 *
 * @author Václav Blažej
 */
public class MainFrame extends JFrame {

    // main window
    private JFrame mainFrame;
    private JPanel rootPanel;

    // menu card
    private JPanel menuCard;
    private JPanel margin;
    private JPanel menuCards;
    // main menu
    private JPanel mainMenuCard;
    private JButton singleplayerButton;
    private JButton multiplayerButton;
    private JButton settingsButton;
    private JButton exitButton;
    // multiplayer
    private JPanel multiplayerCard;
    private JButton joinMultiplayerButton;
    private JButton hostMultiplayerButton;
    private JButton backMultiplayerButton;
    // multiplayer - join
    private JPanel joinMultiplayerCard;
    private JTextField joinAddressTextField;
    private JTextField joinPortTextField;
    private JButton joinJoinButton;
    private JProgressBar joinProgressBar;
    private JButton backJoinButton;
    // multiplayer - host
    private JPanel hostMultiplayerCard;
    private JTextField hostPortTextField;
    private JButton hostHostButton;
    private JButton backHostButton;
    // settings
    private JPanel settingsCard;
    private JButton videoSettingsButton;
    private JButton audioSettingsButton;
    private JButton backSettingsButton;
    // settings - video
    private JPanel videoSettingsCard;
    private JButton backVideoSettingsButton;
    // settings - audio
    private JPanel audioSettingsCard;
    private JLabel soundAudioSettingSlider;
    private JSlider soundSlider;
    private JLabel musicAudioSettingSlider;
    private JSlider musicSlider;
    private JButton backAudioSettingsButton;
    // status bar
    private JPanel statusBar;
    private JLabel versionNumberLabel;

    // client card
    private JPanel clientCard;


    CardLayout mainCardLayout;
    CardLayout menuCardLayout;

    public MainFrame() {
        super("Test window");
        mainFrame = this;

        URL url = MainFrame.class.getResource(Paths.ICON_FILE.value());
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        // todo set inactive when this window is inactive

        mainCardLayout = (CardLayout) rootPanel.getLayout();
        menuCardLayout = (CardLayout) menuCards.getLayout();
        versionNumberLabel.setText(Application.version);
    }

    /**
     * Structure:
     * -mainMenu
     * |-singleplayer
     * |  |-xxx
     * |  \-yyy
     * |-multiplayer
     * |  |-join
     * |  \-host
     * \-settings
     * * |-video
     * * \-audio
     */
    private void createUIComponents() {
        // menu card
        menuCard = new BackgroundPanel(SpriteLoader.loadSprite("background", ".jpg"));

        // main menu
        singleplayerButton = new CustomButton(e -> {
            final int port = 4242;
            new ServerFrame();
            try {
                new ServerCommunication(port);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                ClientCommunication.getInstance().init("localhost", "4242");
                joinProgressBar.setValue(joinProgressBar.getMaximum());
                mainCardLayout.show(rootPanel, "clientCard");
                clientCard.requestFocusInWindow();
            });
        });
        multiplayerButton = new CustomButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));
        settingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "settingsCard"));
        exitButton = new CustomButton(e -> Application.exit());

        // singleplayer
        videoSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "videoSettingsCard"));
        audioSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "audioSettingsCard"));
        backSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "mainMenuCard"));

        // multiplayer
        joinMultiplayerButton = new CustomButton(e -> menuCardLayout.show(menuCards, "joinMultiplayerCard"));
        hostMultiplayerButton = new CustomButton(e -> menuCardLayout.show(menuCards, "hostMultiplayerCard"));
        backMultiplayerButton = new CustomButton(e -> menuCardLayout.show(menuCards, "mainMenuCard"));

        // multiplayer - join
        joinJoinButton = new CustomButton(e -> {
            ClientCommunication.getInstance().init(joinAddressTextField.getText(), joinPortTextField.getText());
            joinProgressBar.setValue(joinProgressBar.getMaximum());
            mainCardLayout.show(rootPanel, "clientCard");
            clientCard.requestFocusInWindow();
        });
        backJoinButton = new CustomButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));

        // multiplayer - host
        hostHostButton = new CustomButton(e -> {
            final int port = Integer.parseInt(hostPortTextField.getText());
            new ServerFrame();
            try {
                new ServerCommunication(port);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        backHostButton = new CustomButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));

        // settings
        backVideoSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "settingsCard"));
        backAudioSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "settingsCard"));


        // client card
        clientCard = ClientView.getInstance();
    }
}
