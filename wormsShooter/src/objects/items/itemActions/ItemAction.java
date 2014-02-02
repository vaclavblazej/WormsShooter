package objects.items.itemActions;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public interface ItemAction extends Serializable {

    public void action(Point point);
}
