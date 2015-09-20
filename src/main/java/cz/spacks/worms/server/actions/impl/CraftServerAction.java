package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.client.menu.GameWindowItemBar;
import cz.spacks.worms.objects.Body;
import cz.spacks.worms.objects.items.InventoryTableModel;
import cz.spacks.worms.objects.items.Recipe;
import cz.spacks.worms.server.actions.ActionServer;

import java.util.Map;

/**
 * @author Václav Blažej
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
