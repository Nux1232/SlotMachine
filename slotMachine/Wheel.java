import java.util.ArrayList;

/** A graphical wheel that holds symbols and displays one selected symbol. */
public class Wheel {
    public enum Type { NORMAL, LEFTY, REBEL, CRAZY }

    private final Type type;
    private final ArrayList<Symbol> symbols = new ArrayList<>();
    private final Rectangle wheelShape = new Rectangle();
    private final Rectangle window = new Rectangle();
    private final Rectangle typeBand = new Rectangle();
    private final Shapes typeMarker;
    private final Shapes typeMarkerAccent;
    private int selectedIndex = -1;
    private int xPosition;
    private int yPosition;
    private boolean visible;
    private boolean locked;

    public Wheel() { this(Type.NORMAL); }

    protected Wheel(Type type) {
        this.type = type;
        wheelShape.changeSize(210, 70);
        wheelShape.changeColor("white");
        window.changeSize(70, 70);
        window.changeColor("white");
        typeBand.changeSize(12, 210);
        typeBand.changeColor(typeColor(type));
        typeMarker = createTypeMarker(type);
        typeMarkerAccent = createTypeMarkerAccent(type);
    }

    public Type getType() { return type; }
    public boolean isRebel() { return false; }
    public boolean isLocked() { return locked; }

    private String typeColor(Type wheelType) {
        if (wheelType == Type.LEFTY) return "cyan";
        if (wheelType == Type.REBEL) return "red";
        if (wheelType == Type.CRAZY) return "magenta";
        return "gray";
    }

    private Shapes createTypeMarker(Type wheelType) {
        Shapes marker;
        if (wheelType == Type.LEFTY) {
            Triangle triangle = new Triangle();
            triangle.changeSize(28, 28);
            marker = triangle;
        } else if (wheelType == Type.REBEL) {
            Rectangle rectangle = new Rectangle();
            rectangle.changeSize(28, 28);
            marker = rectangle;
        } else {
            Circle circle = new Circle();
            circle.changeSize(wheelType == Type.CRAZY ? 28 : 20);
            marker = circle;
        }
        marker.changeColor(typeColor(wheelType));
        return marker;
    }

    private Shapes createTypeMarkerAccent(Type wheelType) {
        if (wheelType != Type.CRAZY) return null;
        Triangle accent = new Triangle();
        accent.changeSize(12, 12);
        accent.changeColor("yellow");
        return accent;
    }

    public void setPositionWheel(int x, int y) {
        xPosition = x;
        yPosition = y;
        wheelShape.setPosition(x, y);
        typeBand.setPosition(x, y);
        typeMarker.setPosition(x + 180, y + 20);
        if (typeMarkerAccent != null) typeMarkerAccent.setPosition(x + 180, y + 28);
        window.setPosition(x, y + 65);
        positionSelectedSymbol();
    }

    public void makeWheelVisible() {
        visible = true;
        wheelShape.makeVisible();
        typeBand.makeVisible();
        typeMarker.makeVisible();
        if (typeMarkerAccent != null) typeMarkerAccent.makeVisible();
        window.makeVisible();
        showSelectedSymbol();
    }

    public void makeWheelInvisible() {
        visible = false;
        wheelShape.makeInvisible();
        typeBand.makeInvisible();
        typeMarker.makeInvisible();
        if (typeMarkerAccent != null) typeMarkerAccent.makeInvisible();
        window.makeInvisible();
        if (getSymbol() != null) getSymbol().makeInvisible();
    }

    /** Adds a symbol to this wheel's symbol set. */
    public void addSymbolWheel(String color) {
        if (Symbol.isAvailableColor(color)) addSymbolWheel(new Symbol(color));
    }

    /** Adds a symbol while retaining its specialized behavior. */
    public void addSymbolWheel(Symbol symbol) {
        if (symbol == null || !Symbol.isAvailableColor(symbol.getColor())) return;
        symbols.add(symbol);
        if (selectedIndex < 0) selectedIndex = 0;
        positionSelectedSymbol();
        showSelectedSymbol();
    }

    /** Replaces the symbol set with one symbol of the given color. */
    public void placeSymbolWheel(String color) {
        if (!Symbol.isAvailableColor(color)) return;
        clearSymbols();
        addSymbolWheel(new Symbol(color));
    }

    public void placeSymbolWheel(Symbol symbol) {
        if (symbol == null || !Symbol.isAvailableColor(symbol.getColor())) return;
        clearSymbols();
        addSymbolWheel(symbol);
    }

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

    /** Rotates this wheel once. Specialized wheels override this behavior. */
    public void spin() { spin((Wheel) null); }

    public void spin(Wheel leftNeighbor) {
        if (locked || symbols.isEmpty()) return;
        rotateOnce();
    }

    /** Applies one deterministic step for the supplied spin/test values. */
    public void spin(String randomColor, String leftColor) {
        if (locked || symbols.isEmpty()) return;
        if (Symbol.isAvailableColor(randomColor)) {
            if (symbols.size() == 1) placeSymbolWheel(randomColor);
            else rotateOnce();
        }
    }

    public void rotateOnce() {
        if (symbols.isEmpty()) return;
        if (selectedIndex >= 0) symbols.get(selectedIndex).makeInvisible();
        selectedIndex = (selectedIndex + 1) % symbols.size();
        applySelectedEffect();
    }

    protected void applySelectedEffect() {
        Symbol selected = getSymbol();
        if (selected != null) selected.spinEffect();
        positionSelectedSymbol();
        showSelectedSymbol();
    }

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

    public Symbol getSymbol() {
        return selectedIndex < 0 || selectedIndex >= symbols.size() ? null : symbols.get(selectedIndex);
    }

    public String symbolColor() {
        Symbol selected = getSymbol();
        if (selected == null) return null;
        if (selected instanceof ShySymbol && !((ShySymbol) selected).isShyVisible()) return "";
        return selected.getColor();
    }

    public void lock() { if (!isRebel()) locked = true; }
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
