import java.util.Random;
import java.util.List;

/**
 * Represents one of the symbols displayed by a slot machine wheel.
 *
 * Symbols are identified by color and rendered as one of the supported shapes.
 */
public class Symbol {
    private static final List<String> AVAILABLE_COLORS = List.of(
        "red", "black", "green", "orange", "yellow", "magenta",
        "brown", "gray", "pink", "cyan"
    );
    private final String color;
    protected Shapes symbolShape;
    protected Shapes badge;
    protected static final int BADGE_SIZE = 10;

    public Symbol(String color) {
        if (!isAvailableColor(color)) {
            throw new IllegalArgumentException("Unsupported symbol color: " + color);
        }
        this.color = color;
        // The shape depends on the position of the color in the palette,
        // so new colors get a shape automatically.
        switch (Math.floorMod(AVAILABLE_COLORS.indexOf(color), 3)) {
            case 0:
                symbolShape = new Triangle();
                ((Triangle) symbolShape).changeSize(50, 50);
                break;
            case 1:
                symbolShape = new Rectangle();
                ((Rectangle) symbolShape).changeSize(50, 50);
                break;
            default:
                symbolShape = new Circle();
                ((Circle) symbolShape).changeSize(50);
                break;
        }
        symbolShape.changeColor(color);
    }
    /**
     * Returns this symbol's color.
     *
     * @return the symbol color
     */
    public String getColor() {
        return color;
    }

    /** Creates an independent copy for wheels that mirror a neighbor. */
    Symbol copy() { return new Symbol(color); }

    /**
     * Checks whether this symbol has the specified color.
     *
     * @param otherColor color to compare
     * @return true when both colors match
     */
    public boolean hasColor(String otherColor) {
        return color != null && color.equals(otherColor);
    }

    /**
     * Creates a symbol with a randomly selected available symbol color.
     *
     * @param random source used to select the color
     * @return a new randomly colored symbol
     */
    public static Symbol random(Random random) {
        return new Symbol(AVAILABLE_COLORS.get(random.nextInt(AVAILABLE_COLORS.size())));
    }

    /**
     * Checks whether a color belongs to the symbol palette supported by the
     * current version of the slot machine.
     *
     * @param color color to check
     * @return {@code true} when the color is supported
     */
    public static boolean isAvailableColor(String color) {
        return color != null && AVAILABLE_COLORS.contains(color);
    }

    /**
     * Return the color list supported by the actual version.
     * @return AVAILABLE_COLORS Color supported
     */
    public static List<String> getAvailableColors() {
        return AVAILABLE_COLORS;
    }

    /**
     * Set the position of the symbol
     * @param x horizontal place
     * @param y vertical place
     */
    public void setPosition(int x, int y) {
        if (symbolShape != null) {
            int adjustedX = x;
            if (symbolShape instanceof Triangle) {
                adjustedX = x + 30;
            }
            symbolShape.setPosition(adjustedX, y);
        }
        if (badge != null) {
            badge.setPosition(x + 50, y - 10);
        }
    }

    /**
     * Makes visible the symbol
     */
    public void makeVisible() {
        if (symbolShape != null) {
            symbolShape.makeVisible();
        }
        if (badge != null) {
            badge.makeVisible();
        }
    }

    /**
     * Makes invisible the symbol
     */
    public void makeInvisible() {
        if (symbolShape != null) {
            symbolShape.makeInvisible();
        }
        if (badge != null) {
            badge.makeInvisible();
        }
    }

    /**
     * Reacts to a Spin
     * In a Normal Symbol this method makes no funtion.
     */
    public void spinEffect() {

    }

    /**
     * Sets the small mark that tells this symbol type apart from the others.
     * Normal symbols have no badge.
     *
     * @param newBadge shape drawn in the upper right corner of the wheel window
     */
    protected void setBadge(Shapes newBadge) {
        badge = newBadge;
    }

    /**
     * Tells whether this symbol has a badge.
     *
     * @return true when the symbol is marked as a special type
     */
    public boolean hasBadge() {
        return badge != null;
    }
}
