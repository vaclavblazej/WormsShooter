package utilities;

/**
 * Information about player.
 *
 * @author Štìpán Plachý
 */
public class PlayerInfo {

    private final String name;
    private final int id;

    public PlayerInfo(int id) {
        this.id = id;
        name = "";
    }

    public int getId() {
        return id;
    }
}
