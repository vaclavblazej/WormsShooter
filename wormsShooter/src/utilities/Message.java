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

    Server_window_title,
    Client_window_title,
    Launcher_window_title,
    Create_server,
    Address,
    Socket,
    Address_initial,
    Socket_initial,
    OK_button,
    Cancel_button,
    Error,
    OK_message,
    Address_error_message,
    Socket_error_message,
    Image_format,
    Image_folder,
    Image_online_folder,
    Image_save_folder,
    Image_load_error;

    public String cm() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            URL url = Message.class.getResource("messages.properties");
            prop.load(new FileInputStream(url.getPath()));
            return prop.getProperty(name());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "ERROR";
    }
}
