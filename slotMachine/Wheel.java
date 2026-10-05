import java.util.ArrayList;
import java.util.Random;

/** A graphical wheel that holds symbols and displays one selected symbol. */
public class Wheel {
    public enum Type { NORMAL, LEFTY, REBEL, CRAZY }

    private final Type type;
    private final ArrayList<Symbol> symbols = new ArrayList<>();
    private final Rectangle wheelShape = new Rectangle();
    private final Rectangle window = new Rectangle();
    private int selectedIndex = -1;
    private int xPosition;
    private int yPosition;
    private boolean visible;
    private boolean locked;

    public Wheel() { this(Type.NORMAL); }

    public Wheel(Type type) {
        this.type = type == null ? Type.NORMAL : type;
        wheelShape.changeSize(210, 70);
        wheelShape.changeColor("white");
        window.changeSize(70, 70);
        window.changeColor("white");
    }

    public Type getType() { return type; }
    public boolean isRebel() { return type == Type.REBEL; }
    public boolean isLocked() { return locked; }

    public void setPositionWheel(int x, int y) {
        xPosition = x;
        yPosition = y;
        wheelShape.setPosition(x, y);
        window.setPosition(x, y + 65);
        positionSelectedSymbol();
    }

    public void makeWheelVisible() {
        visible = true;
        wheelShape.makeVisible();
        window.makeVisible();
        showSelectedSymbol();
    }

    public void makeWheelInvisible() {
        visible = false;
        wheelShape.makeInvisible();
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

    /** Rotates according to this wheel's type. */
    public void spin() { spin((Wheel) null); }

    public void spin(Wheel leftNeighbor) {
        if (locked || symbols.isEmpty()) return;
        switch (type) {
            case LEFTY:
                if (leftNeighbor != null && leftNeighbor.getSymbol() != null) {
                    copySelectedFrom(leftNeighbor);
                } else {
                    rotateOnce();
                }
                break;
            case CRAZY:
                int choice = new Random().nextInt(3);
                if (choice == 0) rotateOnce();
                else if (choice == 1 && leftNeighbor != null && leftNeighbor.getSymbol() != null) {
                    copySelectedFrom(leftNeighbor);
                } else {
                    applySelectedEffect();
                }
                break;
            case NORMAL:
            case REBEL:
            default:
                rotateOnce();
                break;
        }
    }

    /** Applies one deterministic step for the supplied spin/test values. */
    public void spin(String randomColor, String leftColor) {
        if (locked || symbols.isEmpty()) return;
        if (type == Type.LEFTY && Symbol.isAvailableColor(leftColor)) {
            placeSymbolWheel(leftColor);
        } else if (type == Type.CRAZY) {
            spin();
        } else if (Symbol.isAvailableColor(randomColor)) {
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

    private void applySelectedEffect() {
        Symbol selected = getSymbol();
        if (selected != null) selected.spinEffect();
        positionSelectedSymbol();
        showSelectedSymbol();
    }

    private void copySelectedFrom(Wheel leftNeighbor) {
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
}
