package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.objects.items.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Crafting {

    private List<Recipe> recepies;

    public Crafting() {
        this.recepies = new ArrayList<>();
    }

    public void addRecipe(Recipe receipe) {
        recepies.add(receipe);
    }

    public Recipe getRecipe(int index) {
        return recepies.get(index);
    }
}
