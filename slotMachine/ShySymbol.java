/** A symbol that alternates visibility whenever the wheel selects it. */
public class ShySymbol extends Symbol {
    private boolean hidden;

    public ShySymbol(String color) {
        super(color);
        Rectangle mark = new Rectangle();
        mark.changeSize(BADGE_SIZE, BADGE_SIZE);
        mark.changeColor("blue");
        setBadge(mark);
    }

    @Override
    public void spinEffect() { onSelected(); }

    public void onSelected() {
        boolean shapeWasVisible = symbolShape != null && symbolShape.isVisible;
        hidden = !hidden;
        if (shapeWasVisible) {
            if (hidden) super.makeInvisible();
            else super.makeVisible();
        }
    }

    @Override
    public void makeVisible() {
        if (!hidden) super.makeVisible();
    }

    public boolean isShyVisible() { return !hidden; }
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
