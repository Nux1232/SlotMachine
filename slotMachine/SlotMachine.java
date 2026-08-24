import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * SlotMachine class.
 * 
 * @author Samuel Infante Camargo, Juan Pablo Cuervo Contreras 
 * @version 1
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
    //Winner
    private boolean Winner;

    /**
     * Constructor of the SlotMachine.
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
     * This makes the body of the slotmachine.
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
     * This is part of the slotMachine body.
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
     * This method creates and valides the number of wheels.
     * @param pos The position to add the wheel.
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
     * Sets the location of the wheels on the screen.
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
     * This method deletes and valides the number of wheels after deleting.
     * Also, after deleting replace all the wheels into their order.
     * @param pos The position of the wheel to delete.
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
     * Adds a symbol to the machine at a specific position.
     * @param pos The position for the symbol.
     * @param color The color of the symbol.
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
     * Deletes a symbol from the machine.
     * @param symbol The symbol to delete.
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
     * Places a specific symbol on a wheel.
     * @param wheel The wheel index.
     * @param symbol The symbol to place.
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
     * Spins a specific wheel.
     * @param wheel The wheel to spin.
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
     * Spins all wheels of the slot machine.
     */
    public void spin() {
        Winner = false;
        if (numWheels == 0 || wheels[0] == null) {
            lastOperationOk = false; 
            JOptionPane.showMessageDialog(null, "Error: No puedes girar la palanca sin ruedas.");
        }
        lastOperationOk = true; 
        symbolsToSpin = new ArrayList<>(List.of("red", "green", "pink", "black", "yellow", "orange", "magenta", "cyan"));        
        Random random = new Random();
        int symbolsToSpinSize = symbolsToSpin.size();
        double probability = random.nextDouble();
        // This part of the spin method is to make sure there is a probability to win.
        int randomSymbolWinner = random.nextInt(symbolsToSpinSize);
        String symbolWinner = symbolsToSpin.get(randomSymbolWinner);
        // Determines the probability to win.
        if (probability < 0.4) {
            for (int i = 0; i < numWheels; i++) {
                addSymbol(i + 1, symbolWinner);
                Winner = true;
            }
        } else {
            // Determines the probability to lose.
            for (int i = 0; i < numWheels; i++){
                int randomSymbol = random.nextInt(symbolsToSpinSize);
                addSymbol(i + 1, symbolsToSpin.get(randomSymbol));
            }
        }
        // Check directly if the user wins
        isjackpot();
        // I had to call MakeVisible, for some reason it explodes after the jackpot.
        makeVisible();
    }

    /**
     * Returns the array of symbols.
     * @return String[] representing the symbols.
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
     * Returns the count of distinct symbols.
     * @return int distinct symbols count.
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
     * Returns the current configuration of visible symbols.
     * @return String[] of visible symbols.
     */
    public String[] configuration() {
    if (numWheels == 0 || symbols.isEmpty()) {
        lastOperationOk = false;
        return new String[0];
    }
    lastOperationOk = true;
    String[] visibles = new String[numWheels];
    for (int i = 0; i < numWheels; i++) {
        // Just in case if there is a wheel with no color.
        if (i < symbols.size() && symbols.get(i) != null) {
            visibles[i] = symbols.get(i);
        } else {
            visibles[i] = "white"; // Default Color
        }
    }
    return visibles;
    }

    /**
     * Checks if the user won the jackpot.
     * @return boolean true if winner.
     */
    public boolean isjackpot() {
        if (Winner) {
            JOptionPane.showMessageDialog(null, "GANASTE! FELICITACIONEES!!");
            Winner = true;
            body.changeColor("green");
            return true;
        } else {
            body.changeColor("blue");
            Winner = false;
            return false;
        }
    }

    /**
     * Make visible the slotMachine.
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
     * Make invisible the slotMachine.
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
     * Exits the simulator.
     */
    public void exit() {
    }

    /**
     * Checks if the last operation was ok.
     * @return boolean status.
     */
    public boolean ok() {
        if (lastOperationOk) {
            return true;    
        }
        return false;
    }
}