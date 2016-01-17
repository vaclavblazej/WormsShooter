package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.objects.items.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Crafting extends ArrayList<Recipe> {

    private List<Recipe> recipes;

    public Crafting() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe receipe) {
        recipes.add(receipe);
    }

    public Recipe getRecipe(int index) {
        return recipes.get(index);
    }
}
