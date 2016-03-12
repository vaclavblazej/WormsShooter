package cz.spacks.worms.controller;

import cz.spacks.worms.controller.properties.ControlsEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Controls implements Serializable {

    private Map<Integer, ControlsEnum> keys;
    private Map<ControlsEnum, Integer> actions;

    public Controls() {
        if (keys == null) {
            keys = new HashMap<>();
            actions = new HashMap<>();
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
