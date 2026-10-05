/**
 * A symbol that alternates between visible and invisible
 * each time it is selected in the wheel.
 */
public class ShySymbol extends Symbol {
    private boolean isHidden;
    /**
     * Creates a shy symbol of the given color. It starts visible.
     *
     * @param color symbol color
     */
    public ShySymbol(String color) {
        super(color);
        this.isHidden = false;
    }
 
    /**
     * Toggles between hidden and visible.
     */
    @Override
    public void spinEffect() {
        isHidden = !isHidden;
        if (isHidden) {
            super.makeInvisible();
        } else {
            super.makeVisible();
        }
    }

    @Override
    public void makeVisible() {
        if (!isHidden) {
            super.makeVisible();
        }
    }

    public boolean getIsShy() {
        return isHidden;
    }

    /**
     * Indicates whether the symbol is currently hidden.
     *
     * @return true when hidden
     */
    public boolean isHidden() {
        return isHidden;
    }
    }

    public boolean isHidden() { return isHidden; }
}
