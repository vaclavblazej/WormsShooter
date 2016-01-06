package cz.spacks.worms.controller.client.actions.impl;

import cz.spacks.worms.controller.client.actions.ActionClient;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.view.ComponentTableModel;
import cz.spacks.worms.model.objects.items.Recipe;
import cz.spacks.worms.controller.server.ServerCommunication;
import cz.spacks.worms.controller.server.actions.impl.CraftServerAction;

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
        Map<Integer, Body> controls = view.getModel().getControls();
        Inventory inventory = controls.get(id).getInventory();
        Recipe recipe = view.getModel().getFactory().getRecipes().getRecipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        ServerCommunication.getInstance().broadcast(new CraftServerAction(recipeId, id));
        System.out.println("cz.spacks.worms.view.server craft " + id + " " + recipeId);
    }
}