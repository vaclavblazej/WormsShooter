package cz.spacks.worms.view.windows;

import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.view.component.InventoryViewModel;
import cz.spacks.worms.view.defaults.DefaultKeyListener;

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

    private Inventory inventory;
    private FocusGrabber focusGrabber = FocusGrabber.NULL;

    public InventoryPanel() {
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        table = new JTable(new InventoryViewModel(new Body())) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                InventoryViewModel it = (InventoryViewModel) getModel();
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
            focusGrabber.focus();
        });
        close.setText("Close");
        add(close);
    }

    public void setFocusGrabber(FocusGrabber focusGrabber) {
        this.focusGrabber = focusGrabber;
    }

    public void setInventory(Inventory inventory) {
        craftingPanel.setInventory(inventory);
        this.inventory = inventory;
    }

    public void updateInventoryModel(InventoryViewModel inventoryViewModel) {
        table.setModel(inventoryViewModel);
    }

    public void updateCraftingModel(Crafting craftingModel) {
        craftingPanel.setRecipesModel(craftingModel);
    }
}
