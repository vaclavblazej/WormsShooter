package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.items.Recipe;

import java.util.Map;

/**
 *
 */
public class CraftServerAction extends ActionServer {

    private int recipeId;

    public CraftServerAction(int recipeId, int id) {
        super(id);
        this.recipeId = recipeId;
    }

    @Override
    public void perform() {
        Map<Integer, Body> controls = worldService.getWorldModel().getControls();
        Inventory inventory = controls.get(id).getInventory();
        Recipe recipe = worldService.getWorldModel().getFactory().getRecipes().getRecipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        // todo automatic update
        System.out.println("cz.spacks.worms.views craft " + id + " " + recipeId);
    }
}
