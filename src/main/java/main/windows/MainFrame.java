package main.windows;

import client.ChatLog;
import client.ClientCommunication;
import client.ClientView;
import client.menu.Settings;
import main.Application;
import server.ServerCommunication;
import utilities.BindableButton;
import utilities.Controls;
import utilities.ControlsEnum;
import utilities.communication.RegistrationForm;
import utilities.properties.Paths;
import utilities.spritesheets.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom menu class for a project.
 *
 * @author V�clav Bla�ej
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
    private JButton keysSettingsButton;
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
    // settings - keys
    private JPanel keysSettingsCard;
    private JPanel keyBindingPanel;
    private JButton applyKeysSettingsButton;
    // status bar
    private JPanel statusBar;

    private JLabel versionNumberLabel;
    // client card
    private JPanel clientCard;
    private JPanel messagePanel;
    private JPanel chatPanel;
    private JTextField messageTextField;
    private JButton messageSendButton;
    private JPanel minimapPanel;


    CardLayout mainCardLayout;
    CardLayout menuCardLayout;

    List<BindableButton> bindableButtons;

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

        mainFrame.addComponentListener(ClientView.getInstance());

        for (BindableButton button : bindableButtons) button.refreshText();
    }

    /**
     * Structure:
     * -mainMenu
     * |-singleplayer
     * |-multiplayer
     * |  |-join
     * |  \-host
     * \-settings
     * * |-video
     * * |-audio
     * * \-keys
     */
    private void createUIComponents() {
        // menu card
        menuCard = new BackgroundPanel(SpriteLoader.loadSprite("background", ".jpg"));

        // main menu
        singleplayerButton = new CustomSoundButton(e -> {
            final int port = 4242;
            new ServerFrame();
            try {
                new ServerCommunication(port);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                ClientCommunication.getInstance().init("localhost", port, new RegistrationForm("Alpha"));
                joinProgressBar.setValue(joinProgressBar.getMaximum());
                mainCardLayout.show(rootPanel, "clientCard");
                clientCard.requestFocusInWindow();
            });
        });
        multiplayerButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));
        settingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "settingsCard"));
        exitButton = new CustomSoundButton(e -> Application.exit());

        // singleplayer
        videoSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "videoSettingsCard"));
        audioSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "audioSettingsCard"));
        keysSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "keysSettingsCard"));
        backSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "mainMenuCard"));

        // multiplayer
        joinMultiplayerButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "joinMultiplayerCard"));
        hostMultiplayerButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "hostMultiplayerCard"));
        backMultiplayerButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "mainMenuCard"));

        // multiplayer - join
        joinJoinButton = new CustomSoundButton(e -> {
            final String address = joinAddressTextField.getText();
            final int port = Integer.parseInt(joinPortTextField.getText());
            final RegistrationForm form = new RegistrationForm("Beta");
            ClientCommunication.getInstance().init(address, port, form);
            joinProgressBar.setValue(joinProgressBar.getMaximum());
            mainCardLayout.show(rootPanel, "clientCard");
            clientCard.requestFocusInWindow();
        });
        backJoinButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));

        // multiplayer - host
        hostHostButton = new CustomSoundButton(e -> {
            final int port = Integer.parseInt(hostPortTextField.getText());
            new ServerFrame();
            try {
                new ServerCommunication(port);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        backHostButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));

        // settings
        backVideoSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "settingsCard"));
        backAudioSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "settingsCard"));

        // settings - keys
        final Controls controls = Settings.getInstance().getControls();
        final GridLayout keyBindingLayout = new GridLayout(0, 2);
        keyBindingPanel = new JPanel(keyBindingLayout);
        List<ControlsEnum> bindableControls = ControlsEnum.getBindableControls();
        bindableButtons = new ArrayList<>();
        for (ControlsEnum control : bindableControls) {
            final JLabel label = new JLabel(control.getName());
            final BindableButton e = new BindableButton(control, controls.get(control));
            keyBindingPanel.add(label);
            keyBindingPanel.add(e);
            bindableButtons.add(e);
        }
        applyKeysSettingsButton = new CustomSoundButton(e -> {
            for (BindableButton bindableButton : bindableButtons) {
                ControlsEnum control = bindableButton.getControl();
                controls.rebind(control, bindableButton.getKeyCode());
            }
            final Settings settings = Settings.getInstance();
            settings.saveSettings();
            menuCardLayout.show(menuCards, "settingsCard");
        });

        // client card
        final ClientView clientView = ClientView.getInstance();
        clientCard = clientView;
        minimapPanel = clientView.getMinimapView();
        chatPanel = ChatLog.getInstance();
        chatPanel.setBackground(new Color(100, 100, 255, 10));

        ChatLog.getInstance().log("Hello world!");

        final ChatInputPanel chatPanelInstance = ChatInputPanel.getInstance();
        messagePanel = chatPanelInstance;
        messageTextField = chatPanelInstance.getField();
        messageSendButton = chatPanelInstance.getButton();
    }
}
