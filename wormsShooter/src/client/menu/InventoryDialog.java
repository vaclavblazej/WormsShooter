package client.menu;

import client.MainPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import objects.items.Crafting;
import utilities.AbstractDialog;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class InventoryDialog extends AbstractDialog {

    private JSplitPane split;
    private JTable items;
    private JTable recepies;

    public InventoryDialog(JFrame owner) {
        super(owner, Message.Inventory_window_title.cm(), false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        items = new JTable(MainPanel.getInstance().getMyBody().getInventory());
        recepies = new JTable(Crafting.getInstance());
        split.add(new JScrollPane(items), 1);
        split.add(new JScrollPane(recepies), 2);
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
