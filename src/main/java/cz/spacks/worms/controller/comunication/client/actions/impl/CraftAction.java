package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.server.actions.impl.CraftServerAction;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.items.Recipe;

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
        Inventory inventory = controls.get(id).getInventory();
        Recipe recipe = worldService.getWorldModel().getFactory().getRecipes().getRecipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        serverCommunication.broadcast(new CraftServerAction(recipeId, id));
        System.out.println("cz.spacks.worms.worldService.server craft " + id + " " + recipeId);
    }
}
