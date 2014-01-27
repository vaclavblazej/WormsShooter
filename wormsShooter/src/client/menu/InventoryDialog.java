package client.menu;

import client.MainPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import utilities.AbstractDialog;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class InventoryDialog extends AbstractDialog {

    private JSplitPane split;
    private JTable table;

    public InventoryDialog(JFrame owner) {
        super(owner, Message.Inventory_window_title.cm(), false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        table = new JTable(MainPanel.getInstance().getMyBody().getInventory());
        split.add(new JScrollPane(table), 1);
        getContent().add(split);
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
