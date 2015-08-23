package utilities.properties;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Skarab
 */
public enum Paths {

    ADDRESS_INITIAL,
    CLIENT_PORT_INITIAL,
    SERVER_PORT_INITIAL,
    IMAGE_FORMAT,
    IMAGE_FOLDER,
    IMAGE_ONLINE_FOLDER,
    IMAGE_SAVE_FOLDER,
    SOUND_FORMAT,
    SOUND_FOLDER;
    private static Properties properties;

    public String cm() {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("paths.properties"));
            }
            return properties.getProperty(name());
        } catch (IOException ex) {
            Logger.getLogger(Paths.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
