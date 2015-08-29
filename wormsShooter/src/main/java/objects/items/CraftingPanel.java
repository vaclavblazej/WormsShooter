package objects.items;

import client.ClientCommunication;
import client.ClientView;
import client.actions.impl.CraftAction;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Václav Blažej
 */
public class CraftingPanel extends JPanel implements ListSelectionListener {

    private JScrollPane recipesScroll;
    private JTable recipes;
    private Crafting recipesModel;
    private JScrollPane ingredientsScroll;
    private JTable ingredients;
    private ComponentTableModel ingredientsModel;
    private JScrollPane productsScroll;
    private JTable products;
    private ComponentTableModel productsModel;
    private JButton craftButton;
    private int lastIndex;

    public CraftingPanel() {
        super();
        setLayout(new BorderLayout());
        recipesModel = ClientView.getInstance().getModel().getFactory().getRecipes();
        recipes = new JTable(recipesModel);
        recipesScroll = new JScrollPane(recipes);
        recipesScroll.setPreferredSize(new Dimension(200, 100));
        ingredients = new JTable();
        ingredientsScroll = new JScrollPane(ingredients);
        ingredientsScroll.setPreferredSize(new Dimension(200, 100));
        products = new JTable();
        productsScroll = new JScrollPane(products);
        productsScroll.setPreferredSize(new Dimension(200, 100));
        craftButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentTableModel inventory = ClientView.getInstance().getMyView().getInventory();
                if (inventory.contains(ingredientsModel)) {
                    ClientCommunication.getInstance().send(new CraftAction(lastIndex));
                }
            }
        });
        craftButton.setText("Craft");
        craftButton.setEnabled(false);

        JPanel container = new JPanel(new BorderLayout());
        container.add(ingredientsScroll, BorderLayout.NORTH);
        container.add(productsScroll, BorderLayout.CENTER);
        add(recipesScroll, BorderLayout.CENTER);
        add(container, BorderLayout.SOUTH);
        JPanel confirmation = new JPanel();
        confirmation.add(craftButton);
        container.add(confirmation, BorderLayout.SOUTH);
        //add(new JScrollPane(recipes), BorderLayout.CENTER);
        recipes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipes.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        craftButton.setEnabled(true);
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        //boolean isAdjusting = e.getValueIsAdjusting();
        //int minIndex = lsm.getMinSelectionIndex();
        lastIndex = lsm.getMaxSelectionIndex();
        Recipe recipe = recipesModel.getReceipe(lastIndex);
        if (recipe != null) {
            ingredientsModel = recipe.getIngredients();
            ingredients.setModel(ingredientsModel);
            productsModel = recipe.getProducts();
            products.setModel(productsModel);
        }
    }
}
