package cz.spacks.worms.view.windows;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.properties.ControlsEnum;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.Controls;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.items.RecipeViewModel;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.component.ItemsTableModel;
import cz.spacks.worms.view.defaults.DefaultKeyListener;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 */
public class InventoryPanel extends JPanel implements DefaultKeyListener {

    private JSplitPane split;
    private JTable itemList;
    private CraftingPanel craftingPanel;
    private WorldService worldService;
    private Controls controls;
    private ItemsTableModel inventoryModel;
    private RecipeViewModel recipeViewModel;

    private FocusGrabber focusGrabber = FocusGrabber.NULL;

    public InventoryPanel() {
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        inventoryModel = new ItemsTableModel();
        inventoryModel.setInventory(new Inventory());
        recipeViewModel = new RecipeViewModel();
        itemList = new JTable(inventoryModel) {
//            @Override
//            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                Component c = super.prepareRenderer(renderer, row, column);
//                c.setForeground(inventoryModel.getColor(row));
//                if (inventoryModel.isHeldItem(row)) {
//                    if (isRowSelected(row)) {
//                        c.setBackground(Color.GREEN);
//                    } else {
//                        c.setBackground(Color.ORANGE);
//                    }
//                } else {
//                    if (isRowSelected(row)) {
//                        c.setBackground(Color.LIGHT_GRAY);
//                    } else {
//                        c.setBackground(Color.WHITE);
//                    }
//                }
//                return c;
//            }
        };
        split.add(new JScrollPane(itemList), 1);
        craftingPanel = new CraftingPanel();
        split.add(craftingPanel, 2);
        this.add(split);

        controls = Settings.getInstance().getControls();
        this.addKeyListener(this);
    }

    public void setFocusGrabber(FocusGrabber focusGrabber) {
        this.focusGrabber = focusGrabber;
    }

    public void setWorldService(WorldService worldService) {
        this.worldService = worldService;
        craftingPanel.setWorldService(worldService);
        craftingPanel.setRecipesModel(worldService.getWorldModel().getFactory().getRecipes());
    }

    public void setInventory(Body body) {
        craftingPanel.setInventory(body.getInventory());
        final ItemsTableModel itemsTableModel = new ItemsTableModel();
        itemsTableModel.setInventory(body.getInventory());
        updateInventoryModel(itemsTableModel);
    }

    public void updateInventoryModel(ItemsTableModel itemsTableModel) {
        inventoryModel = itemsTableModel;
        itemList.setModel(itemsTableModel);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ControlsEnum en = controls.get(e.getKeyCode());
        if (en != null) {
            switch (en) {
                case INVENTORY_TOGGLE:
                    this.setVisible(!this.isVisible());
                    focusGrabber.focus();
                    break;
            }
        }
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        split.addKeyListener(this);
        itemList.addKeyListener(this);
        craftingPanel.addKeyListener(this);
    }
}
