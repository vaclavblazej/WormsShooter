package client.actions.impl;

import client.actions.ActionClient;
import objects.Body;
import objects.items.ComponentTableModel;
import objects.items.Recipe;
import server.ServerCommunication;
import server.actions.impl.CraftServerAction;

import java.util.Map;

/**
 * @author Václav Blažej
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
        System.out.println("server craft " + id + " " + recipeId);
    }
}
