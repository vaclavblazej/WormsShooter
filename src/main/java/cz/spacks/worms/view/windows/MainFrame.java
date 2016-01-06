package cz.spacks.worms.view.windows;

import cz.spacks.worms.view.client.ChatLog;
import cz.spacks.worms.controller.client.ClientCommunicationInternet;
import cz.spacks.worms.controller.client.ClientCommunicationLocal;
import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.view.client.menu.GameWindowItemBar;
import cz.spacks.worms.controller.Settings;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import cz.spacks.worms.Application;
import cz.spacks.worms.controller.server.ServerCommunicationInternet;
import cz.spacks.worms.controller.server.ServerCommunicationLocal;
import cz.spacks.worms.controller.BindableButton;
import cz.spacks.worms.controller.Controls;
import cz.spacks.worms.controller.ControlsEnum;
import cz.spacks.worms.controller.communication.RegistrationForm;
import cz.spacks.worms.controller.properties.Paths;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom menu class for a project.
 */
public class MainFrame extends JFrame {

    // cz.spacks.worms.main window
    private JFrame mainFrame;
    private JPanel rootPanel;

    // menu card
    private JPanel menuCard;
    private JPanel margin;
    private JPanel menuCards;
    // cz.spacks.worms.main menu
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
    private JButton applyAudioSettingsButton;
    private JButton backAudioSettingsButton;
    // settings - keys
    private JPanel keysSettingsCard;
    private JPanel keyBindingPanel;
    private JButton applyKeysSettingsButton;
    // status bar
    private JPanel statusBar;

    private JLabel versionNumberLabel;
    // cz.spacks.worms.client card
    private JPanel clientCard;
    private JPanel messagePanel;
    private JPanel chatPanel;
    private JTextField messageTextField;
    private JButton messageSendButton;
    private JPanel minimapPanel;
    private JPanel inventoryPanel;
    private JToolBar itemToolbar;


    CardLayout mainCardLayout;
    CardLayout menuCardLayout;

    List<BindableButton> bindableButtons;

    public MainFrame() {
        super("Test window");
        mainFrame = this;

        $$$setupUI$$$();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        URL cursorImg = MainFrame.class.getResource(Paths.CURSOR_FILE.value());
//        Image image = toolkit.createImage(cursorImg);
//        System.out.println(image.toString());
//        Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
//        System.out.println(c.toString());
//        setCursor(c);

        URL url = MainFrame.class.getResource(Paths.ICON_FILE.value());
        Image img = toolkit.createImage(url);
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
//        menuCard = new BackgroundPanel(SpriteLoader.loadSprite("background", ".jpg"));
        menuCard = new JPanel();

        // cz.spacks.worms.main menu
        singleplayerButton = new CustomSoundButton(e -> {
            new ServerFrame();
            try {
                new ServerCommunicationLocal();
                SwingUtilities.invokeLater(() -> {
                    ClientCommunicationLocal clientCommunicationLocal = new ClientCommunicationLocal();
                    clientCommunicationLocal.init(new RegistrationForm("Alpha"));
                    joinProgressBar.setValue(joinProgressBar.getMaximum());
                    mainCardLayout.show(rootPanel, "clientCard");
                    clientCard.requestFocusInWindow();
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
            ClientCommunicationInternet clientCommunicationInternet = new ClientCommunicationInternet();
            clientCommunicationInternet.init(address, port, form);
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
                new ServerCommunicationInternet(port);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        backHostButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "multiplayerCard"));

        // settings
        soundSlider = new JSlider(0, 100, Settings.getInstance().getVolume());
        backVideoSettingsButton = new CustomSoundButton(e -> menuCardLayout.show(menuCards, "settingsCard"));
        applyAudioSettingsButton = new CustomSoundButton(e -> {
            Settings.getInstance().setVolume(soundSlider.getValue());
            menuCardLayout.show(menuCards, "settingsCard");
        });
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

        // cz.spacks.worms.client card
        final ClientView clientView = ClientView.getInstance();
        clientCard = clientView;
        minimapPanel = clientView.getMinimapView();
        inventoryPanel = clientView.getInventory();
        chatPanel = ChatLog.getInstance();
        chatPanel.setBackground(new Color(100, 100, 255, 10));

        ChatLog.getInstance().log("Hello world!");

        final ChatInputPanel chatPanelInstance = ChatInputPanel.getInstance();
        messagePanel = chatPanelInstance;
        messageTextField = chatPanelInstance.getField();
        messageSendButton = chatPanelInstance.getButton();

        itemToolbar = GameWindowItemBar.getInstance();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new CardLayout(0, 0));
        rootPanel.setAutoscrolls(false);
        rootPanel.setEnabled(true);
        rootPanel.setInheritsPopupMenu(false);
        rootPanel.setMinimumSize(new Dimension(800, 600));
        rootPanel.setVisible(true);
        menuCard.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        rootPanel.add(menuCard, "menuCard");
        margin = new JPanel();
        margin.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        margin.setEnabled(true);
        margin.setOpaque(false);
        menuCard.add(margin, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuCards = new JPanel();
        menuCards.setLayout(new CardLayout(0, 0));
        menuCards.setOpaque(false);
        margin.add(menuCards, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mainMenuCard = new JPanel();
        mainMenuCard.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainMenuCard.setOpaque(false);
        menuCards.add(mainMenuCard, "mainMenuCard");
        singleplayerButton.setBackground(new Color(-1644826));
        singleplayerButton.setBorderPainted(false);
        singleplayerButton.setContentAreaFilled(false);
        singleplayerButton.setFocusPainted(true);
        singleplayerButton.setFocusable(true);
        singleplayerButton.setFont(new Font(singleplayerButton.getFont().getName(), singleplayerButton.getFont().getStyle(), 26));
        singleplayerButton.setHideActionText(false);
        singleplayerButton.setText("Single Player");
        mainMenuCard.add(singleplayerButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        multiplayerButton.setBackground(new Color(-1644826));
        multiplayerButton.setBorderPainted(false);
        multiplayerButton.setContentAreaFilled(false);
        multiplayerButton.setFocusPainted(true);
        multiplayerButton.setFocusable(true);
        multiplayerButton.setFont(new Font(multiplayerButton.getFont().getName(), multiplayerButton.getFont().getStyle(), 26));
        multiplayerButton.setHideActionText(false);
        multiplayerButton.setText("Multiplayer");
        mainMenuCard.add(multiplayerButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        settingsButton.setBackground(new Color(-1644826));
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(true);
        settingsButton.setFocusable(true);
        settingsButton.setFont(new Font(settingsButton.getFont().getName(), settingsButton.getFont().getStyle(), 26));
        settingsButton.setHideActionText(false);
        settingsButton.setText("Settings");
        mainMenuCard.add(settingsButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton.setBackground(new Color(-1644826));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(true);
        exitButton.setFocusable(true);
        exitButton.setFont(new Font(exitButton.getFont().getName(), exitButton.getFont().getStyle(), 26));
        exitButton.setHideActionText(false);
        exitButton.setText("Exit");
        mainMenuCard.add(exitButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainMenuCard.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        settingsCard = new JPanel();
        settingsCard.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        settingsCard.setOpaque(false);
        menuCards.add(settingsCard, "settingsCard");
        videoSettingsButton.setBackground(new Color(-1644826));
        videoSettingsButton.setBorderPainted(false);
        videoSettingsButton.setContentAreaFilled(false);
        videoSettingsButton.setFocusPainted(true);
        videoSettingsButton.setFocusable(true);
        videoSettingsButton.setFont(new Font(videoSettingsButton.getFont().getName(), videoSettingsButton.getFont().getStyle(), 26));
        videoSettingsButton.setHideActionText(false);
        videoSettingsButton.setText("Video");
        settingsCard.add(videoSettingsButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        audioSettingsButton.setBackground(new Color(-1644826));
        audioSettingsButton.setBorderPainted(false);
        audioSettingsButton.setContentAreaFilled(false);
        audioSettingsButton.setFocusPainted(true);
        audioSettingsButton.setFocusable(true);
        audioSettingsButton.setFont(new Font(audioSettingsButton.getFont().getName(), audioSettingsButton.getFont().getStyle(), 26));
        audioSettingsButton.setHideActionText(false);
        audioSettingsButton.setText("Audio");
        settingsCard.add(audioSettingsButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backSettingsButton.setBackground(new Color(-1644826));
        backSettingsButton.setBorderPainted(false);
        backSettingsButton.setContentAreaFilled(false);
        backSettingsButton.setFocusPainted(true);
        backSettingsButton.setFocusable(true);
        backSettingsButton.setFont(new Font(backSettingsButton.getFont().getName(), backSettingsButton.getFont().getStyle(), 26));
        backSettingsButton.setHideActionText(false);
        backSettingsButton.setText("Back");
        settingsCard.add(backSettingsButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        settingsCard.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        keysSettingsButton.setBackground(new Color(-1644826));
        keysSettingsButton.setBorderPainted(false);
        keysSettingsButton.setContentAreaFilled(false);
        keysSettingsButton.setFocusPainted(true);
        keysSettingsButton.setFocusable(true);
        keysSettingsButton.setFont(new Font(keysSettingsButton.getFont().getName(), keysSettingsButton.getFont().getStyle(), 26));
        keysSettingsButton.setHideActionText(false);
        keysSettingsButton.setText("Keys");
        settingsCard.add(keysSettingsButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        multiplayerCard = new JPanel();
        multiplayerCard.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        multiplayerCard.setOpaque(false);
        menuCards.add(multiplayerCard, "multiplayerCard");
        joinMultiplayerButton.setBackground(new Color(-1644826));
        joinMultiplayerButton.setBorderPainted(false);
        joinMultiplayerButton.setContentAreaFilled(false);
        joinMultiplayerButton.setFocusPainted(true);
        joinMultiplayerButton.setFocusable(true);
        joinMultiplayerButton.setFont(new Font(joinMultiplayerButton.getFont().getName(), joinMultiplayerButton.getFont().getStyle(), 26));
        joinMultiplayerButton.setHideActionText(false);
        joinMultiplayerButton.setText("Join");
        multiplayerCard.add(joinMultiplayerButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hostMultiplayerButton.setBackground(new Color(-1644826));
        hostMultiplayerButton.setBorderPainted(false);
        hostMultiplayerButton.setContentAreaFilled(false);
        hostMultiplayerButton.setFocusPainted(true);
        hostMultiplayerButton.setFocusable(true);
        hostMultiplayerButton.setFont(new Font(hostMultiplayerButton.getFont().getName(), hostMultiplayerButton.getFont().getStyle(), 26));
        hostMultiplayerButton.setHideActionText(false);
        hostMultiplayerButton.setText("Host");
        multiplayerCard.add(hostMultiplayerButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backMultiplayerButton.setBackground(new Color(-1644826));
        backMultiplayerButton.setBorderPainted(false);
        backMultiplayerButton.setContentAreaFilled(false);
        backMultiplayerButton.setFocusPainted(true);
        backMultiplayerButton.setFocusable(true);
        backMultiplayerButton.setFont(new Font(backMultiplayerButton.getFont().getName(), backMultiplayerButton.getFont().getStyle(), 26));
        backMultiplayerButton.setHideActionText(false);
        backMultiplayerButton.setText("Back");
        multiplayerCard.add(backMultiplayerButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        multiplayerCard.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        joinMultiplayerCard = new JPanel();
        joinMultiplayerCard.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        joinMultiplayerCard.setOpaque(false);
        menuCards.add(joinMultiplayerCard, "joinMultiplayerCard");
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        joinMultiplayerCard.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Address:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        joinAddressTextField = new JTextField();
        joinAddressTextField.setText("localhost");
        panel1.add(joinAddressTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        joinMultiplayerCard.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Port:");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        joinPortTextField = new JTextField();
        joinPortTextField.setText("4242");
        panel2.add(joinPortTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel2.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        joinMultiplayerCard.add(spacer6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        joinJoinButton.setBorderPainted(false);
        joinJoinButton.setContentAreaFilled(false);
        joinJoinButton.setFont(new Font(joinJoinButton.getFont().getName(), joinJoinButton.getFont().getStyle(), 26));
        joinJoinButton.setText("Join");
        joinMultiplayerCard.add(joinJoinButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backJoinButton.setBorderPainted(false);
        backJoinButton.setContentAreaFilled(false);
        backJoinButton.setFont(new Font(backJoinButton.getFont().getName(), backJoinButton.getFont().getStyle(), 26));
        backJoinButton.setText("Back");
        joinMultiplayerCard.add(backJoinButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        joinProgressBar = new JProgressBar();
        joinMultiplayerCard.add(joinProgressBar, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hostMultiplayerCard = new JPanel();
        hostMultiplayerCard.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        hostMultiplayerCard.setOpaque(false);
        menuCards.add(hostMultiplayerCard, "hostMultiplayerCard");
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        hostMultiplayerCard.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Port:");
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hostPortTextField = new JTextField();
        hostPortTextField.setText("4242");
        panel3.add(hostPortTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel3.add(spacer7, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        hostMultiplayerCard.add(spacer8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        hostHostButton.setBorderPainted(false);
        hostHostButton.setContentAreaFilled(false);
        hostHostButton.setFont(new Font(hostHostButton.getFont().getName(), hostHostButton.getFont().getStyle(), 26));
        hostHostButton.setText("Host");
        hostMultiplayerCard.add(hostHostButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backHostButton.setBorderPainted(false);
        backHostButton.setContentAreaFilled(false);
        backHostButton.setFont(new Font(backHostButton.getFont().getName(), backHostButton.getFont().getStyle(), 26));
        backHostButton.setText("Back");
        hostMultiplayerCard.add(backHostButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        videoSettingsCard = new JPanel();
        videoSettingsCard.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        videoSettingsCard.setOpaque(false);
        menuCards.add(videoSettingsCard, "videoSettingsCard");
        backVideoSettingsButton.setBackground(new Color(-1644826));
        backVideoSettingsButton.setBorderPainted(false);
        backVideoSettingsButton.setContentAreaFilled(false);
        backVideoSettingsButton.setFocusPainted(true);
        backVideoSettingsButton.setFocusable(true);
        backVideoSettingsButton.setFont(new Font(backVideoSettingsButton.getFont().getName(), backVideoSettingsButton.getFont().getStyle(), 26));
        backVideoSettingsButton.setHideActionText(false);
        backVideoSettingsButton.setText("Back");
        videoSettingsCard.add(backVideoSettingsButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setFont(new Font(label4.getFont().getName(), label4.getFont().getStyle(), 36));
        label4.setText("Video settings");
        videoSettingsCard.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        videoSettingsCard.add(spacer9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        audioSettingsCard = new JPanel();
        audioSettingsCard.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        audioSettingsCard.setOpaque(false);
        menuCards.add(audioSettingsCard, "audioSettingsCard");
        backAudioSettingsButton.setBackground(new Color(-1644826));
        backAudioSettingsButton.setBorderPainted(false);
        backAudioSettingsButton.setContentAreaFilled(false);
        backAudioSettingsButton.setFocusPainted(true);
        backAudioSettingsButton.setFocusable(true);
        backAudioSettingsButton.setFont(new Font(backAudioSettingsButton.getFont().getName(), backAudioSettingsButton.getFont().getStyle(), 26));
        backAudioSettingsButton.setHideActionText(false);
        backAudioSettingsButton.setText("Back");
        audioSettingsCard.add(backAudioSettingsButton, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        audioSettingsCard.add(spacer10, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        soundSlider.setValue(80);
        audioSettingsCard.add(soundSlider, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        soundAudioSettingSlider = new JLabel();
        soundAudioSettingSlider.setText("Sound");
        audioSettingsCard.add(soundAudioSettingSlider, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        musicAudioSettingSlider = new JLabel();
        musicAudioSettingSlider.setText("Music");
        audioSettingsCard.add(musicAudioSettingSlider, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setFont(new Font(label5.getFont().getName(), label5.getFont().getStyle(), 36));
        label5.setText("Audio");
        audioSettingsCard.add(label5, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        musicSlider = new JSlider();
        musicSlider.setValue(80);
        audioSettingsCard.add(musicSlider, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        applyAudioSettingsButton.setBackground(new Color(-1644826));
        applyAudioSettingsButton.setBorderPainted(false);
        applyAudioSettingsButton.setContentAreaFilled(false);
        applyAudioSettingsButton.setFocusPainted(true);
        applyAudioSettingsButton.setFocusable(true);
        applyAudioSettingsButton.setFont(new Font(applyAudioSettingsButton.getFont().getName(), applyAudioSettingsButton.getFont().getStyle(), 26));
        applyAudioSettingsButton.setHideActionText(false);
        applyAudioSettingsButton.setText("Apply");
        audioSettingsCard.add(applyAudioSettingsButton, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keysSettingsCard = new JPanel();
        keysSettingsCard.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        keysSettingsCard.setOpaque(false);
        menuCards.add(keysSettingsCard, "keysSettingsCard");
        applyKeysSettingsButton.setBackground(new Color(-1644826));
        applyKeysSettingsButton.setBorderPainted(false);
        applyKeysSettingsButton.setContentAreaFilled(false);
        applyKeysSettingsButton.setFocusPainted(true);
        applyKeysSettingsButton.setFocusable(true);
        applyKeysSettingsButton.setFont(new Font(applyKeysSettingsButton.getFont().getName(), applyKeysSettingsButton.getFont().getStyle(), 26));
        applyKeysSettingsButton.setHideActionText(false);
        applyKeysSettingsButton.setText("Apply");
        keysSettingsCard.add(applyKeysSettingsButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        keysSettingsCard.add(spacer11, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setFont(new Font(label6.getFont().getName(), label6.getFont().getStyle(), 36));
        label6.setText("Keys");
        keysSettingsCard.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyBindingPanel.setOpaque(false);
        keysSettingsCard.add(keyBindingPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statusBar = new JPanel();
        statusBar.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        statusBar.setOpaque(false);
        margin.add(statusBar, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        statusBar.add(spacer12, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Some more info");
        statusBar.add(label7, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        versionNumberLabel = new JLabel();
        versionNumberLabel.setText("version number");
        statusBar.add(versionNumberLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        margin.add(spacer13, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        margin.add(spacer14, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        margin.add(spacer15, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        margin.add(spacer16, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        clientCard.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        clientCard.setEnabled(true);
        rootPanel.add(clientCard, "clientCard");
        messagePanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        messagePanel.setOpaque(false);
        clientCard.add(messagePanel, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(165, 91), null, 0, false));
        messageTextField.setFocusable(true);
        messageTextField.setOpaque(false);
        messagePanel.add(messageTextField, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        messageSendButton.setFocusable(false);
        messageSendButton.setOpaque(false);
        messageSendButton.setText("Send");
        messagePanel.add(messageSendButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chatPanel.setOpaque(true);
        messagePanel.add(chatPanel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        messagePanel.add(spacer17, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        minimapPanel.setFocusable(false);
        minimapPanel.setOpaque(false);
        clientCard.add(minimapPanel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer18 = new Spacer();
        clientCard.add(spacer18, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer19 = new Spacer();
        clientCard.add(spacer19, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        clientCard.add(inventoryPanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        clientCard.add(itemToolbar, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
