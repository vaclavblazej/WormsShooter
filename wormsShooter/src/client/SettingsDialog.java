package client;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;

    public SettingsDialog(JFrame owner) {
        super(owner, Message.Settings_window_title.cm());

        //JTabbedPane tabbedPane = new JTabbedPane();
        // todo - different tabs for soud, graphics, keys etc.
        getContent().setLayout(new GridBagLayout());
        int row = 0;
        up = new BindableButton("" + Settings.getInstance().getControls().get(ControlsEnum.Up));
        getContent().add(new JLabel(Message.Up.cm() + ": "), new GBCBuilder().setY(row).build());
        getContent().add(up, new GBCBuilder().setY(row).setXRel().build());
        row++;
        down = new BindableButton("" + Settings.getInstance().getControls().get(ControlsEnum.Down));
        getContent().add(new JLabel(Message.Down.cm() + ": "), new GBCBuilder().setY(row).build());
        getContent().add(down, new GBCBuilder().setY(row).setXRel().build());
        row++;
        left = new BindableButton("" + Settings.getInstance().getControls().get(ControlsEnum.Left));
        getContent().add(new JLabel(Message.Left.cm() + ": "), new GBCBuilder().setY(row).build());
        getContent().add(left, new GBCBuilder().setY(row).setXRel().build());
        row++;
        right = new BindableButton("" + Settings.getInstance().getControls().get(ControlsEnum.Right));
        getContent().add(new JLabel(Message.Right.cm() + ": "), new GBCBuilder().setY(row).build());
        getContent().add(right, new GBCBuilder().setY(row).setXRel().build());
        row++;
        pack();

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void okAction() throws Exception {
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Up, Integer.parseInt(up.getText()));
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Down, Integer.parseInt(down.getText()));
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Left, Integer.parseInt(left.getText()));
        Settings.getInstance().getControls()
                .rebind(ControlsEnum.Right, Integer.parseInt(right.getText()));
    }

    @Override
    public boolean validateDialog() {
        return true;
    }
}
