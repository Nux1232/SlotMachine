import java.util.ArrayList;

/**
 * Graphical base class for slot-machine wheels.
 *
 * <p>A wheel owns its symbols, renders its selected symbol, and exposes
 * overridable spin behavior for specialized wheel implementations.</p>
 */
public class Wheel {
    private static final int WHEEL_WIDTH = 70;
    private static final int WHEEL_HEIGHT = 80;
    private static final int TYPE_BAND_HEIGHT = 210;

    /** Built-in wheel behaviors supported by the legacy type-based API. */
    public enum Type {
        /** Advances through the wheel's symbols in insertion order. */
        NORMAL,
        /** Copies the selected symbol from the wheel immediately to the left. */
        LEFTY,
        /** Cannot be locked, unlocked, swapped, or deleted. */
        REBEL,
        /** Randomly advances, copies a neighbor, or reapplies an effect. */
        CRAZY
    }

    private final Type type;
    private final ArrayList<Symbol> symbols = new ArrayList<>();
    private final Rectangle wheelShape = new Rectangle();
    private final Rectangle window = new Rectangle();
    private final Rectangle typeBand = new Rectangle();
    private int selectedIndex = -1;
    private int xPosition;
    private int yPosition;
    private boolean visible;
    private boolean locked;

    /** Creates a standard wheel that advances through its symbols. */
    public Wheel() { this(Type.NORMAL); }

    /** Creates a wheel tagged with the supplied built-in type. */
    protected Wheel(Type type) {
        this.type = type;
        wheelShape.changeSize(WHEEL_HEIGHT, WHEEL_WIDTH);
        wheelShape.changeColor("white");
        window.changeSize(70, 70);
        window.changeColor("white");
        typeBand.changeSize(TYPE_BAND_HEIGHT, WHEEL_WIDTH);
        typeBand.changeColor(typeColor(type));
    }

    /** @return the built-in type associated with this wheel */
    public Type getType() { return type; }

    /** @return whether this wheel has rebel restrictions */
    public boolean isRebel() { return false; }

    /** @return whether this wheel is currently locked */
    public boolean isLocked() { return locked; }

    private String typeColor(Type wheelType) {
        if (wheelType == Type.LEFTY) return "cyan";
        if (wheelType == Type.REBEL) return "red";
        if (wheelType == Type.CRAZY) return "magenta";
        return "white";
    }

    /**
     * Positions the wheel body, type bar, window, and selected symbol.
     *
     * @param x horizontal coordinate of the wheel body
     * @param y vertical coordinate of the wheel body
     */
    public void setPositionWheel(int x, int y) {
        xPosition = x;
        yPosition = y;
        wheelShape.setPosition(x, y);
        typeBand.setPosition(x, y);
        window.setPosition(x, y + 65);
        positionSelectedSymbol();
    }

    /** Shows the wheel and its selected symbol. */
    public void makeWheelVisible() {
        visible = true;
        wheelShape.makeVisible();
        typeBand.makeVisible();
        window.makeVisible();
        showSelectedSymbol();
    }

    /** Hides the wheel and its selected symbol. */
    public void makeWheelInvisible() {
        visible = false;
        wheelShape.makeInvisible();
        typeBand.makeInvisible();
        window.makeInvisible();
        if (getSymbol() != null) getSymbol().makeInvisible();
    }

    /**
     * Adds a normal symbol with the requested color.
     *
     * @param color supported symbol color
     */
    public void addSymbolWheel(String color) {
        if (Symbol.isAvailableColor(color)) addSymbolWheel(new Symbol(color));
    }

    /**
     * Adds a symbol object, retaining any specialized symbol behavior.
     *
     * @param symbol symbol to add
     */
    public void addSymbolWheel(Symbol symbol) {
        if (symbol == null || !Symbol.isAvailableColor(symbol.getColor())) return;
        symbols.add(symbol);
        if (selectedIndex < 0) selectedIndex = 0;
        positionSelectedSymbol();
        showSelectedSymbol();
    }

    /**
     * Replaces all symbols with a normal symbol of the requested color.
     *
     * @param color supported symbol color
     */
    public void placeSymbolWheel(String color) {
        if (!Symbol.isAvailableColor(color)) return;
        clearSymbols();
        addSymbolWheel(new Symbol(color));
    }

    /**
     * Replaces all symbols with the supplied symbol object.
     *
     * @param symbol symbol to place
     */
    public void placeSymbolWheel(Symbol symbol) {
        if (symbol == null || !Symbol.isAvailableColor(symbol.getColor())) return;
        clearSymbols();
        addSymbolWheel(symbol);
    }

    /**
     * Removes every symbol with the requested color.
     *
     * @param color color of the symbols to remove
     */
    public void delSymbolWheel(String color) {
        for (int i = symbols.size() - 1; i >= 0; i--) {
            if (symbols.get(i).hasColor(color)) {
                boolean selected = i == selectedIndex;
                symbols.get(i).makeInvisible();
                symbols.remove(i);
                if (symbols.isEmpty()) selectedIndex = -1;
                else if (i < selectedIndex) selectedIndex--;
                else if (selected) selectedIndex %= symbols.size();
            }
        }
        showSelectedSymbol();
    }

    /**
     * Spins this wheel once without a left neighbor.
     * Specialized wheels can override either spin overload.
     */
    public void spin() { spin((Wheel) null); }

    /**
     * Spins this wheel once, optionally using its left neighbor.
     *
     * @param leftNeighbor wheel immediately to the left, or {@code null}
     */
    public void spin(Wheel leftNeighbor) {
        if (locked || symbols.isEmpty()) return;
        rotateOnce();
    }

    /**
     * Applies one step using caller-supplied values, primarily for deterministic
     * configuration and tests.
     *
     * @param randomColor color supplied for the standard-wheel behavior
     * @param leftColor color supplied by the left-neighbor behavior
     */
    public void spin(String randomColor, String leftColor) {
        if (locked || symbols.isEmpty()) return;
        if (Symbol.isAvailableColor(randomColor)) {
            if (symbols.size() == 1) placeSymbolWheel(randomColor);
            else rotateOnce();
        }
    }

    /** Advances the selected symbol by one position and applies its spin effect. */
    public void rotateOnce() {
        if (symbols.isEmpty()) return;
        if (selectedIndex >= 0) symbols.get(selectedIndex).makeInvisible();
        selectedIndex = (selectedIndex + 1) % symbols.size();
        applySelectedEffect();
    }

    /** Applies the selected symbol's spin effect and refreshes its display. */
    protected void applySelectedEffect() {
        Symbol selected = getSymbol();
        if (selected != null) selected.spinEffect();
        positionSelectedSymbol();
        showSelectedSymbol();
    }

    /**
     * Replaces this wheel's symbols with a copy of the neighbor's selection.
     *
     * @param leftNeighbor neighbor that supplies the selected symbol
     */
    protected void copySelectedFrom(Wheel leftNeighbor) {
        Symbol source = leftNeighbor.getSymbol();
        placeSymbolWheel(source.copy());
        positionSelectedSymbol();
        showSelectedSymbol();
    }

    private void clearSymbols() {
        for (Symbol symbol : symbols) symbol.makeInvisible();
        symbols.clear();
        selectedIndex = -1;
    }

    private void positionSelectedSymbol() {
        Symbol selected = getSymbol();
        if (selected != null) selected.setPosition(xPosition + 10, yPosition + 75);
    }

    private void showSelectedSymbol() {
        Symbol selected = getSymbol();
        if (!visible || selected == null) return;
        if (selected instanceof ShySymbol && !((ShySymbol) selected).isShyVisible()) {
            selected.makeInvisible();
        } else {
            selected.makeVisible();
        }
    }

    /** Returns the currently selected symbol, if any. */
    public Symbol getSymbol() {
        return selectedIndex < 0 || selectedIndex >= symbols.size() ? null : symbols.get(selectedIndex);
    }

    /**
     * Returns the visible color of the selected symbol.
     *
     * @return selected color, an empty string for a hidden shy symbol, or
     *         {@code null} when the wheel has no selected symbol
     */
    public String symbolColor() {
        Symbol selected = getSymbol();
        if (selected == null) return null;
        if (selected instanceof ShySymbol && !((ShySymbol) selected).isShyVisible()) return "";
        return selected.getColor();
    }

    /** Locks this wheel unless the wheel type disallows locking. */
    public void lock() { if (!isRebel()) locked = true; }

    /** Unlocks this wheel unless the wheel type disallows unlocking. */
    public void unlock() { if (!isRebel()) locked = false; }

    /**
     * Returns the colors of all the symbols of this wheel, not only the selected one.
     *
     * @return colors of the wheel's symbols in insertion order
     */
    public ArrayList<String> allColors() {
        ArrayList<String> colors = new ArrayList<>();
        for (Symbol symbol : symbols) {
            colors.add(symbol.getColor());
        }
        return colors;
    }
    /**
     * Selects the symbol of the given color if this wheel has one.
     *
     * @param color color to show
     * @return true when the wheel has a symbol with that color
     */
    public boolean selectColor(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).hasColor(color)) {
                if (selectedIndex >= 0) {
                    symbols.get(selectedIndex).makeInvisible();
                }
                selectedIndex = i;
                positionSelectedSymbol();
                showSelectedSymbol();
                return true;
            }
        }
        return false;
    }
}
