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
 * call: <code>SoundManager.playSound(Sounds.CASH_REGISTER);</code> to play sound of
 * "CASH_REGISTER"
 *
 * @author Václav Blažej
 */
public class SoundManager {

    private static Logger logger = Logger.getLogger(SoundManager.class.getName());

    private static Sounds sound;
    private static HashMap<Sounds, File> cache = new HashMap<>();

    public static synchronized void playSound(Sounds sound) {
        Runnable runnable = new PlaySound(sound);
        new Thread(runnable).start();
    }

    private static class PlaySound implements Runnable {
        private Sounds sound;

        public PlaySound(Sounds sound) {
            this.sound = sound;
        }

        @Override
        public void run() {
            final File soundFile;
            if (cache.containsKey(sound)) {
                soundFile = cache.get(sound);
            } else {
                URL url = Application.class.getResource(sound.value());
                soundFile = new File(url.getPath());
                cache.put(sound, soundFile);
            }
            AudioInputStream audioInputStream;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    private SoundManager() {
    }
}
