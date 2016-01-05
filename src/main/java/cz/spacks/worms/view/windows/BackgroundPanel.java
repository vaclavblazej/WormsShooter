package cz.spacks.worms.view.windows;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class BackgroundPanel extends JPanel {

    Image img;

    public BackgroundPanel(Image img) {
        this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
