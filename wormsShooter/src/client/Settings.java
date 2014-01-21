package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Controls;
import objects.ControlsEnum;

/**
 *
 * @author Skarab
 */
public class Settings implements Serializable {
    
    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            //default settings if it fails to obtain controls from a file.
            if (!loadSettings()) instance = new Settings();
        }
        return instance;
    }
    private int quality;
    private int volume;
    private Controls controls;

    private Settings() {
        // todo loading from file
        controls = new Controls()
                .add(ControlsEnum.Up, 38)
                .add(ControlsEnum.Down, 40)
                .add(ControlsEnum.Right, 39)
                .add(ControlsEnum.Left, 37)
                .add(ControlsEnum.Fire, 32);
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
            OutputStream file = new FileOutputStream("settings.cfg");
            ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(file));
            output.writeObject(this);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static boolean loadSettings() {
        try {
            InputStream file = new FileInputStream("settings.cfg");
            ObjectInput input = new ObjectInputStream(new BufferedInputStream(file));
            if (input.read() == -1) return false; //checks empty file 
            try {
                instance = (Settings)input.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
