package cz.spacks.worms.view.server.actions.impl;

import cz.spacks.worms.view.client.ClientCommunication;
import cz.spacks.worms.view.client.menu.GameWindowItemBar;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.items.InventoryTableModel;
import cz.spacks.worms.model.objects.items.Recipe;
import cz.spacks.worms.view.server.actions.ActionServer;

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
        InventoryTableModel inventory = controls.get(id).getInventory();
        Recipe recipe = view.getModel().getFactory().getRecipes().getReceipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        inventory.fireTableDataChanged();
        if (ClientCommunication.getInstance().getInfo().getId() == id) {
            GameWindowItemBar.getInstance().refreshBar(inventory);
        }
        System.out.println("cz.spacks.worms.client craft " + id + " " + recipeId);
    }
}
