package utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.Main;
import utilities.properties.Sounds;

/**
 * Used to play sounds in program.
 *
 * call: SoundManager.playSound(Sounds.CASH_REGISTER); to play sound of
 * "CASH_REGISTER"
 *
 * @author Skarab
 */
public class SoundManager {

    private static Sounds sound;

    public static synchronized void playSound(Sounds sound) {
        SoundManager.sound = sound;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = Main.class.getResource(SoundManager.sound.cm());
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
