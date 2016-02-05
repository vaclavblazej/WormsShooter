package cz.spacks.worms.view;

import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.items.Recipe;

import javax.swing.table.AbstractTableModel;

/**
 *
 */
public class CraftingViewModel extends AbstractTableModel {

    private String[] columnName = {"Recipes"};
    private Crafting recipes;

    public CraftingViewModel(Crafting recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public Recipe getRecipe(int index) {
        if (index == -1) {
            return null;
        }
        return recipes.get(index);
    }

    @Override
    public int getRowCount() {
        return recipes.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return recipes.get(rowIndex).getName();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }
}
