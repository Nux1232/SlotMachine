import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SlotMachine {
    // ok() method+
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

    public void placeSymbol(int wheel, String symbol) {
        if (numWheels == 0) {
            lastOperationOk = false; 
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: No se puede borrar llantas, no hay llantas.");
            }
        }
        if (wheel <= 0) {
            wheel = 1;
        } else if (wheel < numWheels) {
            wheel = numWheels;
        }
        int index = wheel - 1;
        lastOperationOk = true;
        if (wheels[index] != null) {
            wheels[index].placeSymbolWheel(symbol);
            symbols.set(index, symbol);
        } 
    }

    public void spin(int wheel) {
    }

    public void spin() {
    }

   public String[] symbols() {
        if (numWheels == 0 || wheels[0] == null) {
           lastOperationOk = false; 
            return new String[0];
        }
        lastOperationOk = true; 
        String[] inventario = new String[symbols.size()];
        //for (int i = 0; i < new)
        //return symbols[];
    }

    public int distinctSymbols() {
        return 0;
    }

    public String[] configuration() {
        return null;
    }

    public boolean isjackpot() {
        return false;
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

    public void exit() {
    }

    public boolean ok() {
        if (lastOperationOk) {
            return true;    
        }
        return false;
    }
}