package objects.items;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class ComponentTableModel extends AbstractTableModel implements Serializable {

    private ArrayList<Elem> components;
    private String[] columnNames;

    public ComponentTableModel(String... columnNames) {
        this.columnNames = columnNames;
        components = new ArrayList<>();
    }

    public void add(ItemBlueprint item, int count) {
        Elem e = new Elem(item, count);
        int index = components.indexOf(e);
        if (index >= 0) {
            components.get(index).count += count;
        } else {
            components.add(e);
            Collections.sort(components);
        }
    }

    public void add(ComponentTableModel table) {
        if (table == null) {
            return;
        }
        List<Elem> com = table.getComponents();
        int idx;
        for (Elem elem : com) {
            idx = components.indexOf(elem);
            if (idx != -1) {
                components.get(idx).count += elem.count;
            } else {
                add(elem.item, elem.count);
            }
        }
    }

    public void remove(ComponentTableModel table) {
        List<Elem> com = table.getComponents();
        int idx;
        for (Elem elem : com) {
            idx = components.indexOf(elem);
            if (idx != -1) {
                components.get(idx).count -= elem.count;
                if (components.get(idx).count <= 0) {
                    components.remove(idx);
                }
            }
        }
    }

    public boolean contains(ComponentTableModel table) {
        List<Elem> com = table.getComponents();
        int idx;
        for (Elem elem : com) {
            idx = components.indexOf(elem);
            if (idx == -1 || components.get(idx).count < elem.count) {
                return false;
            }
        }
        return true;
    }

    private List<Elem> getComponents() {
        return Collections.unmodifiableList(components);
    }

    public ItemBlueprint getItem(int i) {
        return getComponents().get(i).item;
    }

    public boolean isUsable(int index) {
        return components.get(index).item.isUsable();
    }

    @Override
    public int getRowCount() {
        return components.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return components.get(rowIndex).item.getName();
            case 1:
                return components.get(rowIndex).count;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    private class Elem implements Comparable<Elem>, Serializable {

        public ItemBlueprint item;
        public int count;

        public Elem(ItemBlueprint item, int count) {
            this.item = item;
            this.count = count;
        }

        @Override
        public int compareTo(Elem o) {
            return item.compareTo(o.item);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Elem)) {
                return false;
            }
            return item.equals(((Elem) obj).item);
        }
    }
}
