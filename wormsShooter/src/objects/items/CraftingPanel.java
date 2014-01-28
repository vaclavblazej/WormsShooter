package objects.items;

import client.MainPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Skarab
 */
public class CraftingPanel extends JPanel implements ListSelectionListener {

    private JScrollPane recepiesScroll;
    private JTable recepies;
    private JScrollPane ingredientsScroll;
    private JTable ingredients;
    private ComponentTableModel ingredientsModel;
    private JScrollPane productsScroll;
    private JTable products;
    private ComponentTableModel productsModel;
    private JButton craftButton;

    public CraftingPanel() {
        super();

        setLayout(new BorderLayout());
        recepies = new JTable(Crafting.getInstance());
        recepiesScroll = new JScrollPane(recepies);
        recepiesScroll.setPreferredSize(new Dimension(200, 100));
        ingredients = new JTable();
        ingredientsScroll = new JScrollPane(ingredients);
        ingredientsScroll.setPreferredSize(new Dimension(200, 100));
        products = new JTable();
        productsScroll = new JScrollPane(products);
        productsScroll.setPreferredSize(new Dimension(200, 100));
        craftButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentTableModel inventory = MainPanel.getInstance().getMyBody().getInventory();
                if (inventory.contains(ingredientsModel)) {
                    inventory.remove(ingredientsModel);
                    inventory.add(productsModel);
                    inventory.fireTableDataChanged();
                }
            }
        });
        craftButton.setText("Craft");

        JPanel container = new JPanel(new BorderLayout());
        container.add(ingredientsScroll, BorderLayout.NORTH);
        container.add(productsScroll, BorderLayout.CENTER);
        add(recepiesScroll, BorderLayout.CENTER);
        add(container, BorderLayout.SOUTH);
        JPanel confirmation = new JPanel();
        confirmation.add(craftButton);
        container.add(confirmation, BorderLayout.SOUTH);
        //add(new JScrollPane(recepies), BorderLayout.CENTER);
        recepies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recepies.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        //boolean isAdjusting = e.getValueIsAdjusting();
        //int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        Recipe recipe = Crafting.getInstance().getReceipe(maxIndex);
        ingredientsModel = recipe.getIngredients();
        ingredients.setModel(ingredientsModel);
        productsModel = recipe.getProducts();
        products.setModel(productsModel);
    }
}
