

public class SlotMachine {
    private Rectangle body; 
    private Circle leverCircle;
    private Rectangle leverHorizontal;
    private Rectangle leverVertical;

    /**
     * Constructor of the SlotMachine.
     */
    public SlotMachine() {
        bodyConstructor();
        leversConstructor();
        leverHorizontal.moveHorizontal(628);
        leverHorizontal.moveVertical( 400);
        leverVertical.moveHorizontal(670);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(700);
    }

    /**
     * This makes the body of the slotmachine.
     */
    public void bodyConstructor() {
        body = new Rectangle();
        body.changeSize(530,620);
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

    public void addWheel(int pos) {
    }

    public void delWheel(int pos) {
    }

    public void addSymbol(int pos, String color) {
    }

    public void delSymbol(String symbol) {
    }

    public void placeSymbol(int wheel, String symbol) {
    }

    public void spin(int wheel) {
    }

    public void spin() {
    }

    public String[] symbols() {
        return null;
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
        body.makeVisible();
        leverCircle.makeVisible();
        leverHorizontal.makeVisible();
        leverVertical.makeVisible();
    }

    public void makeInvisible() {
    }

    public void exit() {
    }

    public boolean ok() {
        return false;
    }
}