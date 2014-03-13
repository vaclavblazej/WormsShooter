package utilities.materials;

/**
 *
 * @author Skarab
 */
public enum Opacity {

    OPAQUE(30),
    SEMITRANSPARENT(5),
    TRANSPARENT(2);
    int value;

    private Opacity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
