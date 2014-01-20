package utilities;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import objects.CollisionState;

/**
 *
 * @author Skarab
 */
public class Materials {

    private static final Map<Color, CollisionState> background;
    public static final CollisionState DEFAULT = CollisionState.Crushed;

    static {
        background = new HashMap<>();
        background.put(Color.decode("#84AAF8"), CollisionState.Free);
        background.put(Color.decode("#976B4B"), CollisionState.Free);
    }

    public static CollisionState check(Color color) {
        CollisionState result = background.get(color);
        return (result != null) ? result : DEFAULT;
    }
}
