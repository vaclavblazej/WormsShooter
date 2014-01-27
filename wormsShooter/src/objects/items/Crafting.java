package objects.items;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skarab
 */
public class Crafting extends AbstractTableModel {

    private static Crafting instance;

    public static Crafting getInstance() {
        if (instance == null) {
            instance = new Crafting();
        }
        return instance;
    }
    private List<Receipe> recepies;

    private Crafting() {
        this.recepies = new ArrayList<>();
    }

    public void addReceipe(Receipe receipe) {
        recepies.add(receipe);
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
                return recepies.get(rowIndex);
            case 1:
                return recepies.get(rowIndex);
        }
        return null;
    }
}
