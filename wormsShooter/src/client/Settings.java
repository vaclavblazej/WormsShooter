package client;

import objects.Controls;
import objects.ControlsEnum;

/**
 *
 * @author Skarab
 */
public class Settings {

    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();

        }
        return instance;
    }
    private int quality;
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

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    public Controls getControls() {
        return controls;
    }
}
