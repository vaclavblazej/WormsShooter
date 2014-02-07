package objects.items;

import client.ClientCommunication;
import client.ClientView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utilities.communication.Action;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class CraftingPanel extends JPanel implements ListSelectionListener {

    private JScrollPane recepiesScroll;
    private JTable recepies;
    private Crafting recepiesModel;
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
        recepiesModel = ClientView.getInstance().getModel().getFactory().getRecipes();
        recepies = new JTable(recepiesModel);
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
                ComponentTableModel inventory = ClientView.getInstance().getMyBody().getInventory();
                if (inventory.contains(ingredientsModel)) {
                    ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.CRAFT).addInfo(new Integer(lastIndex)));
                }
            }
        });
        craftButton.setText("Craft");
        craftButton.setEnabled(false);

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
        craftButton.setEnabled(true);
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        //boolean isAdjusting = e.getValueIsAdjusting();
        //int minIndex = lsm.getMinSelectionIndex();
        lastIndex = lsm.getMaxSelectionIndex();
        Recipe recipe = recepiesModel.getReceipe(lastIndex);
        ingredientsModel = recipe.getIngredients();
        ingredients.setModel(ingredientsModel);
        productsModel = recipe.getProducts();
        products.setModel(productsModel);
    }
}
