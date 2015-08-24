package utilities.properties;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Václav Blažej
 */
public enum Sounds {

    CASH_REGISTER;
    private static Properties properties;

    public String value() {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("sounds.properties"));
            }
            return Paths.SOUND_FOLDER.value() + properties.getProperty(name()) + Paths.SOUND_FORMAT.value();
        } catch (IOException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
