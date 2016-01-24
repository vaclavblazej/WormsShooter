package cz.spacks.worms.view.windows;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.properties.ControlsEnum;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.Controls;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.component.InventoryViewModel;
import cz.spacks.worms.view.defaults.DefaultKeyListener;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 *
 */
public class InventoryPanel extends JPanel implements DefaultKeyListener {

    private JSplitPane split;
    private JTable table;
    final CustomButton close;
    private CraftingPanel craftingPanel;
    private WorldService worldService;
    private Controls controls;
    private InventoryViewModel inventoryModel;

    private FocusGrabber focusGrabber = FocusGrabber.NULL;

    public InventoryPanel() {
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        inventoryModel = new InventoryViewModel(new Body());
        table = new JTable(inventoryModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setForeground(inventoryModel.getColor(row));
                if (inventoryModel.isHeldItem(row)) {
                    if (isRowSelected(row)) {
                        c.setBackground(Color.GREEN);
                    } else {
                        c.setBackground(Color.ORANGE);
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
        close = new CustomButton(e -> {
            setVisible(false);
            focusGrabber.focus();
        });
        close.setText("Close");
        add(close);

        controls = Settings.getInstance().getControls();
        addKeyListener(this);
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
        updateInventoryModel(new InventoryViewModel(body));
    }

    public void updateInventoryModel(InventoryViewModel inventoryViewModel) {
        inventoryModel = inventoryViewModel;
        table.setModel(inventoryViewModel);
    }

    public JToolBar generateToolbar(){
        final JToolBar jToolBar = new JToolBar();
        final List<JButton> jButtons = inventoryModel.generateToolbar();
        for (JButton jButton : jButtons) {
            jToolBar.add(jButton);
        }
        return jToolBar;
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
        table.addKeyListener(this);
        craftingPanel.addKeyListener(this);
        close.addKeyListener(l);
    }
}
