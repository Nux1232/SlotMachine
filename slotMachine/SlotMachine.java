

public class SlotMachine {
    private Rectangle body; 
    private Circle lever1;
    private Rectangle lever2;
    private Rectangle lever3;

    /**
     * Constructor of the SlotMachine.
     */
    public SlotMachine() {
        body = new Rectangle();
        body.changeSize(10,20);
        // Acá hay que buscar centrarlo por mientras tanto
        body.moveHorizontal(0);
        body.moveVertical(0);
        body.changeColor("blue");

        // Palanca
        lever1 = new Circle();
        lever1.changeColor("red");
        lever2 = new Rectangle();
        lever2.changeColor("black");
        lever3 = new Rectangle();
        lever3.changeColor("black");

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
        lever1.makeVisible();
        lever2.makeVisible();
        lever3.makeVisible();
    }

    public void makeInvisible() {
    }

    public void exit() {
    }

    public boolean ok() {
        return false;
    }
}