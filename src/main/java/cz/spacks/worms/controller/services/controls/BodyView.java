package cz.spacks.worms.controller.services.controls;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BodyView implements GraphicComponent {

    private Dimension viewSize;

    private Body body;

    public BodyView(Body body) {
        this.body = body;
        final Dimension bodyBlockSize = body.getBodyBlockSize();
        int ratio = Settings.RATIO;
        this.viewSize = new Dimension(bodyBlockSize.width * ratio, bodyBlockSize.height * ratio);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        final Point position = body.getPosition();
        final Dimension bodyBlockSize = body.getBodyBlockSize();
        g.fillRect(position.x / Settings.BLOCK_SIZE, position.y / Settings.BLOCK_SIZE, bodyBlockSize.width, bodyBlockSize.height);
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        AffineTransform tr = new AffineTransform(trans);
        final Point position = body.getPosition();
        tr.translate(position.x, position.y);
//        tr.rotate(rotation);
        g.setTransform(tr);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, viewSize.width, viewSize.height);
        g.setColor(Color.RED);
        g.drawRect(0, 0, viewSize.width, viewSize.height);

//        final int top = position.y / Application.BLOCK_SIZE;
//        final int bottom = (position.y + physicsSize.height - 1) / Application.BLOCK_SIZE;
//        int x = (position.x + physicsSize.width) / Application.BLOCK_SIZE;

//        g.drawImage(frame.getFrame(), null, null);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
