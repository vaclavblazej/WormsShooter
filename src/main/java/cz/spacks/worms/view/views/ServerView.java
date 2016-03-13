package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.services.controls.BodyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

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
        final BufferedImage image = mapViewModel.getImage();
        final BufferedImage glass = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D imgGraphics = (Graphics2D) glass.getGraphics();
        for (BodyView bodyView : bodyViews) {
            bodyView.draw(imgGraphics);
        }
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
