/**
 * A symbol that shrinks every time it is selected by a spin,
 * until it is reduced to a point.
 */
public class EphemeralSymbol extends Symbol {
    private static final int MIN_SIZE = 1;
    private static final int REDUCTION_STEP = 10;
    private int currentSize;
    private int xPos;
    private int yPos;
 
    /**
     * Creates an ephemeral symbol of the given color.
     *
     * @param color symbol color
     */
    public EphemeralSymbol(String color) {
        super(color);
        currentSize = 50;
    }
 
    /**
     * Remembers the position so it can be restored after resizing.
     *
     * @param x horizontal place
     * @param y vertical place
     */
    @Override
    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
        super.setPosition(x, y);
    }
 
    /**
     * Reduces the size of the symbol, down to a minimum of one point.
     */
    @Override
    public void spinEffect() {
        currentSize = Math.max(MIN_SIZE, currentSize - REDUCTION_STEP);
        if (symbolShape instanceof Triangle) {
            ((Triangle) symbolShape).changeSize(currentSize, currentSize);
        } else if (symbolShape instanceof Rectangle) {
            ((Rectangle) symbolShape).changeSize(currentSize, currentSize);
        } else if (symbolShape instanceof Circle) {
            ((Circle) symbolShape).changeSize(currentSize);
        }
        super.setPosition(xPos, yPos);
    }
 
    /**
     * Returns the current size of the symbol.
     *
     * @return current size
     */
    public int getCurrentSize() {
        return currentSize;
    }
}