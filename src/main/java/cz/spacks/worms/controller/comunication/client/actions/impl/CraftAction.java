package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.ItemsCount;
import cz.spacks.worms.model.objects.items.Recipe;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class CraftAction extends ActionClient {

    private int recipeId;

    public CraftAction(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public void perform() {
        Map<Integer, Body> controls = worldService.getWorldModel().getControls();
        Inventory inventory = controls.get(0).getInventory();
        Recipe recipe = worldService.getWorldModel().getFactory().getRecipes().getRecipe(recipeId);
        final List<ItemsCount> ingredients = recipe.getIngredients();
        if (inventory.contains(ingredients)) {
            inventory.remove(ingredients);
            inventory.add(recipe.getProducts());
        }
        System.out.println("cz.spacks.worms.worldService.server craft " + id + " " + recipeId);
    }
}
