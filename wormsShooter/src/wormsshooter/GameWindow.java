package wormsshooter;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author Skarab
 */
public class GameWindow extends JFrame implements KeyListener {

    private static GamePanel gamePlane;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public GameWindow() {
        super("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        gamePlane = new GamePanel();
        gamePlane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gamePlane.setFocusable(true);
        add(gamePlane);
        pack();

        gamePlane.startMoving();
        setFocusable(true);
        addKeyListener(this);
        setLocationRelativeTo(null);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}

