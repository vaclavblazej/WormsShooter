package utilities;

import main.Application;
import utilities.properties.Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to play sounds in program.
 * <p/>
 * call: SoundManager.playSound(Sounds.CASH_REGISTER); to play sound of
 * "CASH_REGISTER"
 *
 * @author Skarab
 */
public class SoundManager {

    private static Sounds sound;
    private static HashMap<Sounds, Clip> cache;

    public static synchronized void playSound(Sounds sound) {
        SoundManager.sound = sound;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = Application.class.getResource(SoundManager.sound.cm());
                File soundFile = new File(url.getPath());
                AudioInputStream audioInputStream;
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private SoundManager() {
    }
}
