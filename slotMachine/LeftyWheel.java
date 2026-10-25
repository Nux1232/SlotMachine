/** A wheel that copies the selected symbol from its left neighbor. */
public class LeftyWheel extends Wheel {
    /** Creates a wheel that copies the selected symbol from its left neighbor. */
    public LeftyWheel() {
        super(Type.LEFTY);
    }

    /** Copies its left neighbor's selection, or advances when none is available. */
    @Override
    public void spin(Wheel leftNeighbor) {
        if (isLocked() || getSymbol() == null) return;
        if (leftNeighbor != null && leftNeighbor.getSymbol() != null) {
            copySelectedFrom(leftNeighbor);
        } else {
            rotateOnce();
        }
    }

    /** Uses the supplied left color, falling back to standard spin behavior. */
    @Override
    public void spin(String randomColor, String leftColor) {
        if (isLocked() || getSymbol() == null) return;
        if (Symbol.isAvailableColor(leftColor)) {
            placeSymbolWheel(leftColor);
        } else {
            super.spin(randomColor, leftColor);
        }
    }
}
