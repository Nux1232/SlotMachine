import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * This class represents a slot machine composed of a body, a lever and
 * up to nine wheels with colored symbols.
 *
 * @author Samuel Infante Camargo
 * @author Juan Pablo Cuervo Contreras
 * @version Ciclo 1
 */
public class SlotMachine {
    // ok() method+ and Visible Operation.
    private boolean isVisible;
    private boolean lastOperationOk;
    //Constructor
    private Rectangle body; 
    private Circle leverCircle;
    private Rectangle leverHorizontal;
    //Wheels
    private Rectangle leverVertical;
    private Wheel[] wheels;
    private int numWheels;
    //Symbols 
    private ArrayList<String> symbols;
    private ArrayList<String> symbolsToSpin;

    /**
     * Constructs an empty slot machine and initializes its visual elements.
     */
    public SlotMachine() {
        lastOperationOk = true;
        wheels = new Wheel[9];
        numWheels = 0;
        bodyConstructor();
        leversConstructor();
        leverHorizontal.moveHorizontal(1206);
        leverHorizontal.moveVertical(400);
        leverVertical.moveHorizontal(1248);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(1277);
        symbols = new ArrayList<>();
    }

    /**
     * Creates and configures the body of the slot machine.
     */
    public void bodyConstructor() {
        body = new Rectangle();
        body.changeSize(530,1200);
        // Acá hay que buscar centrarlo por mientras tanto
        body.moveHorizontal(7);
        body.moveVertical(60);
        body.changeColor("blue");
    }

    /**
     * Creates and configures the three visual elements that form the lever.
     */
    public void leversConstructor() {
        // This Rectangle makes the horizontal part of the lever
        leverHorizontal = new Rectangle();
        leverHorizontal.changeColor("black");
        leverHorizontal.changeSize(50, 92);
        // This Rectangle makes the vertical part of the lever
        leverVertical = new Rectangle();
        leverVertical.changeColor("black");
        leverVertical.changeSize(360, 50);
        // The circle of the lever:
        leverCircle = new Circle();
        leverCircle.changeColor("red");
        leverCircle.changeSize(90);
    }

    /**
     * Adds a wheel at the requested position. Positions outside the current
     * valid range are adjusted to the nearest valid position.
     *
     * @param pos one-based position where the wheel will be inserted
     */
    public void addWheel(int pos) {
        if (pos <= 0) {
            pos = 1;
        } else if (pos > numWheels + 1) {
            pos = numWheels + 1;
        }
        int index = pos - 1;
        if (numWheels >= 9) {
            lastOperationOk = false;
            if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: No se puede tener más de 9 ruedas"); 
            }
            return;
        }
        for(int i = numWheels; i > index; i--) {
            wheels[i] = wheels[i-1];
        }
        wheels[index] = new Wheel();
        numWheels++;
        locationWheel();
    }

    /**
     * Repositions all wheels in their current order.
     */
    public void locationWheel() {
        lastOperationOk = true;
        int startX = 120;
        int startY = 210;
        int space = 130;
        if (numWheels == 0) {
            lastOperationOk = false;
            JOptionPane.showMessageDialog(null, "Error: Se intentó cambiar la ubicación de las ruedas sin tener ruedas"); 
            return;
        }
        for (int i = 0; i < numWheels; i++) {
            wheels[i].setPositionWheel(startX + (i * space), startY);
            wheels[i].makeWheelVisible();
        }
    }
    /**
     * Deletes the wheel at the requested position and shifts the remaining
     * wheels to preserve their order.
     *
     * @param pos one-based position of the wheel to delete
     */
    public void delWheel(int pos) {
        if (numWheels == 0) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
        }
        lastOperationOk = true;
        if (pos <= 0) {
            pos = 1; 
        }else if (pos > numWheels) {
            pos = numWheels;
        }
        int index = pos - 1; 
        wheels[index].makeWheelInvisible();
        for (int i = index; i < numWheels - 1; i++) {
            wheels[i] = wheels[i + 1];
        }    
        numWheels--;
        locationWheel();
    }

    /**
     * Adds a colored symbol to the wheel at the requested position.
     *
     * @param pos one-based position of the wheel
     * @param color color used for the symbol
     */
    public void addSymbol(int pos, String color) {
        if (numWheels == 0) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
        }
        lastOperationOk = true;
        if (pos <= 0) {
            pos = 1; 
        }else if (pos > numWheels) {
            pos = numWheels;
        }
        int index = pos - 1; 
        if (wheels[index] != null) {
            wheels[index].addSymbolWheel(color);
            lastOperationOk = true;
        }else {
            lastOperationOk = false;
        }
        symbols.add(color);
    }

    /**
     * Removes the specified symbol from the machine and clears matching
     * wheel windows.
     *
     * @param symbol symbol color to remove
     */
    public void delSymbol(String symbol) {
        if(numWheels == 0) {
            lastOperationOk = false;
        }
        lastOperationOk = true;
        for (int i = 0; i < numWheels; i++) {
            if (wheels[i] != null) {
                if (symbols.contains(symbol)){
                    symbols.remove(symbol);
                    wheels[i].delSymbolWheel(symbol);
               }   
            } 
        }
    }

    /**
     * Places or replaces a symbol on the requested wheel.
     *
     * @param wheel one-based position of the wheel
     * @param symbol symbol color to place
     */
    public void placeSymbol(int wheel, String symbol) {
        if (numWheels == 0) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
        }
        if (wheel <= 0) {
            wheel = 1;
        } else if (wheel > numWheels) {
            wheel = numWheels;
        }
        int index = wheel - 1;
        lastOperationOk = true;
        if (wheels[index] != null) {
            wheels[index].placeSymbolWheel(symbol);
            symbols.set(index, symbol);
        } 
    }

    /**
     * Spins the requested wheel and assigns it a randomly selected color.
     *
     * @param wheel one-based position of the wheel to spin
     */
    public void spin(int wheel) {
        if (numWheels == 0 || wheels[0] == null) {
            lastOperationOk = false;
            JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca sin ruedas.");
        } else {
            if (numWheels < wheel) {
                JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca en un espacio sin ruedas.");
                return;
            }
        }
        lastOperationOk = true;
        ArrayList<String> symbolsToSpin = new ArrayList<>(List.of("red", "green", "pink", "black", "yellow", "orange", "magenta", "cyan"));
        Random random = new Random();
        int symbolsToSpinSize = symbolsToSpin.size();
        int randomSymbol = random.nextInt(symbolsToSpinSize);

        addSymbol(wheel, symbolsToSpin.get(randomSymbol));
        JOptionPane.showMessageDialog(null, "Se ha girado la palanca!");

    }

    /**
     * Spins the slot machine.
     */
    public void spin() {
    }

    /**
     * Returns the symbols currently assigned to the machine's wheels.
     *
     * @return an array containing the current symbols, or an empty array when
     *         the machine has no wheels
     */
    public String[] symbols() {
        if (numWheels == 0 || wheels[0] == null) {
           lastOperationOk = false; 
            return new String[0];
        }
        lastOperationOk = true; 
        String[] inventario = new String[symbols.size()];
        for (int i = 0; i < numWheels; i++){
            if (symbols.get(i) != null) {
                inventario[i] = symbols.get(i);
            }else {
                inventario[i] = null;
            }
        }
        return inventario;
    }

    /**
     * Returns the number of distinct symbols in the machine.
     *
     * @return the number of distinct symbols
     */
    public int distinctSymbols() {
        return 0;
    }

    /**
     * Returns the current configuration of the slot machine.
     *
     * @return the machine configuration, or {@code null} if it is not
     *         available
     */
    public String[] configuration() {
        return null;
    }

    /**
     * Determines whether all wheels have the same symbol.
     *
     * @return {@code true} when the machine is a jackpot; otherwise
     *         {@code false}
     */
    public boolean isjackpot() {
        return false;
    }

    /**
     * Makes the slot machine and all its wheels visible.
     */
    public void makeVisible() {
        isVisible = true;
        if (isVisible){
            body.makeVisible();
            leverCircle.makeVisible();
            leverHorizontal.makeVisible();
            leverVertical.makeVisible();
            for (int i = 0; i < numWheels; i++) {
                if (wheels[i] != null) {
                    wheels[i].makeWheelVisible();
            }
        }
        }
    }

    /**
     * Makes the slot machine and all its wheels invisible.
     */
    public void makeInvisible() {
        isVisible = false;
        if (isVisible == false) {
            body.makeInvisible();
            leverCircle.makeInvisible();
            leverHorizontal.makeInvisible();
            leverVertical.makeInvisible();
            for (int i = 0; i < numWheels; i++) {
                if (wheels[i] != null) {
                    wheels[i].makeWheelInvisible();
                }
        }
        }
    }

    /**
     * Hides the machine, if necessary, and exits the application.
     */
    public void exit() {
        lastOperationOk = true;
        if(isVisible){
            makeInvisible();
        }

        System.exit(0);
    }

    /**
     * Reports whether the last operation was completed successfully.
     *
     * @return {@code true} when the last operation succeeded; otherwise
     *         {@code false}
     */
    public boolean ok() {
        if (lastOperationOk) {
            return true;    
        }
        return false;
    }
}