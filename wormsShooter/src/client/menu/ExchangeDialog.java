package client.menu;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import objects.items.ComponentTableModel;
import utilities.AbstractDialog;
import utilities.Message;

/**
 * Dialog allowing inventory item exchange.
 *
 * @author Skarab
 */
public class ExchangeDialog extends AbstractDialog {

    private ComponentTableModel from;
    private ComponentTableModel to;
    private JSplitPane split;
    private JTable fromTable;
    private JTable toTable;
    private JButton moveRight;
    private JButton moveLeft;

    /**
     *
     * @param owner window which ownes this dialog
     * @param fromArg owned inventory
     * @param toArg accessed inventory
     */
    public ExchangeDialog(JFrame owner, ComponentTableModel fromArg, ComponentTableModel toArg) {
        super(owner, Message.INVENTORY_WINDOW_TITLE.cm(), false);
        this.from = fromArg;
        this.to = toArg;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        moveRight = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int l = from.getRowCount();
                ComponentTableModel list = new ComponentTableModel();
                for (int i = 0; i < l; i++) {
                    if (fromTable.isRowSelected(i)) {
                        list.add(from.getItem(i), 1);
                    }
                }
                from.remove(list);
                to.add(list);
            }
        });
        moveRight.setText("Right");
        moveLeft = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int l = to.getRowCount();
                ComponentTableModel list = new ComponentTableModel();
                for (int i = 0; i < l; i++) {
                    if (fromTable.isRowSelected(i)) {
                        list.add(to.getItem(i), 1);
                    }
                }
                to.remove(list);
                from.add(list);
            }
        });
        moveLeft.setText("Left");
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        fromTable = new JTable(from);
        toTable = new JTable(to);
        split.add(new JScrollPane(fromTable), 1);
        split.add(new JScrollPane(toTable), 2);
        getContent().add(split);
        getContent().add(moveRight);
        getContent().add(moveLeft);
        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void okAction() throws Exception {
    }

    @Override
    public boolean validateDialog() {
        return true;
    }
}