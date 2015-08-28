package main.windows;

import client.ClientCommunication;
import client.ClientView;
import main.Application;
import utilities.properties.Paths;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Custom menu class for a project.
 *
 * @author Václav Blažej
 */
public class MainFrame extends JFrame {

    private JFrame mainFrame;
    private JPanel rootPanel;
    private JButton singleplayerButton;
    private JButton multiplayerButton;
    private JButton settingsButton;
    private JButton exitButton;
    private JPanel menuCards;
    private JButton videoSettingsButton;
    private JButton audioSettingsButton;
    private JButton backSettingsButton;
    private JButton joinMultiplayerButton;
    private JButton hostMultiplayerButton;
    private JButton backMultiplayerButton;
    private JPanel clientCard;
    private JButton backHostButton;
    private JButton backVideoSettingsButton;
    private JButton backAudioSettingsButton;
    private JLabel versionNumberLabel;
    private JPanel margin;
    private JPanel mainMenuCard;
    private JPanel settingsCard;
    private JPanel multiplayerCard;
    private JPanel joinMultiplayerCard;
    private JTextField joinAddressTextField;
    private JTextField joinPortTextField;
    private JButton joinJoinButton;
    private JButton backJoinButton;
    private JPanel hostMultiplayerCard;
    private JButton hostHostButton;
    private JPanel videoSettingsCard;
    private JPanel audioSettingsCard;
    private JSlider slider1;
    private JLabel soundAudioSettingSlider;
    private JLabel musicAudioSettingSlider;
    private JSlider slider2;
    private JPanel statusBar;
    private JPanel menuCard;
    private JTextField hostPortTextField;
    private JProgressBar joinProgressBar;


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
        // main menu
        singleplayerButton = new CustomButton();
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
        hostHostButton = new CustomButton(e -> Application.startServer(Integer.parseInt(hostPortTextField.getText())));
        backHostButton = new CustomButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));

        // settings
        backVideoSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "settingsCard"));
        backAudioSettingsButton = new CustomButton(e -> menuCardLayout.show(menuCards, "settingsCard"));

        clientCard = ClientView.getInstance();
    }
}
