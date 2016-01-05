package cz.spacks.worms.view.client.actions.impl;

import cz.spacks.worms.view.client.actions.ActionClient;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.items.ComponentTableModel;
import cz.spacks.worms.model.objects.items.Recipe;
import cz.spacks.worms.view.server.ServerCommunication;
import cz.spacks.worms.view.server.actions.impl.CraftServerAction;

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
        ComponentTableModel inventory = controls.get(id).getInventory();
        Recipe recipe = view.getModel().getFactory().getRecipes().getReceipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        ServerCommunication.getInstance().broadcast(new CraftServerAction(recipeId, id));
        System.out.println("cz.spacks.worms.view.server craft " + id + " " + recipeId);
    }
}
