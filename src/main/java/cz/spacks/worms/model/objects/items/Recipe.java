package cz.spacks.worms.model.objects.items;

import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.ItemsCount;

import java.io.Serializable;
import java.util.EnumSet;

/**
 *
 */
public class Recipe implements Serializable {

    private Inventory ingredients;
    private Inventory products;
    private EnumSet<SituationProperty> conditions;
    private String name;

    public Recipe(String name) {
        this.name = name;
        ingredients = new Inventory();
        products = new Inventory();
        conditions = EnumSet.noneOf(SituationProperty.class);
    }

    public String getName() {
        return name;
    }

    public void addIngredient(ItemBlueprint item, int count) {
        ingredients.add(new ItemsCount(item, count));
    }

    public void addProduct(ItemBlueprint item, int count) {
        products.add(new ItemsCount(item, count));
    }

    public void addCondition(SituationProperty prop) {
        conditions.add(prop);
    }

    public Inventory getIngredients() {
        return ingredients;
    }

    public Inventory getProducts() {
        return products;
    }
}
