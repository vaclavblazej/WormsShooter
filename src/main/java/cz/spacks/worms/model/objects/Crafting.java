package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.objects.items.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Crafting extends ArrayList<Recipe> {

    public void addRecipe(Recipe receipe) {
        add(receipe);
    }

    public Recipe getRecipe(int index) {
        return get(index);
    }
}
