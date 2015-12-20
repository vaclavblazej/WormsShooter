package cz.spacks.worms.client.actions.impl;

import cz.spacks.worms.client.actions.ActionClient;
import cz.spacks.worms.objects.Body;
import cz.spacks.worms.objects.items.ComponentTableModel;
import cz.spacks.worms.objects.items.Recipe;
import cz.spacks.worms.server.ServerCommunication;
import cz.spacks.worms.server.actions.impl.CraftServerAction;

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
        System.out.println("cz.spacks.worms.server craft " + id + " " + recipeId);
    }
}
