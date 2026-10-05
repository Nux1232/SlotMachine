/**
 * A symbol that alternates between visible and invisible
 * each time it is selected in the wheel.
 */
public class ShySymbol extends Symbol {
    private boolean isShy;
 
    /**
     * Creates a shy symbol of the given color. It starts visible.
     *
     * @param color symbol color
     */
    public ShySymbol(String color) {
        super(color);
        isShy = false;
    }
 
    /**
     * Toggles between hidden and visible.
     */
    @Override
    public void spinEffect() {
        isShy = !isShy;
        if (isShy) {
            super.makeInvisible();
        }
    }
 
    /**
     * Shows the symbol only when it is not currently hidden.
     */
    @Override
    public void makeVisible() {
        if (!isShy) {
            super.makeVisible();
        }
    }
 
    /**
     * Indicates whether the symbol is currently hidden.
     *
     * @return true when hidden
     */
    public boolean isHidden() {
        return isShy;
    }
}