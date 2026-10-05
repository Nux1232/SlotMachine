import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * This class creates an extension of the Wheels created before.
 *
 * @author Samuel Infante Camargo
 * @author Juan Pablo Cuervo Contreras
 * @version Ciclo 4
 */

public class Wheel {
    private Rectangle Wheel;
    private Rectangle window;
    private int xPosition;
    private int yPosition;
    private boolean isVisible;
    private boolean locked;
    private ArrayList<Symbol> symbols;
    private Random random;
    /**
     * This is the constructor of the Wheel Class.
     */
    public Wheel() {
        isVisible = false;
        locked = false;
        symbols = new ArrayList<Symbol>();
        random = new Random();
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
        if (!symbols.isEmpty()) {
            symbols.get(0).setPosition(x+5, y + 65);
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
            if (!symbols.isEmpty()) {
                symbols.get(0).makeVisible();
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
            if (!symbols.isEmpty()) {
                symbols.get(0).makeInvisible();
            }
        }
    }

    /**
     * Add a symbol in a wheel.
     * @param color The symbol that is going to be used.
     */
    public void addSymbolWheel(Symbol newSymbol) {
        symbols.add(newSymbol);
        if (symbols.size() == 1) {
            newSymbol.setPosition(xPosition, yPosition + 65);
            if (isVisible) {
                newSymbol.makeVisible();
            }
        }
    }

    /**
     * Deletes a symbol in a wheel.
     * @param color The symbol that is going to be deleted.
     */
    public void delSymbolWheel(String color) {
        for (int i=0; i < symbols.size(); i++) {
            if (symbols.get(i).hasColor(color)) {
                if (isVisible) {
                    symbols.get(i).makeInvisible();
                }
                symbols.remove(i);
                break;
            }
        }
        if (!symbols.isEmpty() && isVisible) {
            symbols.get(0).setPosition(xPosition, yPosition + 65);
            symbols.get(0).makeVisible();
        }
    }

    /**
     * Spins the wheel. A random color is picked; if the wheel does not
     * have it yet, a symbol of that color is added. Then the wheel rotates
     * one position. Once every available color is present, it only rotates.
     */
    public void spin() {
        if (locked) return;
    
        List<String> colors = Symbol.getAvailableColors();
        String color = colors.get(random.nextInt(colors.size()));
        if (!containsColor(color)) {
            addSymbolWheel(new Symbol(color));
        }
        rotateOnce();
    }
    
    private boolean containsColor(String color) {
        for (Symbol s : symbols) {
            if (s.hasColor(color)) {
                return true;
            }
        }
        return false;
    }
    
    private void rotateOnce() {
        if (symbols.size() <= 1) return;
    
        if (isVisible) {
            symbols.get(0).makeInvisible();
        }
        Symbol last = symbols.remove(symbols.size() - 1);
        symbols.add(0, last);
        symbols.get(0).spinEffect();
        symbols.get(0).setPosition(xPosition, yPosition + 65);
        if (isVisible) {
            symbols.get(0).makeVisible();
        }
    }
    
    /**
     * Place a Symbol in a Wheel
     * @param color The symbol that is going to be used.
     */
    public void placeSymbolWheel(String color) {
        if (locked) return;
        boolean hasColor = false;
        for(Symbol sym: symbols) {
            if (sym.hasColor(color)) {
                hasColor = true;
                break;
            }
        }
        if (hasColor) {
            while (!symbols.get(0).hasColor(color)) {
                rotateOnce();
            }
        }
    }

    /**
     * Returns the symbol currently displayed by this wheel.
     *
     * @return the current symbol, or null when the wheel has no symbol
     */
    public Symbol getSymbol() {
        if (symbols.isEmpty()) {
            return null;
        } else {
            return symbols.get(0);
        }
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