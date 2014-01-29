package utilities;

import utilities.communication.Model;

/**
 *
 * @author Skarab
 */
public interface MapInterface {

    public CollisionState check(int x, int y);

    public Model getModel();

    public int getRatio();
}
