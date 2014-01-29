package utilities.communication;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import objects.TestBody;
import objects.items.Crafting;
import objects.items.ItemFactory;

/**
 *
 * @author Skarab
 */
public class Model {

    private BufferedImage map;
    private Map<Integer, TestBody> controls;
    private ItemFactory factory;
    private int counter;

    public Model(
            BufferedImage map,
            Map<Integer, TestBody> controls,
            ItemFactory factory,
            int counter) {
        this.map = map;
        this.controls = controls;
        this.factory = factory;
        this.counter = counter;
    }

    public BufferedImage getMap() {
        return map;
    }

    public Map<Integer, TestBody> getControls() {
        return controls;
    }

    public ItemFactory getFactory() {
        return factory;
    }

    public int getCounter() {
        return counter;
    }

    public SerializableModel serialize() {
        Map<Integer, SerializableBody> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).serialize());
        }
        return new SerializableModel(new SerializableBufferedImage(map), factory, arr, counter);
    }
}
