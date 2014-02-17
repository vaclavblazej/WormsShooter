package communication.client;

import communication.server.CraftServerAction;
import java.util.Map;
import objects.Body;
import objects.items.ComponentTableModel;
import objects.items.Recipe;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class CraftAction extends PerformablePacket {

    private int recipeId;

    public CraftAction(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public void perform(AbstractView view) {
        Map<Integer, Body> controls = view.getModel().getControls();
        ComponentTableModel inventory = controls.get(id).getInventory();
        Recipe recipe = view.getModel().getFactory().getRecipes().getReceipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        ServerComService.getInstance().broadcast(new CraftServerAction(recipeId, id));
        System.out.println("server craft " + id + " " + recipeId);
    }
}
