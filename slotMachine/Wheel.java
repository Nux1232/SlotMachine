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
    private boolean isVisible;
    private boolean locked;

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
        Wheel.setPosition(x, y);
        window.setPosition(x, y);
        window.moveVertical(65);
    }

    /**
    * Makes Visible the wheel and the window.
    */
    public void makeWheelVisible() {
        isVisible = true;
        if (isVisible) {
            Wheel.makeVisible();
            window.makeVisible();
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
        }
    }

    /**
    * Add a symbol in a wheel.
    * @param color The symbol that is going to be used.
    */
    public void addSymbolWheel(String color) {
        window.changeColor(color);
    }

    /**
    * Deletes a symbol in a wheel.
    * @param color The symbol that is going to be deleted.
    */
    public void delSymbolWheel(String color) {
        window.changeColor("white");
    }

    /**
    * Place a Symbol in a Wheel
    * @param color The symbol that is going to be used.
    */
    public void placeSymbolWheel(String color) {
        window.changeColor(color);
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