package cz.spacks.worms.controller.properties;

import cz.spacks.worms.controller.PropertyLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public enum ControlsEnum implements PropertyLoader {

    UP,
    DOWN,
    LEFT,
    RIGHT,
    JUMP,
    FIRE,
    MINE,
    CHAT,
    INTERACT,
    INVENTORY_TOGGLE,
    MAP_TOGGLE;

    private static Properties properties;
    private static final Logger logger = Logger.getLogger(ControlsEnum.class.getName());

    public static List<ControlsEnum> getBindableControls() {
        return Arrays.asList(ControlsEnum.values());
    }

    public String getName() {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("controlNames.properties"));
            }
            return properties.getProperty(name());
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
