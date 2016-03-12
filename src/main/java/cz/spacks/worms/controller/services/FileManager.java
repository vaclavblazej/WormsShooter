package cz.spacks.worms.controller.services;

import cz.spacks.worms.controller.Settings;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class FileManager {

    @SuppressWarnings("unchecked")
    public static <T> T load(String name) {
        try {
            File f = new File(name);
            if (!f.exists()) {
                return null;
            }
            FileInputStream file = new FileInputStream(f);
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(file));
            final Object o = input.readObject();
            return (T) o;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static <T> void save(String name, T object) {
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
