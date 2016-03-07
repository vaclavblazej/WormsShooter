package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.view.defaults.DefaultComponentListener;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

/**
 *
 */
public class MinimapView extends AbstractView implements DefaultComponentListener {

    public MinimapView() {
        setPreferredSize(new Dimension(300, 200));
        WorldService worldService = new WorldService();
        setWorldService(worldService);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int compositeRule = AlphaComposite.SRC_ATOP;
        float alphaValue = 0.6f;
        final AlphaComposite alphaComposite = AlphaComposite.getInstance(compositeRule, alphaValue);

        Graphics2D g = (Graphics2D) graphics;
        final BufferedImage mapImage = mapViewModel.getImage();
        final BufferedImage glass = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D imgGraphics = (Graphics2D) glass.getGraphics();
        for (Body b : worldModelCache.getBodies()) b.draw(imgGraphics);
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

}
