package objects.items;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skarab
 */
public class Inventory extends AbstractTableModel {

    String[] columnNames = {"Name",
        "Count",
        "IDK"};
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>(10);
    }

    public void addItem(Item item) {
        items.add(item);
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
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items.get(rowIndex).get(columnIndex);
    }
}