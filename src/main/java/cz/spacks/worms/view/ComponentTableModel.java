package cz.spacks.worms.view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class ComponentTableModel<Elem> extends AbstractTableModel {

    private List<Elem> components;
    private String[] columnNames;

    public ComponentTableModel(String... columnNames) {
        this.columnNames = columnNames;
        components = new ArrayList<>();
    }

    public void add(Elem elem) {
        components.add(elem);
    }

    public void remove(Elem elem) {
        components.remove(elem);
    }

    public boolean contains(Elem elem) {
        return components.contains(elem);
    }

    private List<Elem> getComponents() {
        return Collections.unmodifiableList(components);
    }

    public void setComponents(List<Elem> components) {
        this.components = components;
    }

    @Override
    public int getRowCount() {
        return components.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getElementValueAt(components.get(rowIndex), columnIndex);
    }

    public abstract Object getElementValueAt(Elem elem, int idx);

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Elem getComponentAtRow(int row){
        return components.get(row);
    }
}
