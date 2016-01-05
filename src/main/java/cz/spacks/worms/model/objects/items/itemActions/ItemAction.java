package cz.spacks.worms.model.objects.items.itemActions;

import java.awt.*;
import java.io.Serializable;

/**
 *
 */
public interface ItemAction extends Serializable {

    void action(Point point);
}
