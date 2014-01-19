package utilities;

import client.GameWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Skarab
 */
public abstract class AbstractDialog extends JDialog implements Validator {

    private JLabel errorLabel;
    private JPanel content = new JPanel();
    private Action okAction;

    public AbstractDialog(String title) {
        super();
        //super(null, title, true);
        int i = 5;
        JPanel interior = new JPanel(new BorderLayout(i, i));
        interior.setBorder(BorderFactory.createEmptyBorder(i, i, i, i));
        add(interior);
        interior.add(createErrorPanel(), BorderLayout.PAGE_START);
        interior.add(content, BorderLayout.LINE_START);
        interior.add(createButtonPanel(), BorderLayout.PAGE_END);
        //Point loc = MainFrame.getInstance().getLocation();
        //loc.translate(200, 100);
        //setLocation(loc);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AbstractDialog.this.dispose();
            }
        });
    }

    public final JComponent createErrorPanel() {
        Box errPanel = new Box(BoxLayout.X_AXIS);
        errPanel.setPreferredSize(new Dimension(300, 30));
        int i = 5;
        errPanel.setBorder(BorderFactory.createEmptyBorder(i, i, i, i));
        errorLabel = new JLabel("     ");
        errPanel.add(errorLabel);
        errorLabel.setForeground(Color.RED);
        return errPanel;
    }

    public final JComponent createButtonPanel() {
        Box btnPanel = new Box(BoxLayout.X_AXIS);
        int i = 5;
        btnPanel.setBorder(BorderFactory.createEmptyBorder(i, i, i, i));
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(new JButton(new AbstractAction(Message.Cancel_button.cm()) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    AbstractDialog.this.cancelAction();
                    AbstractDialog.this.dispose();
                } catch (Exception ex) {
                    GameWindow.getInstance().showError(ex.toString());
                }
            }
        }));
        btnPanel.add(Box.createHorizontalStrut(5));
        okAction = new AbstractAction(Message.OK_button.cm()) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    AbstractDialog.this.okAction();
                    AbstractDialog.this.dispose();
                } catch (Exception ex) {
                    GameWindow.getInstance().showError(ex.toString());
                }
            }
        };
        btnPanel.add(new JButton(okAction));
        return btnPanel;
    }

    public void error(String message) {
        errorLabel.setForeground(Color.RED);
        errorLabel.setText(Message.Error.cm() + ": " + message);
    }

    public void clearError() {
        errorLabel.setForeground(Color.BLUE);
        errorLabel.setText("Everything is ok");
        okAction.setEnabled(true);
    }

    public void blockOk() {
        okAction.setEnabled(false);
    }

    public abstract void okAction() throws Exception;

    public void cancelAction() throws Exception {
    }

    public JPanel getContent() {
        return content;
    }
}