import java.util.List;
import java.util.Random;

/**
 * Represents a symbol displayed by a slot machine wheel.
 */
public class Symbol {
    private static final List<String> AVAILABLE_COLORS = List.of(
        "red", "green", "pink", "black",
        "yellow", "orange", "magenta", "cyan"
    );

    private final String color;

    /**
     * Creates a symbol with the specified color.
     *
     * @param color color used to display the symbol
     */
    public Symbol(String color) {
        this.color = color;
    }

    /**
     * Returns this symbol's color.
     *
     * @return the symbol color
     */
    public String getColor() {
        return color;
    }

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
     * Creates a symbol with a randomly selected available color.
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
}
