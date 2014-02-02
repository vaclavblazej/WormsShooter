package objects.items;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

/**
 *
 * @author Skarab
 */
public class InventoryTableModel extends ComponentTableModel {

    private ItemBlueprint heldItem;
    private MyButton hightlight;

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

    public List<JButton> generateToolbar() {
        List<JButton> toolbar = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            if (isUsable(i)) {
                MyButton btn = new MyButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (hightlight != null) {
                            hightlight.setBackground(Color.WHITE);
                        }
                        hightlight = (MyButton) e.getSource();
                        hightlight.setBackground(Color.GREEN);
                        heldItem = hightlight.item;
                    }
                });
                btn.item = getItem(i);
                btn.setText(getValueAt(i, 0).toString() + ": " + getValueAt(i, 1).toString());
                btn.setFocusable(false);
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
