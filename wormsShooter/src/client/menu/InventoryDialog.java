package client.menu;

import client.ClientView;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import objects.items.CraftingPanel;
import objects.items.InventoryTableModel;
import utilities.AbstractDialog;
import utilities.properties.Message;

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
        items = new JTable(ClientView.getInstance().getMyBody().getInventory()) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                InventoryTableModel it = (InventoryTableModel) getModel();
                c.setForeground(it.getColor(row));
                if (it.isHeldItem(row)) {
                    if (isRowSelected(row)) {
                        c.setBackground(Color.GREEN);
                    } else {
                        c.setBackground(Color.decode("#00AA00"));
                    }
                } else if (isRowSelected(row)) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };
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
