/**
 * This class creates an extension of the Wheels created before.
 *
 * @author Samuel Infante Camargo
 * @author Juan Pablo Cuervo Contreras
 * @version Cliclo 1
 */

public class Wheel {
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
            if (symbolShape != null) {
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
        symbol = new Symbol(color);
        updateSymbolShape();
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
        symbol = new Symbol(color);
        updateSymbolShape();
    }

    private void updateSymbolShape() {
        removeSymbolShape();
        if (symbol == null) {
            return;
        }
        if (symbol.hasColor("red")) {
            symbolShape = new Triangle();
            ((Triangle) symbolShape).changeSize(50, 50);
        } else if (symbol.hasColor("black")) {
            symbolShape = new Rectangle();
            ((Rectangle) symbolShape).changeSize(50, 50);
        } else if (symbol.hasColor("green")) {
            symbolShape = new Circle();
            ((Circle) symbolShape).changeSize(50);
        }
        symbolShape.changeColor(symbol.getColor());
        positionSymbolShape();
        if (isVisible) {
            symbolShape.makeVisible();
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
        locked = true;
    }

    /**
     * Releases this wheel so it can change during a complete spin.
     */
    public void unlock() {
        locked = false;
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