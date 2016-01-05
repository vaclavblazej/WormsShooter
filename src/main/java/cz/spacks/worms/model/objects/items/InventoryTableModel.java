package cz.spacks.worms.model.objects.items;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InventoryTableModel extends ComponentTableModel {

    private ItemBlueprint heldItem;
    private MyButton highlight;

    public InventoryTableModel(String... columnNames) {
        super(columnNames);
        heldItem = null;
    }

    public ItemBlueprint getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    public boolean isHeldItem(int i) {
        return getItem(i) == heldItem;
    }

    public Color getColor(int i) {
        return getItem(i).getCategory().gc();
    }

    public List<JButton> generateToolbar() {
        List<JButton> toolbar = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            if (isUsable(i)) {
                MyButton btn = new MyButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (highlight != null) {
                            highlight.setBackground(Color.WHITE);
                        }
                        highlight = (MyButton) e.getSource();
                        highlight.setBackground(Color.GREEN);
                        heldItem = highlight.item;
                    }
                });
                btn.item = getItem(i);
                if (heldItem != null && heldItem.equals(btn.item)) {
                    btn.setBackground(Color.GREEN);
                    highlight = btn;
                }
                btn.setText(getValueAt(i, 0).toString() + ": " + getValueAt(i, 1).toString());
                btn.setFocusable(false);
                btn.setIcon(new ImageIcon(btn.item.getImage()));
                toolbar.add(btn);
            }
        }
        return toolbar;
    }

    private class MyButton extends JButton {

        public ItemBlueprint item;

        public MyButton(Action a) {
            super(a);
            item = null;
        }
    }
}
