package cz.spacks.worms.view.windows;

import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.ItemsCount;
import cz.spacks.worms.model.objects.items.Recipe;
import cz.spacks.worms.view.CraftingViewModel;
import cz.spacks.worms.view.component.ItemsTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 *
 */
public class CraftingPanel extends JPanel implements ListSelectionListener {

    private JScrollPane recipesScroll;
    private JTable recipes;
    private Crafting recipesModel;
    private JScrollPane ingredientsScroll;
    private JTable ingredients;
    private Inventory ingredientsModel;
    private JScrollPane productsScroll;
    private JTable products;
    private Inventory productsModel;
    private JButton craftButton;
    private int selectedRecipeId;

    private Inventory inventory;
    private WorldService worldService;

    public CraftingPanel() {
        super();
        setLayout(new BorderLayout());
        recipesModel = new Crafting();
        recipes = new JTable(new CraftingViewModel(recipesModel));
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
                if (inventory.contains(ingredientsModel)) {
                    worldService.craft(selectedRecipeId);
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

    public void setWorldService(WorldService worldService) {
        this.worldService = worldService;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        craftButton.setEnabled(true);
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        //boolean isAdjusting = e.getValueIsAdjusting();
        //int minIndex = lsm.getMinSelectionIndex();
        selectedRecipeId = lsm.getMaxSelectionIndex();
        Recipe recipe = recipesModel.getRecipe(selectedRecipeId);
        if (recipe != null) {
            ingredientsModel = recipe.getIngredients();
            ingredients.setModel(new ItemsTableModel(ingredientsModel));
            productsModel = recipe.getProducts();
            products.setModel(new ItemsTableModel(productsModel));
        }
    }

    public void setRecipesModel(Crafting recipesModel) {
        this.recipesModel = recipesModel;
        recipes.setModel(new CraftingViewModel(recipesModel));
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        recipesScroll.addKeyListener(l);
        ingredientsScroll.addKeyListener(l);
        productsScroll.addKeyListener(l);
        recipes.addKeyListener(l);
        ingredients.addKeyListener(l);
        products.addKeyListener(l);
        craftButton.addKeyListener(l);
    }
}
