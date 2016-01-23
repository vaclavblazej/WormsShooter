package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.services.SpriteLoader;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.WorldModel;
import cz.spacks.worms.model.objects.items.*;
import cz.spacks.worms.model.objects.items.itemActions.ItemActionMine;
import cz.spacks.worms.model.objects.items.itemActions.ItemActionShoot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 */
public class ServerView extends AbstractView implements ComponentListener {

    private BufferedImage rasteredView;

    public ServerView() {

        SwingUtilities.invokeLater(this::recalculateGraphicWindowLayout);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        final BufferedImage image = mapModelCache.getImage();
        final BufferedImage glass = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D imgGraphics = (Graphics2D) glass.getGraphics();
        for (Body b : worldModelCache.getBodies()) b.draw(imgGraphics);
        final Graphics raster = rasteredView.getGraphics();
        raster.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        raster.drawImage(glass, 0, 0, glass.getWidth(), glass.getHeight(), null);
        g.drawImage(rasteredView, 0, 0, getWidth(), getHeight(), null);
    }


    private void recalculateGraphicWindowLayout() {
        final Dimension dimension = getSize();
        rasteredView = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        recalculateGraphicWindowLayout();
    }

}
