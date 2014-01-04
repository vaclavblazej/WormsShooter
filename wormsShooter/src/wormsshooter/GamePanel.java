package wormsshooter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import spritesheets.Controls;
import spritesheets.ControlsEnum;

/**
 *
 * @author Skarab
 */
public class GamePanel extends JPanel implements ActionListener {

    private static Controls controls;
    private BufferedImage image;
    private Timer timer;
    private Worm worm;

    public GamePanel() {
        image = new BufferedImage(
                GameWindow.WIDTH,
                GameWindow.HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        timer = new Timer(20, this);
        // when invokeLater is not used, first game is spoiled by bug
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

        controls = new Controls()
                .add(ControlsEnum.Up, 38)
                .add(ControlsEnum.Down, 40)
                .add(ControlsEnum.Right, 39)
                .add(ControlsEnum.Left, 37);
    }

    public void startMoving() {
        timer.start();
    }

    public void init() {
        worm = new Worm(200, 200);
        repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(image, null, this);
        if (worm != null) {
            worm.draw(g, this);
        }
        g.scale(4, 4);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        worm.tick();
        repaint();
    }

    void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        System.out.println(i + " " + en);
        if (en != null) {
            worm.controlOn(en);
        }
    }

    void keyReleased(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        System.out.println(i + " " + en);
        if (en != null) {
            worm.controlOff(en);
        }
    }
}
