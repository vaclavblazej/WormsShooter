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
    Client_window_title;

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
