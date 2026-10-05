/**
 * Represents a "shy" symbol in the slot machine.
 * A shy symbol alternates its visibility state (visible to invisible and vice versa)
 * each time it is selected (moved to the window position) after a spin.
 *
 * @author Samuel Infante Camargo, Juan Pablo Cuervo Contreras
 * @version Ciclo 4
 */
public class ShySymbol extends Symbol {
    private boolean isHidden;

    /**
     * Creates a ShySymbol with a specefied color.
     * Uses the constructor of the parent Class Symbol
     * @param color color used to display the symbol
     */
    public ShySymbol(String color) {
        super(color);
        this.isHidden = false;
    }

    /**
     * Alternates the visibility state of this symbol.
     */
    @Override
    public void spinEffect() {
        isHidden = !isHidden;
    }

    public boolean getIsShy() {
        return isHidden;
    }

    public boolean isHidden() { return isHidden; }
}
