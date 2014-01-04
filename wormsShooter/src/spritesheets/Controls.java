package spritesheets;

import spritesheets.ControlsEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Skarab
 */
public class Controls {

    public Map<Integer, ControlsEnum> keys;

    public Controls() {
    }

    public Controls add(ControlsEnum en, Integer i) {
        if (keys == null) {
            keys = new HashMap<>(6);
        }
        keys.put(i, en);
        return this;
    }
    
    public ControlsEnum get(Integer i){
        return keys.get(i);
    }
}
