package client.menu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Controls;
import utilities.ControlsEnum;

/**
 *
 * @author Skarab
 */
public class Settings implements Serializable {

    private static Settings instance;
    private static final String SAVE_FILE = "settings.cfg";

    public static Settings getInstance() {
        if (instance == null) {
            //default settings if it fails to obtain controls from a file.
            if (!loadSettings()) {
                instance = new Settings();
            }
        }
        return instance;
    }

    private static boolean loadSettings() {
        try {
            File f = new File(SAVE_FILE);
            if (!f.exists()) {
                return false;
            }
            FileInputStream file = new FileInputStream(f);
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(file));
            instance = (Settings) input.readObject();
            return true;
        } catch (IOException | ClassNotFoundException ex) {
            return false;
        }
    }
    private int quality;
    private int volume;
    private Controls controls;

    private Settings() {
        controls = new Controls()
                .add(ControlsEnum.Up, 38)
                .add(ControlsEnum.Down, 40)
                .add(ControlsEnum.Right, 39)
                .add(ControlsEnum.Left, 37)
                .add(ControlsEnum.Fire, 32)
                .add(ControlsEnum.Mine, 67);
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    public Controls getControls() {
        return controls;
    }

    public void saveSettings() {
        try {
            FileOutputStream file = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(instance);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
