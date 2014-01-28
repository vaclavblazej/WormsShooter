package objects.items;

/**
 *
 * @author Skarab
 */
public class Recipe {

    private ComponentTableModel ingredients;
    private ComponentTableModel products;
    private String name;

    public Recipe(String name) {
        this.name = name;
        ingredients = new ComponentTableModel("Ingredients", "Count");
        products = new ComponentTableModel("Products", "Count");
    }

    public String getName() {
        return name;
    }

    public void addIngredient(ItemBlueprint item, int count) {
        ingredients.add(item, count);
    }

    public void addProduct(ItemBlueprint item, int count) {
        products.add(item, count);
    }

    public ComponentTableModel getIngredients() {
        return ingredients;
    }

    public ComponentTableModel getProducts() {
        return products;
    }
}
