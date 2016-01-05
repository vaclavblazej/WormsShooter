package cz.spacks.worms.view.windows;

import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.model.objects.items.Crafting;
import cz.spacks.worms.model.objects.items.InventoryTableModel;
import cz.spacks.worms.controller.defaults.DefaultKeyListener;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 *
 */
public class InventoryPanel extends JPanel implements DefaultKeyListener {

    private JSplitPane split;
    private JTable table;
    private CraftingPanel craftingPanel;

    public InventoryPanel() {
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        table = new JTable(new InventoryTableModel("", "", "")) {
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
        split.add(new JScrollPane(table), 1);
        craftingPanel = new CraftingPanel();
        split.add(craftingPanel, 2);
        add(split);
        final CustomButton close = new CustomButton(e -> {
            setVisible(false);
            ClientView.getInstance().grabFocus();
        });
        close.setText("Close");
        add(close);
    }

    public void updateInventoryModel(InventoryTableModel inventoryTableModel) {
        table.setModel(inventoryTableModel);
    }

    public void updateCraftingModel(Crafting craftingModel) {
        craftingPanel.setRecipesModel(craftingModel);
    }
}
