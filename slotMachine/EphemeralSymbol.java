/** A symbol that becomes smaller each time the wheel selects it. */
public class EphemeralSymbol extends Symbol {
    private static final int INITIAL_SIZE = 50;
    private static final int MINIMUM_SIZE = 10;
    private static final int SIZE_STEP = 5;
    private int size = INITIAL_SIZE;
    private int x;
    private int y;

    public EphemeralSymbol(String color) {
        super(color);
        resizeShape();
    }

    /** Compatibility constructor: the project represents symbols by color only. */
    public EphemeralSymbol(String color, String ignoredShape) { this(color); }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        super.setPosition(x, y);
    }

    @Override
    public void spinEffect() { onWheelSpin(); }

    public void onWheelSpin() {
        if (size > MINIMUM_SIZE) {
            size = Math.max(MINIMUM_SIZE, size - SIZE_STEP);
            resizeShape();
            super.setPosition(x, y);
        }
    }

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
