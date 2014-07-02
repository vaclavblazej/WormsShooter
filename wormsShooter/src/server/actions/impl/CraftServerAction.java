package server.actions.impl;

import client.ClientCommunication;
import client.menu.GameWindowItemBar;
import java.util.Map;
import objects.Body;
import objects.items.InventoryTableModel;
import objects.items.Recipe;
import server.actions.ActionServer;

/**
 *
 * @author Skarab
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
        System.out.println("client craft " + id + " " + recipeId);
    }
}
