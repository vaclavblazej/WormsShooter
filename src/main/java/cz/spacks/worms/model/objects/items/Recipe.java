package cz.spacks.worms.model.objects.items;

import cz.spacks.worms.model.objects.ItemsCount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 *
 */
public class Recipe implements Serializable {

    private List<ItemsCount> ingredients;
    private List<ItemsCount> products;
    private EnumSet<SituationProperty> conditions;
    private String name;

    public Recipe(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
        products = new ArrayList<>();
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

    public List<ItemsCount> getIngredients() {
        return ingredients;
    }

    public List<ItemsCount> getProducts() {
        return products;
    }
}
