package utilities;

import utilities.properties.Message;
import client.ClientWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Skarab
 */
public abstract class AbstractDialog extends JDialog implements Validator {

    private JLabel errorLabel;
    private JPanel content;
    private Action okAction;

    public AbstractDialog(JFrame owner, String title) {
        this(owner, title, true);
    }

    public AbstractDialog(JFrame owner, String title, boolean errorPanel) {
        super(owner, title, true);
        int i = 5;
        content = new JPanel();
        JPanel interior = new JPanel(new BorderLayout(i, i));
        interior.setBorder(BorderFactory.createEmptyBorder(i, i, i, i));
        add(interior);
        if (errorPanel) {
            interior.add(createErrorPanel(), BorderLayout.PAGE_START);
        }
        interior.add(content, BorderLayout.LINE_START);
        interior.add(createButtonPanel(), BorderLayout.PAGE_END);
        Point loc = owner.getLocation();
        loc.translate(-200, 100);
        setLocation(loc);
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
        btnPanel.add(new JButton(new AbstractAction(Message.CANCEL_BUTTON.cm()) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    AbstractDialog.this.cancelAction();
                    AbstractDialog.this.dispose();
                } catch (Exception ex) {
                    ClientWindow.getInstance().showError(ex);
                }
            }
        }));
        btnPanel.add(Box.createHorizontalStrut(5));
        okAction = new AbstractAction(Message.OK_BUTTON.cm()) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    AbstractDialog.this.okAction();
                    AbstractDialog.this.dispose();
                } catch (Exception ex) {
                    ClientWindow.getInstance().showError(ex);
                }
            }
        };
        btnPanel.add(new JButton(okAction));
        return btnPanel;
    }

    public void error(String message) {
        if (errorLabel != null) {
            errorLabel.setForeground(Color.RED);
            errorLabel.setText(Message.ERROR.cm() + ": " + message);
        }
    }

    public void clearError() {
        if (errorLabel != null) {
            errorLabel.setForeground(Color.BLUE);
            errorLabel.setText(Message.OK_MESSAGE.cm());
        }
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