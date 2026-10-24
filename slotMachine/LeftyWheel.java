/** A wheel that copies the selected symbol from its left neighbor. */
public class LeftyWheel extends Wheel {
    public LeftyWheel() {
        super(Type.LEFTY);
    }

    @Override
    public void spin(Wheel leftNeighbor) {
        if (isLocked() || getSymbol() == null) return;
        if (leftNeighbor != null && leftNeighbor.getSymbol() != null) {
            copySelectedFrom(leftNeighbor);
        } else {
            rotateOnce();
        }
    }

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
