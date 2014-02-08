package dynamic.communication;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import server.Performable;
import utilities.communication.Action;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public final class DynamicLoader {

    private static Map<Action, Performable> performables;
    private static DynamicLoader instance;

    public static DynamicLoader getInstance() {
        if (instance == null) {
            instance = new DynamicLoader();
        }
        return instance;
    }

    public DynamicLoader() {
        performables = new HashMap<>(Action.values().length);
        load();
    }

    public Performable get(Action action) {
        return performables.get(action);
    }

    public void load() {
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            for (Action action : Action.values()) {
                Class<?> loader = classLoader.loadClass("dynamic.communication." + action.name().toLowerCase());
                //System.out.println("Dynamic loading: " + loader.getCanonicalName());
                Packet dc = (Packet) loader.newInstance();
                performables.put(action, dc);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DynamicLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
