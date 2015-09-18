package client.menu;

import utilities.Controls;
import utilities.ControlsEnum;
import utilities.FileManager;

import java.io.Serializable;

/**
 * @author V�clav Bla�ej
 */
public class Settings implements Serializable {

    private static Settings instance;
    private static final String SAVE_FILE = "settings.cfg";

    public static Settings getInstance() {
        if (instance == null) loadSettings();
        return instance;
    }

    private static void loadSettings() {
        //default settings if it fails to obtain controls from a file.
        instance = FileManager.load(SAVE_FILE);
        if (instance == null) instance = new Settings();
    }

    private int quality;
    private int volume;
    private Controls controls;

    private Settings() {
        controls = new Controls()
                .add(ControlsEnum.UP, 87)
                .add(ControlsEnum.DOWN, 83)
                .add(ControlsEnum.RIGHT, 68)
                .add(ControlsEnum.LEFT, 65)
                .add(ControlsEnum.JUMP, 32)
                .add(ControlsEnum.FIRE, 0)
                .add(ControlsEnum.MINE, 67)
                .add(ControlsEnum.CHAT, 10)
                .add(ControlsEnum.MAP_TOGGLE, 77);
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
