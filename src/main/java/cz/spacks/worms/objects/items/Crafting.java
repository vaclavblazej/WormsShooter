package cz.spacks.worms.objects.items;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class Crafting extends AbstractTableModel implements Serializable {

    private List<Recipe> recepies;
    private String[] columnName = {"Recipes"};

    public Crafting() {
        this.recepies = new ArrayList<>();
    }

    public void addReceipe(Recipe receipe) {
        recepies.add(receipe);
    }

    public Recipe getReceipe(int index) {
        if (index == -1) {
            return null;
        }
        return recepies.get(index);
    }

    @Override
    public int getRowCount() {
        return recepies.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return recepies.get(rowIndex).getName();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }
}
