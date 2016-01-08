package cz.spacks.worms.controller.services;

import cz.spacks.worms.Application;
import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.properties.Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to play sounds in program.
 * <p>
 * call: <code>SoundManager.playSound(Sounds.CASH_REGISTER);</code> to play sound of
 * "CASH_REGISTER"
 */
public class SoundManager {

    private static Logger logger = Logger.getLogger(SoundManager.class.getName());

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
            if (!cache.containsKey(sound)) {
                URL url = Application.class.getResource(sound.value());
                cache.put(sound, new File(url.getPath()));
            }
            final File soundFile = cache.get(sound);
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float setVol = volume.getMinimum() + (volume.getMaximum() - volume.getMinimum()) * Settings.getInstance().getVolume() / 100.0f;
                volume.setValue(setVol);
                if (!clip.isRunning()) {
                    clip.start();
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    private SoundManager() {
    }
}
