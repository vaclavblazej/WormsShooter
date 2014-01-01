package wormsshooter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Skarab
 */
public class GamePanel extends JPanel implements ActionListener {

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
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        worm.tick();
        repaint();
    }
}
