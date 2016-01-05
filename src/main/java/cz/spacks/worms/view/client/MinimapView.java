package cz.spacks.worms.view.client;

import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.controller.AbstractView;
import cz.spacks.worms.controller.defaults.DefaultComponentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 *
 */
public class MinimapView extends AbstractView implements DefaultComponentListener {

    private static final Logger logger = Logger.getLogger(MinimapView.class.getName());

    private static MinimapView instance;

    public static MinimapView getInstance() {
        if (instance == null) instance = new MinimapView();
        return instance;
    }

    private MinimapView() {
        super(1);

        setPreferredSize(new Dimension(300, 200));
        SwingUtilities.invokeLater(this::init);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int compositeRule = AlphaComposite.SRC_ATOP;
        float alphaValue = 0.6f;
        final AlphaComposite alphaComposite = AlphaComposite.getInstance(compositeRule, alphaValue);

        Graphics2D g = (Graphics2D) graphics;
        final BufferedImage mapImage = map.getImage();
        final BufferedImage glass = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D imgGraphics = (Graphics2D) glass.getGraphics();
        for (Body b : bodies) b.draw(imgGraphics);
        for (GraphicComponent c : objects) c.draw(imgGraphics);
        g.setComposite(alphaComposite);
        g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(glass, 0, 0, getWidth(), getHeight(), null);
    }


    private void recalculateGraphicWindowLayout() {
        final Dimension dimension = getSize();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        recalculateGraphicWindowLayout();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }
}
