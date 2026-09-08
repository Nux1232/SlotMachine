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
    // Esto de acá debe ser una arrayList
    private int numWheels;
    //Winner
    private boolean winner;

    /**
     * Constructs an empty slot machine and initializes its visual elements.
     */
    public SlotMachine() {
        lastOperationOk = true;
        wheels = new Wheel[9];
        numWheels = 0;
        bodyConstructor();
        leversConstructor();
        // mover
        leverVertical.moveHorizontal(1248);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(1277);
    }

    /**
     * Validates if a position may be okay or not (Needs to be implemented)
     */
    private void validatePosition(int pos) {

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
        leverHorizontal.moveHorizontal(1206); // mover
        leverHorizontal.moveVertical(400);
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
            return;
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
        if (numWheels > 0) {
            locationWheel();
        }
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
            return;
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
            return;
        }
        lastOperationOk = true;
        for (int i = 0; i < numWheels; i++) {
            if (wheels[i] != null) {
                if (wheels[i].getSymbol() != null
                        && wheels[i].getSymbol().hasColor(symbol)){
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
            return;
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
        } 
    }

    /**
     * Exchanges two wheels and their symbols.
     *
     * @param first one-based position of the first wheel
     * @param second one-based position of the second wheel
     */
    public void swap(int first, int second) {
        if (!validWheelPositions(first, second)) {
            return;
        }
        int firstIndex = first - 1;
        int secondIndex = second - 1;
        if (wheels[firstIndex].isLocked() || wheels[secondIndex].isLocked()) {
            operationError("No se pueden intercambiar ruedas fijadas.");
            return;
        }
        Wheel temporaryWheel = wheels[firstIndex];
        wheels[firstIndex] = wheels[secondIndex];
        wheels[secondIndex] = temporaryWheel;
        locationWheel();
        lastOperationOk = true;
    }

    /**
     * Fixes a wheel so a complete spin leaves it unchanged.
     *
     * @param pos one-based position of the wheel
     */
    public void lock(int pos) {
        if (!validWheelPosition(pos)) {
            return;
        }
        wheels[pos - 1].lock();
        lastOperationOk = true;
    }

    /**
     * Releases a previously fixed wheel.
     *
     * @param pos one-based position of the wheel
     */
    public void unlock(int pos) {
        if (!validWheelPosition(pos)) {
            return;
        }
        wheels[pos - 1].unlock();
        lastOperationOk = true;
    }
    /**
     * Spins the requested wheel and assigns it a randomly selected color.
     *
     * @param wheel one-based position of the wheel to spin
     */
    public void spin(int wheel) {
        if (numWheels == 0 || wheels[0] == null) {
            lastOperationOk = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca sin ruedas.");
            }
            return;
        } else {
            if (wheel < 1 || wheel > numWheels) {
                operationError("No puedes girar la palanca en un espacio sin ruedas.");
                return;
            }
        }
        if (wheels[wheel - 1].isLocked()) {
            operationError("No puedes girar una rueda fijada.");
            return;
        }
        lastOperationOk = true;
        Random random = new Random();
        addSymbol(wheel, Symbol.random(random).getColor());
        JOptionPane.showMessageDialog(null, "Se ha girado la palanca!");
    }

    /**
     * Spins the slot machine.
     */
    public void spin() {
        winner = false;
        if (numWheels == 0 || wheels[0] == null) {
            lastOperationOk = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca sin ruedas.");
            }
            return;
        }
        lastOperationOk = true;
        Random random = new Random();
        double probability = random.nextDouble();
        // This part of the spin method is to make sure there is a probability to win.
        String symbolWinner = Symbol.random(random).getColor();
        // Determines the probability to win.
        if (probability < 0.4) {
            for (int i = 0; i < numWheels; i++) {
                if (!wheels[i].isLocked()) {
                    addSymbol(i + 1, symbolWinner);
                    winner = true;
                }
            }
        } else {
            // Determines the probability to lose.
            for (int i = 0; i < numWheels; i++){
                if (!wheels[i].isLocked()) {
                    addSymbol(i + 1, Symbol.random(random).getColor());
                }
            }
        }
        // Check directly if the user wins
        isjackpot();
        // I had to call MakeVisible, for some reason it explodes after the jackpot.
        makeVisible();
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
        String[] inventario = new String[numWheels];
        for (int i = 0; i < numWheels; i++){
            inventario[i] = wheels[i].symbolColor();
        }
        return inventario;
    }

    /**
     * Returns the number of distinct symbols in the machine.
     *
     * @return the number of distinct symbols
     */
    public int distinctSymbols() {
    if (numWheels == 0) {
        lastOperationOk = false;
        return 0;
    }
    lastOperationOk = true;
    String[] visibles = configuration();
    java.util.ArrayList<String> UniqueColors = new java.util.ArrayList<>();
    for (int i = 0; i < visibles.length; i++) {
        String colorActual = visibles[i];
        if (colorActual != null && !UniqueColors.contains(colorActual)) {
            UniqueColors.add(colorActual);
        }
    }
    return UniqueColors.size();
    }

    /**
     * Returns the current configuration of the slot machine.
     *
     * @return the machine configuration, or {@code null} if it is not
     *         available
     */
    public String[] configuration() {
    if (numWheels == 0) {
        lastOperationOk = false;
        return new String[0];
    }
    lastOperationOk = true;
    String[] visibles = new String[numWheels];
    for (int i = 0; i < numWheels; i++) {
        if (wheels[i].symbolColor() != null) {
            visibles[i] = wheels[i].symbolColor();
        } else {
            visibles[i] = "white"; // Default Color
        }
    }
    return visibles;
    }

    /**
     * Determines whether all wheels have the same symbol.
     *
     * @return {@code true} when the machine is a jackpot; otherwise
     *         {@code false}
     */
    public boolean isjackpot() {
        if (winner) {
            JOptionPane.showMessageDialog(null, "GANASTE! FELICITACIONEES!!");
            winner = true;
            body.changeColor("green");
            return true;
        } else {
            body.changeColor("blue");
            winner = false;
            return false;
        }
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

    /**
     * Checks two one-based wheel positions.
     */
    private boolean validWheelPositions(int first, int second) {
        if (!validWheelPosition(first) || !validWheelPosition(second)) {
            return false;
        }
        if (first == second) {
            operationError("No se puede intercambiar una rueda consigo misma.");
            return false;
        }
        return true;
    }

    /**
     * Checks a one-based wheel position without silently adjusting it.
     */
    private boolean validWheelPosition(int pos) {
        if (pos < 1 || pos > numWheels) {
            operationError("La posición de la rueda no es válida.");
            return false;
        }
        return true;
    }

    /**
     * Records an invalid operation and shows its message when the machine is visible.
     */
    private void operationError(String message) {
        lastOperationOk = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: " + message);
        }
    }
}