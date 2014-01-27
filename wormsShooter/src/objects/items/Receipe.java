package objects.items;

import java.util.ArrayList;

/**
 *
 * @author Skarab
 */
public class Receipe {

    private ArrayList<Elem> ingredients;
    private ArrayList<Elem> products;

    public Receipe() {
        ingredients = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void addIngredient(Item item, int count) {
        ingredients.add(new Elem(item, count));
    }

    public void addProduct(Item item, int count) {
        products.add(new Elem(item, count));
    }

    private class Elem {

        public Item item;
        public int count;

        public Elem(Item item, int count) {
            this.item = item;
            this.count = count;
        }
    }
}
