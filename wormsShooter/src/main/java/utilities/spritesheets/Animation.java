package utilities.spritesheets;

/**
 * @author Václav Blažej
 */

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation
    private boolean stopped;                // has animations stopped
    private List<Frame> frames = new ArrayList<>(5);    // Arraylist of frames 

    public Animation(ArrayList<Frame> frames) {
        this.stopped = true;
        frames.forEach(this::addFrame);
        this.frameCount = 0;
        this.frameDelay = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
    }

    public Animation(ArrayList<Frame> frames, int frameDelay) {
        this(frames);
        for (Frame frame : frames) {
            frame.setDuration(frameDelay);
        }
        this.frameDelay = frameDelay;
    }

    public void start() {
        if (!stopped) {
            return;
        }
        if (frames.isEmpty()) {
            return;
        }
        stopped = false;
    }

    public void stop() {
        if (frames.isEmpty()) {
            return;
        }
        stopped = true;
    }

    public void restart() {
        if (frames.isEmpty()) {
            return;
        }
        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    public int getDirection() {
        return animationDirection;
    }

    public void setDirection(int i) {
        animationDirection = i;
    }

    private void addFrame(Frame frame) {
        frames.add(frame);
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    public boolean update() {
        if (!stopped) {
            frameCount++;
            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;
                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                } else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
                return true;
            }
        }
        return false;
    }

    public int value() {
        return frames.get(currentFrame).getValue();
    }
}