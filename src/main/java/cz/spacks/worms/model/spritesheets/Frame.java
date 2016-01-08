package cz.spacks.worms.model.spritesheets;

/**
 *
 */

import java.awt.image.BufferedImage;

public class Frame {

    private BufferedImage frame;
    private int duration;
    private int value;

    public Frame(BufferedImage frame) {
        this(frame, 1, 0);
    }

    public Frame(BufferedImage frame, int duration) {
        this(frame, duration, 0);
    }

    public Frame(BufferedImage frame, int duration, int value) {
        this.frame = frame;
        this.duration = duration;
        this.value = value;
    }

    public BufferedImage getFrame() {
        return frame;
    }

    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
