/** A symbol that alternates visibility whenever the wheel selects it. */
public class ShySymbol extends Symbol {
    private boolean hidden;

    /**
     * Creates a shy symbol that alternates visibility when selected.
     *
     * @param color supported symbol color
     */
    public ShySymbol(String color) {
        super(color);
        Rectangle mark = new Rectangle();
        mark.changeSize(BADGE_SIZE, BADGE_SIZE);
        mark.changeColor("blue");
        setBadge(mark);
    }

    /** Toggles this symbol's visibility. */
    @Override
    public void spinEffect() { onSelected(); }

    /** Toggles whether this symbol is visible. */
    public void onSelected() {
        boolean shapeWasVisible = symbolShape != null && symbolShape.isVisible;
        hidden = !hidden;
        if (shapeWasVisible) {
            if (hidden) super.makeInvisible();
            else super.makeVisible();
        }
    }

    /** Makes the symbol visible only when its hidden state permits it. */
    @Override
    public void makeVisible() {
        if (!hidden) super.makeVisible();
    }

    /** @return whether this symbol is currently visible */
    public boolean isShyVisible() { return !hidden; }

    /** @return whether this symbol is currently hidden */
    public boolean isHidden() { return hidden; }

    void setShyVisible(boolean visible) {
        hidden = !visible;
    }

    @Override
    Symbol copy() {
        ShySymbol copy = new ShySymbol(getColor());
        copy.setShyVisible(isShyVisible());
        return copy;
    }
}
