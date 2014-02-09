package dynamic.communication;

import client.ClientCommunication;
import client.menu.GameWindowItemBar;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import objects.Body;
import objects.items.ComponentTableModel;
import objects.items.InventoryTableModel;
import objects.items.Recipe;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class craft extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        int id = packet.getId();
        int recipeId = (Integer) packet.get(0);
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

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        int id = packet.getId();
        int recipeId = (Integer) packet.get(0);
        Map<Integer, Body> controls = view.getModel().getControls();
        ComponentTableModel inventory = controls.get(id).getInventory();
        Recipe recipe = view.getModel().getFactory().getRecipes().getReceipe(recipeId);
        inventory.remove(recipe.getIngredients());
        inventory.add(recipe.getProducts());
        ServerComService.getInstance().broadcast(
                new PacketBuilder(Action.CRAFT, id).addInfo(recipeId));
        System.out.println("server craft " + id + " " + recipeId);
    }
}
