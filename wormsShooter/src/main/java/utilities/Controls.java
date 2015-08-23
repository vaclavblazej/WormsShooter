package utilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Skarab
 */
public class Controls implements Serializable {

    private Map<Integer, ControlsEnum> keys;
    private Map<ControlsEnum, Integer> actions;

    public Controls() {
        if (keys == null) {
            keys = new HashMap<>(10);
            actions = new HashMap<>(10);
        }
    }

    public Controls add(ControlsEnum en, Integer i) {
        keys.put(i, en);
        actions.put(en, i);
        return this;
    }

    public Controls rebind(ControlsEnum en, Integer i) {
        keys.remove(get(en));
        actions.remove(get(i));
        keys.remove(i);
        actions.remove(en);
        add(en, i);
        return this;
    }

    public Integer get(ControlsEnum en) {
        return actions.get(en);
    }

    public ControlsEnum get(Integer i) {
        return keys.get(i);
    }
}
