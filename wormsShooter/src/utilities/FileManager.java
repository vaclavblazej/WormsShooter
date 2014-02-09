package utilities;

import client.menu.Settings;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pajcak
 */
public class FileManager {

    public static Object load(String name) {
        try {
            File f = new File(name);
            if (!f.exists()) {
                return null;
            }
            FileInputStream file = new FileInputStream(f);
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(file));
            return input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static void save(String name, Object object) {
        try {
            FileOutputStream file = new FileOutputStream(name);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FileManager() {
    }
}
