package client.menu;

import client.ClientView;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import objects.items.CraftingPanel;
import utilities.AbstractDialog;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class InventoryDialog extends AbstractDialog {

    private JSplitPane split;
    private JTable items;

    public InventoryDialog(JFrame owner) {
        super(owner, Message.INVENTORY_WINDOW_TITLE.cm(), false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        items = new JTable(ClientView.getInstance().getMyBody().getInventory());
        split.add(new JScrollPane(items), 1);
        split.add(new CraftingPanel(), 2);
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
