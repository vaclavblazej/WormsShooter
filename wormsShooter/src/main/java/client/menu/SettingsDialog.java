package client.menu;

import utilities.AbstractDialog;
import utilities.BindableButton;
import utilities.ControlsEnum;
import utilities.GridBagConstraintsBuilder;
import utilities.properties.Message;

import javax.swing.*;
import java.awt.*;

/**
 * @author Václav Blažej
 */
public class SettingsDialog extends AbstractDialog {

    private BindableButton up;
    private BindableButton down;
    private BindableButton left;
    private BindableButton right;
    private JSlider soundLevel;
    private JSlider detailLevel;
    private JTabbedPane tabbedPane;

    public SettingsDialog(JFrame owner) {
        super(owner, Message.SETTINGS_WINDOW_TITLE.value(), false);
        tabbedPane = new JTabbedPane();
        createKeyTab();
        createGraphicsTab();
        createSoundTab();
        getContent().add(tabbedPane);
        pack();

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void okAction() throws Exception {
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.UP, up.getKeyCode());
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.DOWN, down.getKeyCode());
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.LEFT, left.getKeyCode());
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.RIGHT, right.getKeyCode());
        Settings.getInstance().setVolume(soundLevel.getValue());
        Settings.getInstance().setQuality(detailLevel.getValue());
        Settings.getInstance().saveSettings();
    }

    @Override
    public boolean validateDialog() {
        return true;
    }

    private void createKeyTab() {
        JPanel keys = new JPanel();
        keys.setLayout(new GridBagLayout());
        int row = 0;
        up = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.UP));
        keys.add(new JLabel(Message.UP.value() + ": "), new GridBagConstraintsBuilder().setY(row).build());
        keys.add(up, new GridBagConstraintsBuilder().setY(row).setXRel().build());
        row++;
        down = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.DOWN));
        keys.add(new JLabel(Message.DOWN.value() + ": "), new GridBagConstraintsBuilder().setY(row).build());
        keys.add(down, new GridBagConstraintsBuilder().setY(row).setXRel().build());
        row++;
        left = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.LEFT));
        keys.add(new JLabel(Message.LEFT.value() + ": "), new GridBagConstraintsBuilder().setY(row).build());
        keys.add(left, new GridBagConstraintsBuilder().setY(row).setXRel().build());
        row++;
        right = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.RIGHT));
        keys.add(new JLabel(Message.RIGHT.value() + ": "), new GridBagConstraintsBuilder().setY(row).build());
        keys.add(right, new GridBagConstraintsBuilder().setY(row).setXRel().build());
        tabbedPane.addTab(Message.SETTINGS_TAB_KEYS.value(), keys);
    }

    private void createGraphicsTab() {
        JPanel graphic = new JPanel();
        graphic.setLayout(new GridBagLayout());
        int row = 0;
        detailLevel = new JSlider(0, 4, 1);
        detailLevel.setValue(Settings.getInstance().getQuality());
        graphic.add(new JLabel(Message.DETAIL_LEVEL.value() + ": "), new GridBagConstraintsBuilder().setY(row).build());
        graphic.add(detailLevel, new GridBagConstraintsBuilder().setY(row).setXRel().build());
        tabbedPane.addTab(Message.SETTINGS_TAB_GRAPHICS.value(), graphic);
    }

    private void createSoundTab() {
        JPanel sound = new JPanel();
        sound.setLayout(new GridBagLayout());
        int row = 0;
        soundLevel = new JSlider(0, 100, 1);
        soundLevel.setValue(Settings.getInstance().getVolume());
        sound.add(new JLabel(Message.SOUND_LEVEL.value() + ": "), new GridBagConstraintsBuilder().setY(row).build());
        sound.add(soundLevel, new GridBagConstraintsBuilder().setY(row).setXRel().build());
        tabbedPane.addTab(Message.SETTINGS_TAB_SOUND.value(), sound);
    }
}
