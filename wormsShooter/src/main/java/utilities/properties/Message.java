package utilities.properties;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Václav Blažej
 */
public enum Message {

    SERVER_WINDOW_TITLE,
    CLIENT_WINDOW_TITLE,
    LAUNCHER_WINDOW_TITLE,
    SETTINGS_WINDOW_TITLE,
    INVENTORY_WINDOW_TITLE,
    CREATE_SERVER,
    ADDRESS,
    CLIENT_PORT,
    SERVER_PORT,
    OK_BUTTON,
    CANCEL_BUTTON,
    ERROR,
    OK_MESSAGE,
    ADDRESS_ERROR_MESSAGE,
    SOCKET_ERROR_MESSAGE,
    IMAGE_LOAD_ERROR,
    KEY_SETTINGS,
    LEFT,
    RIGHT,
    UP,
    DOWN,
    SERVER_NAME,
    SOUND_LEVEL,
    SETTINGS_TAB_KEYS,
    SETTINGS_TAB_SOUND,
    DETAIL_LEVEL,
    SETTINGS_TAB_GRAPHICS,
    MENU_MAIN,
    MENU_SETTINGS,
    MENU_INVENTORY,
    MENU_EXIT,
    MENU_CONNECTION,
    MENU_CONNECT,
    MENU_DISCONNECT;
    private static Properties properties;

    public String cm() {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("messages.properties"));
            }
            return properties.getProperty(name());
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
