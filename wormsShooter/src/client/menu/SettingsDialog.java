package client.menu;

import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import objects.ControlsEnum;
import utilities.AbstractDialog;
import utilities.BindableButton;
import utilities.GBCBuilder;
import utilities.Message;

/**
 *
 * @author Skarab
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
        super(owner, Message.Settings_window_title.cm(), false);
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
                .rebind(ControlsEnum.Up, up.getKeyCode());
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Down, down.getKeyCode());
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Left, left.getKeyCode());
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Right, right.getKeyCode());
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
        up = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.Up));
        keys.add(new JLabel(Message.Up.cm() + ": "), new GBCBuilder().setY(row).build());
        keys.add(up, new GBCBuilder().setY(row).setXRel().build());
        row++;
        down = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.Down));
        keys.add(new JLabel(Message.Down.cm() + ": "), new GBCBuilder().setY(row).build());
        keys.add(down, new GBCBuilder().setY(row).setXRel().build());
        row++;
        left = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.Left));
        keys.add(new JLabel(Message.Left.cm() + ": "), new GBCBuilder().setY(row).build());
        keys.add(left, new GBCBuilder().setY(row).setXRel().build());
        row++;
        right = new BindableButton(Settings.getInstance().getControls().get(ControlsEnum.Right));
        keys.add(new JLabel(Message.Right.cm() + ": "), new GBCBuilder().setY(row).build());
        keys.add(right, new GBCBuilder().setY(row).setXRel().build());
        tabbedPane.addTab(Message.Settings_tab_keys.cm(), keys);
    }

    private void createGraphicsTab() {
        JPanel graphic = new JPanel();
        graphic.setLayout(new GridBagLayout());
        int row = 0;
        detailLevel = new JSlider(0, 4, 1);
        detailLevel.setValue(Settings.getInstance().getQuality());
        graphic.add(new JLabel(Message.Detail_level.cm() + ": "), new GBCBuilder().setY(row).build());
        graphic.add(detailLevel, new GBCBuilder().setY(row).setXRel().build());
        tabbedPane.addTab(Message.Settings_tab_graphics.cm(), graphic);
    }

    private void createSoundTab() {
        JPanel sound = new JPanel();
        sound.setLayout(new GridBagLayout());
        int row = 0;
        soundLevel = new JSlider(0, 100, 1);
        soundLevel.setValue(Settings.getInstance().getVolume());
        sound.add(new JLabel(Message.Sound_level.cm() + ": "), new GBCBuilder().setY(row).build());
        sound.add(soundLevel, new GBCBuilder().setY(row).setXRel().build());
        tabbedPane.addTab(Message.Settings_tab_sound.cm(), sound);
    }
}
