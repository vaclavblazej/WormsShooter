package cz.spacks.worms.model.objects.items;

import cz.spacks.worms.view.component.ItemsTableModel;

import java.io.Serializable;
import java.util.EnumSet;

/**
 *
 */
public class RecipeViewModel implements Serializable {

    private ItemsTableModel ingredients;
    private ItemsTableModel products;
    private EnumSet<SituationProperty> conditions;

    public RecipeViewModel() {
        ingredients = new ItemsTableModel("Ingredients", "Count");
        products = new ItemsTableModel("Products", "Count");
        conditions = EnumSet.noneOf(SituationProperty.class);
    }

    public ItemsTableModel getIngredients() {
        return ingredients;
    }

    public void setIngredients(ItemsTableModel ingredients) {
        this.ingredients = ingredients;
    }

    public ItemsTableModel getProducts() {
        return products;
    }

    public void setProducts(ItemsTableModel products) {
        this.products = products;
    }

    public EnumSet<SituationProperty> getConditions() {
        return conditions;
    }

    public void setConditions(EnumSet<SituationProperty> conditions) {
        this.conditions = conditions;
    }
}
