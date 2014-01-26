package client.menu;

import client.MainPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import utilities.AbstractDialog;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class InventoryDialog extends AbstractDialog {

    private JTable table;

    public InventoryDialog(JFrame owner) {
        super(owner, Message.Inventory_window_title.cm());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table = new JTable(MainPanel.getInstance().getMyBody().getInventory());
        getContent().add(new JScrollPane(table));
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
