package cz.spacks.worms.objects.items.itemActions;

import java.awt.*;
import java.io.Serializable;

/**
 *
 */
public interface ItemAction extends Serializable {

    public void action(Point point);
}
