package cz.spacks.worms.controller;

import cz.spacks.worms.controller.properties.ControlsEnum;
import cz.spacks.worms.controller.services.FileManager;
import cz.spacks.worms.model.Controls;
import javafx.scene.input.KeyCode;

import java.io.Serializable;

/**
 *
 */
public class Settings implements Serializable {

    public static final int RATIO = 20; // todo should define zoom in views
    public static final int BLOCK_SIZE = 32;

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
                .add(ControlsEnum.CHAT, KeyCode.ENTER.impl_getCode())
                .add(ControlsEnum.INTERACT, KeyCode.F.impl_getCode())
                .add(ControlsEnum.INVENTORY_TOGGLE, 71)
                .add(ControlsEnum.MAP_TOGGLE, 77);
        volume = 86;
        quality = 2;
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
