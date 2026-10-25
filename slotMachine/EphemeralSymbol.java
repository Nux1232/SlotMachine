/** A symbol that becomes smaller each time the wheel selects it. */
public class EphemeralSymbol extends Symbol {
    private static final int INITIAL_SIZE = 50;
    private static final int MINIMUM_SIZE = 10;
    private static final int SIZE_STEP = 5;
    private int size = INITIAL_SIZE;
    private int x;
    private int y;
    /**
     * Creates an ephemeral symbol that shrinks whenever it is selected.
     *
     * @param color supported symbol color
     */
    public EphemeralSymbol(String color) {
        super(color);
        Circle mark = new Circle();
        mark.changeSize(BADGE_SIZE);
        mark.changeColor("black");
        setBadge(mark);
        resizeShape();
    }

    /** Remembers its position and updates the symbol and badge positions. */
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        super.setPosition(x, y);
    }

    /** Shrinks this symbol when selected by its wheel. */
    @Override
    public void spinEffect() { onWheelSpin(); }

    /** Reduces the symbol size by one step, down to the minimum size. */
    public void onWheelSpin() {
        if (size > MINIMUM_SIZE) {
            size = Math.max(MINIMUM_SIZE, size - SIZE_STEP);
            resizeShape();
            super.setPosition(x, y);
        }
    }

    /** @return the current symbol size in pixels */
    public int getSize() { return size; }

    void setSize(int newSize) {
        size = Math.max(MINIMUM_SIZE, Math.min(INITIAL_SIZE, newSize));
        resizeShape();
    }

    @Override
    Symbol copy() {
        EphemeralSymbol copy = new EphemeralSymbol(getColor());
        copy.setSize(size);
        return copy;
    }

    private void resizeShape() {
        if (symbolShape instanceof Circle) ((Circle) symbolShape).changeSize(size);
        else if (symbolShape instanceof Rectangle) ((Rectangle) symbolShape).changeSize(size, size);
        else if (symbolShape instanceof Triangle) ((Triangle) symbolShape).changeSize(size, size);
    }
}
