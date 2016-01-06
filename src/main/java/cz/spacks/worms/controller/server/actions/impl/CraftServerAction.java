package cz.spacks.worms.controller.server.actions.impl;

import cz.spacks.worms.controller.client.ClientCommunication;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.view.client.menu.GameWindowItemBar;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.view.client.menu.InventoryViewModel;
import cz.spacks.worms.model.objects.items.Recipe;
import cz.spacks.worms.controller.server.actions.ActionServer;

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
        Map<Integer, Body> controls = view.getModel().getControls();
        Inventory inventory = controls.get(id).getInventory();
        Recipe recipe = view.getModel().getFactory().getRecipes().getRecipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        // todo automatic update
        System.out.println("cz.spacks.worms.client craft " + id + " " + recipeId);
    }
}
