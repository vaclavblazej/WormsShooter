package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Skarab
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
    ADDRESS_INITIAL,
    CLIENT_PORT_INITIAL,
    SERVER_PORT_INITIAL,
    OK_BUTTON,
    CANCEL_BUTTON,
    ERROR,
    OK_MESSAGE,
    ADDRESS_ERROR_MESSAGE,
    SOCKET_ERROR_MESSAGE,
    IMAGE_FORMAT,
    IMAGE_FOLDER,
    IMAGE_ONLINE_FOLDER,
    IMAGE_SAVE_FOLDER,
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

    public String cm() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("messages.properties"));
            return properties.getProperty(name());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "ERROR";
    }
}
