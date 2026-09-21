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
    private static final int STEP_DELAY_MILLISECONDS = 100;

    // ok() method+ and Visible Operation.
    private boolean isVisible;
    private boolean lastOperationOk;
    //Constructor
    private Rectangle body; 
    private Circle leverCircle;
    private Rectangle leverHorizontal;
    //Wheels
    private Rectangle leverVertical;
    private ArrayList<Wheel> wheels;
    //Winner
    private boolean winner;

    /**
     * Constructs an empty slot machine and initializes its visual elements.
     */
    public SlotMachine() {
        lastOperationOk = true;
        // wheels
        wheels = new ArrayList<>();
        bodyConstructor();
        leversConstructor();
        // mover
        leverVertical.moveHorizontal(1248);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(1277);
    }

    /**
     * Creates a new slotMachine with n wheels and n symbols, randomly started.
     * @param n wheels and symbols
     */
    public SlotMachine(int n) {
        lastOperationOk = true;
        wheels = new ArrayList<>();
        bodyConstructor();
        leversConstructor();
        leverVertical.moveHorizontal(1248);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(1277);

        if (n > 9) {
            n = 9;
        }
        for (int i = 0; i < n; i++) {
            addWheel(i);
        }
        java.util.List<String> palette = Symbol.getAvailableColors();
        String[] baseSymbols = new String[n];
        for (int i = 0; i < n; i++) {
            // Usamos el % para repetir colores si n es mayor que la paleta actual
            baseSymbols[i] = palette.get(i % palette.size());
        }

        for (int i = 0; i < n; i++) {
            for (String color : baseSymbols) {
                wheels.get(i).addSymbolWheel(color);
            }
        }

        Random random = new Random();
        for  (int i = 0; i < n; i++) {
            int randomSteps = random.nextInt(n);
            spin(i + 1, randomSteps);
        }
        // With one wheel, one distinct symbol is already a winning state.
        while (n > 1 && distinctSymbols() == 1) {
            spin(1,1);
        }
        makeInvisible();

    }

    /**
     * Validates if a position may be okay or not
     */
    private int validatePosition(int pos, int maxLimit) {
        if (pos <= 0) {
            return 1;
        } else if (pos > maxLimit) {
            return maxLimit;
        }
        return pos;

    }

    /**
     * Creates and configures the body of the slot machine.
     */
    private void bodyConstructor() {
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
    private void leversConstructor() {
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
        lastOperationOk = true;
        if (wheels.size() >= 9) {
            lastOperationOk = false;
            if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: No se puede tener más de 9 ruedas"); 
            }
            return;
        }
        // The max limit allowed to add is the actual size + 1
        pos = validatePosition(pos, wheels.size() + 1);
        int index= pos - 1;
        
        // We implemented the ArrayList, so at the moment of adding a new
        // Wheel, it justs add one by one
        wheels.add(index, new Wheel());
        locationWheel();
    }

    /**
     * Repositions all wheels in their current order.
     */
    private void locationWheel() {
        lastOperationOk = true;
        int startX = 120;
        int startY = 210;
        int space = 130;
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            JOptionPane.showMessageDialog(null, "Error: Se intentó cambiar la ubicación de las ruedas sin tener ruedas"); 
            return;
        }
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPositionWheel(startX + (i * space), startY);
            if (this.isVisible) {
                wheels.get(i).makeWheelVisible();
            }
        }
    }

    /**
     * Deletes the wheel at the requested position and shifts the remaining
     * wheels to preserve their order.
     *
     * @param pos one-based position of the wheel to delete
     */
    public void delWheel(int pos) {
        lastOperationOk = true;
        if (wheels.isEmpty()) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
            return;
        }
        pos = validatePosition(pos, wheels.size());
        int index = pos - 1; 
        wheels.get(index).makeWheelInvisible();
        // The arraylist move all the elements to the left
        wheels.remove(index);
        if (!wheels.isEmpty()) {
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
        lastOperationOk = true;
        if (wheels.isEmpty()) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
            return;
        }
        pos = validatePosition(pos, wheels.size());
        int index = pos - 1;
        wheels.get(index).addSymbolWheel(color);
    }

    /**
     * Removes the specified symbol from the machine and clears matching
     * wheel windows.
     *
     * @param symbol symbol color to remove
     */
    public void delSymbol(String symbol) {
        lastOperationOk = true;
        if(wheels.isEmpty()){
            lastOperationOk = false;
            return;
        }

        for (Wheel wheel: wheels) {
                if (wheel.getSymbol() != null && wheel.getSymbol().hasColor(symbol)){
                    wheel.delSymbolWheel(symbol);
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
        lastOperationOk = true;
        if (wheels.size() == 0) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
            return;
        }
        wheel = validatePosition(wheel, wheels.size());
        int index = wheel - 1;
        lastOperationOk = true;
        if (wheels.get(index) != null) {
            wheels.get(index).placeSymbolWheel(symbol);
        } 
    }

    /**
     * Exchanges two wheels and their symbols.
     *
     * @param first one-based position of the first wheel
     * @param second one-based position of the second wheel
     */
    public void swap(int first, int second) {
        lastOperationOk = true;
        if (!validWheelPositions(first, second)) {
            return;
        }
        first = validatePosition(first, wheels.size());
        second = validatePosition(second, wheels.size());

        if (first == second) {
            lastOperationOk = false;
            operationError("No se puede intercambiar una rueda consigo misma.");
            return;
        }

        int firstIndex = first - 1;
        int secondIndex = second - 1;
        if (wheels.get(firstIndex).isLocked() || wheels.get(secondIndex).isLocked()) {
            lastOperationOk = false;
            operationError("No se pueden intercambiar ruedas fijadas.");
            return;
        }
        Wheel temporaryWheel = wheels.get(firstIndex);
        wheels.set(firstIndex, wheels.get(secondIndex));
        wheels.set(secondIndex, temporaryWheel);
        locationWheel();
    }

    /**
     * Fixes a wheel so a complete spin leaves it unchanged.
     *
     * @param pos one-based position of the wheel
     */
    public void lock(int pos) {
        lastOperationOk = true;
        if (!validWheelPosition(pos)) {
            return;
        }
        wheels.get(pos - 1).lock();
    }

    /**
     * Releases a previously fixed wheel.
     *
     * @param pos one-based position of the wheel
     */
    public void unlock(int pos) {
        lastOperationOk = true;
        if (!validWheelPosition(pos)) {
            return;
        }
        wheels.get(pos - 1).unlock();
    }
    /**
     * Spins the requested wheel and assigns it a randomly selected color.
     *
     * @param wheel one-based position of the wheel to spin
     */
    public void spin(int wheel) {
        lastOperationOk = true;
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca sin ruedas.");
            }
            return;
        } else if (wheel < 1 || wheel > wheels.size()) {
                operationError("No puedes girar la palanca en un espacio sin ruedas.");
                lastOperationOk = false;
                return;
            }
        
        if (wheels.get(wheel - 1).isLocked()) {
            lastOperationOk = false;
            operationError("No puedes girar una rueda fijada.");
            return;
        }
        Random random = new Random();
        addSymbol(wheel, Symbol.random(random).getColor());
        JOptionPane.showMessageDialog(null, "Se ha girado la palanca!");
    }

    /**
     * Spins the slot machine.
     */
    public void spin() {
        winner = false;
        lastOperationOk = true;
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca sin ruedas.");
            }
            return;
        }
        Random random = new Random();
        double probability = random.nextDouble();
        // This part of the spin method is to make sure there is a probability to win.
        String symbolWinner = Symbol.random(random).getColor();
        // Determines the probability to win.
        if (probability < 0.2) {
            for (int i = 0; i < wheels.size(); i++) {
                if (!wheels.get(i).isLocked()) {
                    addSymbol(i + 1, symbolWinner);
                    winner = true;
                }
            }
        } else {
            // Determines the probability to lose.
            for (int i = 0; i < wheels.size(); i++){
                if (!wheels.get(i).isLocked()) {
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
     * Rotates one wheel the requested number of steps. Each step selects a
     * symbol from the palette supported by the current version. When the
     * machine is visible, a short pause lets the user see every intermediate
     * symbol.
     *
     * @param wheel one-based position of the wheel to rotate
     * @param steps number of steps; it must be zero or greater
     */
    public void spin(int wheel, int steps) {
        lastOperationOk = true;
        if (!hasWheels()) {
            operationError("No puedes girar una rueda sin ruedas.");
            return;
        }
        if (!validWheelPosition(wheel)) {
            return;
        }
        if (steps < 0) {
            operationError("El número de pasos no puede ser negativo.");
            return;
        }
        if (wheels.get(wheel - 1).isLocked()) {
            operationError("No puedes girar una rueda fijada.");
            return;
        }

        Random random = new Random();
        for (int step = 0; step < steps; step++) {
            wheels.get(wheel - 1).addSymbolWheel(Symbol.random(random).getColor());
            if (!pauseBetweenSteps()) {
                return;
            }
        }
    }

    /**
     * Leaves the machine in the configuration described by a string.
     * Colors may be separated by commas, semicolons, or whitespace. The
     * configuration must contain exactly one supported color per wheel.
     * A locked wheel may only keep its current color.
     *
     * @param setSymbols desired colors in wheel order
     */
    public void spin(String setSymbols) {
        lastOperationOk = true;
        if (!hasWheels()) {
            lastOperationOk = false;
            operationError("No puedes establecer una configuración sin ruedas.");
            return;
        }
        String[] requestedSymbols = parseConfiguration(setSymbols);
        if (requestedSymbols == null) {
            return;
        }

        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).isLocked() && !requestedSymbols[i].equals(wheels.get(i).symbolColor())) {
                operationError("La configuración cambia una rueda fijada.");
                return;
            }
        }
        for (int i = 0; i < wheels.size(); i++) {
            if (!wheels.get(i).isLocked()) {
                wheels.get(i).placeSymbolWheel(requestedSymbols[i]);
            }
        }
        winner = allSymbolsEqual(requestedSymbols);
        isjackpot();
    }

    /**
     * Checks whether a requested configuration contains the same symbol on
     * every wheel.
     */
    private boolean allSymbolsEqual(String[] requestedSymbols) {
        for (int i = 1; i < requestedSymbols.length; i++) {
            if (!requestedSymbols[0].equals(requestedSymbols[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses and validates the textual representation of a configuration.
     */
    private String[] parseConfiguration(String setSymbols) {
        if (setSymbols == null) {
            operationError("La configuración no puede ser nula.");
            return null;
        }
        String configurationText = setSymbols.trim();
        if (configurationText.startsWith("[") && configurationText.endsWith("]")) {
            configurationText = configurationText.substring(1, configurationText.length() - 1).trim();
        }
        if (configurationText.isEmpty()) {
            operationError("La configuración no puede estar vacía.");
            return null;
        }

        String[] requestedSymbols = configurationText.split("[,;\\s]+");
        if (requestedSymbols.length != wheels.size()) {
            operationError("La configuración debe tener un símbolo por rueda.");
            return null;
        }
        for (String color : requestedSymbols) {
            if (!Symbol.isAvailableColor(color)) {
                operationError("El símbolo '" + color
                        + "' no está permitido en la versión actual.");
                return null;
            }
        }
        return requestedSymbols;
    }

    /**
     * Waits between visible steps without hiding interruption from callers.
     */
    private boolean pauseBetweenSteps() {
        if (!isVisible) {
            return true;
        }
        try {
            Thread.sleep(STEP_DELAY_MILLISECONDS);
            return true;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            operationError("La animación del giro fue interrumpida.");
            return false;
        }
    }

    /**
     * Indicates whether the machine contains at least one wheel.
     */
    private boolean hasWheels() {
        return !wheels.isEmpty();
    }
    /**
     * Returns the symbols currently assigned to the machine's wheels.
     *
     * @return an array containing the current symbols, or an empty array when
     *         the machine has no wheels
     */
    public String[] symbols() {
        lastOperationOk = true;
        if (wheels.isEmpty()) {
           lastOperationOk = false; 
            return new String[0];
        }

        String[] inventario = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++){
            inventario[i] = wheels.get(i).symbolColor();
        }
        return inventario;
    }

    /**
     * Returns the number of distinct symbols in the machine.
     *
     * @return the number of distinct symbols
     */
    public int distinctSymbols() {
        lastOperationOk = true;
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            return 0;
        }

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
        lastOperationOk = true;
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            return new String[0];
        }

        String[] visibles = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).symbolColor() != null) {
                visibles[i] = wheels.get(i).symbolColor();
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
            if (isVisible) {
                makeVisible();
            }
            return true;
        } else {
            body.changeColor("blue");
            winner = false;
            if (isVisible){
                makeVisible();
            }
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
            for (Wheel wheel : wheels) {
                wheel.makeWheelVisible();
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
            for (Wheel wheel : wheels) {
                wheel.makeWheelInvisible();
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
        if (pos < 1 || pos > wheels.size()) {
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