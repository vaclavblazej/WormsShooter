package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;

import java.awt.*;
import java.io.Serializable;

/**
 *
 */
public interface ItemAction extends Serializable {

    ActionClient action(Point point);
}
