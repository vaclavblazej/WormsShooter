package client.menu;

import utilities.Controls;
import utilities.ControlsEnum;
import utilities.FileManager;

import java.io.Serializable;

/**
 * @author Václav Blažej
 */
public class Settings implements Serializable {

    private static Settings instance;
    private static final String SAVE_FILE = "settings.cfg";

    public static Settings getInstance() {
        if (instance == null) {
            loadSettings();
        }
        return instance;
    }

    private static void loadSettings() {
        //default settings if it fails to obtain controls from a file.
        instance = (Settings) FileManager.load(SAVE_FILE);
        if (instance == null) {
            instance = new Settings();
        }
    }

    private int quality;
    private int volume;
    private Controls controls;

    private Settings() {
        controls = new Controls()
                .add(ControlsEnum.UP, 38)
                .add(ControlsEnum.DOWN, 40)
                .add(ControlsEnum.RIGHT, 39)
                .add(ControlsEnum.LEFT, 37)
                .add(ControlsEnum.FIRE, 32)
                .add(ControlsEnum.MINE, 67);
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
        FileManager.save(SAVE_FILE, this);
    }
}
