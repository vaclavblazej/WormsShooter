package objects.items;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skarab
 */
public class Inventory extends AbstractTableModel {

    String[] columnNames = {"Name", "Count"};
    private ArrayList<Elem> items;

    public Inventory() {
        items = new ArrayList<>(10);
    }

    public void addItem(Item item) {
        items.add(new Elem(item, 1));
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items.get(rowIndex).item.get(columnIndex);
    }

    private class Elem {

        public Item item;
        public int count;

        public Elem(Item item, int count) {
            this.item = item;
            this.count = count;
        }
    }
}
