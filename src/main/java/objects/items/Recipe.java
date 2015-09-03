package objects.items;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * @author Václav Blažej
 */
public class Recipe implements Serializable {

    private ComponentTableModel ingredients;
    private ComponentTableModel products;
    private EnumSet<SituationProperty> conditions;
    private String name;

    public Recipe(String name) {
        this.name = name;
        ingredients = new ComponentTableModel("Ingredients", "Count");
        products = new ComponentTableModel("Products", "Count");
        conditions = EnumSet.noneOf(SituationProperty.class);
    }

    public String getName() {
        return name;
    }

    public void addIngredient(ItemBlueprint item, int count) {
        ingredients.add(item, count);
    }

    public void addProduct(ItemBlueprint item, int count) {
        products.add(item, count);
    }

    public void addCondition(SituationProperty prop) {
        conditions.add(prop);
    }

    public ComponentTableModel getIngredients() {
        return ingredients;
    }

    public ComponentTableModel getProducts() {
        return products;
    }
}
