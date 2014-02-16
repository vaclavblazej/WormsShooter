package communication.server;

import client.ClientCommunication;
import client.menu.GameWindowItemBar;
import java.util.Map;
import objects.Body;
import objects.items.InventoryTableModel;
import objects.items.Recipe;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class CraftServerAction extends PerformablePacket {

    private int recipeId;

    public CraftServerAction(int recipeId, int id) {
        super(id);
        this.recipeId = recipeId;
    }

    @Override
    public void perform(AbstractView view) {
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
