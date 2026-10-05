/**
 * This class creates an extension of the Wheels created before.
 *
 * @author Samuel Infante Camargo
 * @author Juan Pablo Cuervo Contreras
 * @version Cliclo 1
 */

public class Wheel {
    public enum Type { NORMAL, LEFTY, REBEL, CRAZY }
    private final Type type;
    private Rectangle Wheel;
    private Rectangle window;
    private Shapes symbolShape;
    private int xPosition;
    private int yPosition;
    private boolean isVisible;
    private boolean locked;
    private Symbol symbol;

    /**
     * This is the constructor of the Wheel Class.
     */
    public Wheel() {
        this(Type.NORMAL);
    }

    public Wheel(Type type) {
        this.type = type == null ? Type.NORMAL : type;
        isVisible = false;
        locked = false;
        Wheel = new Rectangle();
        Wheel.changeSize(210, 70);
        Wheel.changeColor("white");
        window = new Rectangle();
        window.changeSize(70,70);
        window.changeColor("white");

    }

    /**
     * Set the position of the wheel and the window
     * @param x The X position in the screen.
     * @param y The Y position in the screen.
     */
    public void setPositionWheel(int x, int y){
        xPosition = x;
        yPosition = y;
        Wheel.setPosition(x, y);
        window.setPosition(x, y);
        window.moveVertical(65);
        if (symbolShape != null) {
            positionSymbolShape();
        }
    }

    /**
     * Makes Visible the wheel and the window.
     */
    public void makeWheelVisible() {
        isVisible = true;
        if (isVisible) {
            Wheel.makeVisible();
            window.makeVisible();
            if (symbolShape != null && !(symbol instanceof ShySymbol && ((ShySymbol) symbol).isHidden())) {
                symbolShape.makeVisible();
            }
        }
    }

    /**
     * Makes invisible the wheel and the window.
     */
    public void makeWheelInvisible() {
        isVisible = false;
        if (isVisible == false) {
            Wheel.makeInvisible();
            window.makeInvisible();
            if (symbolShape != null) {
                symbolShape.makeInvisible();
            }
        }
    }

    /**
     * Add a symbol in a wheel.
     * @param color The symbol that is going to be used.
     */
    public void addSymbolWheel(String color) {
        if (!Symbol.isAvailableColor(color)) return;
        symbol = new Symbol(color);
        updateSymbolShape();
    }

    /** Adds a symbol object while preserving its specialized behavior. */
    public void addSymbolWheel(Symbol newSymbol) {
        if (newSymbol == null || !Symbol.isAvailableColor(newSymbol.getColor())) return;
        symbol = newSymbol;
        updateSymbolShape();
    }

    public Type getType() { return type; }
    public boolean isRebel() { return type == Type.REBEL; }

    /** Applies this wheel's behavior to one spin. */
    public void spin(String randomColor, String leftColor) {
        if (type == Type.NORMAL && hasSpecialSymbol()) {
            symbol.spinEffect();
            updateSymbolShape();
            return;
        }
        boolean keptSymbol = false;
        if (type == Type.LEFTY && leftColor != null) {
            placeSymbolWheel(leftColor);
        } else if (type == Type.CRAZY) {
            int choice = new java.util.Random().nextInt(3);
            if (choice == 0) addSymbolWheel(randomColor);
            else if (choice == 1 && leftColor != null) placeSymbolWheel(leftColor);
            else keptSymbol = true;
        } else {
            addSymbolWheel(randomColor);
        }
        if (keptSymbol && hasSpecialSymbol()) {
            symbol.spinEffect();
            updateSymbolShape();
        }
    }

    private boolean hasSpecialSymbol() {
        return symbol != null && symbol.getClass() != Symbol.class;
    }

    /**
     * Deletes a symbol in a wheel.
     * @param color The symbol that is going to be deleted.
     */
    public void delSymbolWheel(String color) {
        if (symbol != null && symbol.hasColor(color)) {
            symbol = null;
            removeSymbolShape();
        }
    }

    /**
     * Place a Symbol in a Wheel
     * @param color The symbol that is going to be used.
     */
    public void placeSymbolWheel(String color) {
        addSymbolWheel(color);
    }

    private void updateSymbolShape() {
        removeSymbolShape();
        if (symbol == null) {
            return;
        }
        symbolShape = symbol.symbolShape;
        positionSymbolShape();
        if (isVisible && !(symbol instanceof ShySymbol && ((ShySymbol) symbol).isHidden())) {
            symbolShape.makeVisible();
        } else {
            symbolShape.makeInvisible();
        }
    }

    /**
     * Positions the symbol inside the 70x70 window.
     *
     * Rectangle and Circle use x/y as their upper-left corner. Triangle
     * uses x as the horizontal center of its upper vertex, so it needs a
     * different horizontal coordinate.
     */
    private void positionSymbolShape() {
        int symbolX = xPosition + 10;
        int symbolY = yPosition + 75;
        if (symbolShape instanceof Triangle) {
            symbolX = xPosition + 35;
        }
        symbolShape.setPosition(symbolX, symbolY);
    }

    private void removeSymbolShape() {
        if (symbolShape != null) {
            symbolShape.makeInvisible();
            symbolShape = null;
        }
    }

    /**
     * Returns the symbol currently displayed by this wheel.
     *
     * @return the current symbol, or null when the wheel has no symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Returns this wheel's symbol color.
     *
     * @return the current color, or null when the wheel has no symbol
     */
    public String symbolColor() {
        return symbol == null ? null : symbol.getColor();
    }

    /**
     * Fixes this wheel so a complete spin does not change its symbol.
     */
    public void lock() {
        if (!isRebel()) locked = true;
    }

    /**
     * Releases this wheel so it can change during a complete spin.
     */
    public void unlock() {
        if (!isRebel()) locked = false;
    }

    /**
     * Indicates whether this wheel is fixed.
     *
     * @return {@code true} when this wheel is locked
     */
    public boolean isLocked() {
        return locked;
    }

}
