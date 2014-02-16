package utilities.properties;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skarab
 */
public enum Sounds {

    CASH_REGISTER;
    private static Properties properties;

    public String cm() {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(this.getClass().getResourceAsStream("sounds.properties"));
            }
            return Paths.SOUND_FOLDER.cm() + properties.getProperty(name()) + Paths.SOUND_FORMAT.cm();
        } catch (IOException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
