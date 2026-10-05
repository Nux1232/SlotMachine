/** A symbol that shrinks each time its spin effect is applied. */
public class EphemeralSymbol extends Symbol {
    private static final int INITIAL_SIZE = 50;
    private static final int MINIMUM_SIZE = 10;
    private static final int SIZE_STEP = 5;
    private int size;

    public EphemeralSymbol(String color) {
        super(color);
        size = INITIAL_SIZE;
        resizeShape();
    }

    @Override
    public void spinEffect() {
        if (size > MINIMUM_SIZE) {
            size = Math.max(MINIMUM_SIZE, size - SIZE_STEP);
            resizeShape();
        }
    }

    public int getSize() { return size; }

    private void resizeShape() {
        if (symbolShape instanceof Circle) {
            ((Circle) symbolShape).changeSize(size);
        } else if (symbolShape instanceof Rectangle) {
            ((Rectangle) symbolShape).changeSize(size, size);
        } else if (symbolShape instanceof Triangle) {
            ((Triangle) symbolShape).changeSize(size, size);
        }
    }
}
