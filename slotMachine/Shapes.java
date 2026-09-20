/**
 * Esta clase contiene los atributos y comportamientos comunes de las figuras.
 *
 * La clase base conserva la posicion, el color y la visibilidad. Las
 * subclases solo deben definir como se dibuja su forma concreta.
 */
public abstract class Shapes {
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }

    /**
     * Dibuja la forma concreta. Debe ser implementado por cada subclase.
     *
     * Antes estaba declarado como private y vacio. Por eso los movimientos
     * heredados no redibujaban Rectangle ni Triangle.
     */
    protected abstract void draw();

    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    /**
     * Move the rectangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the rectangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the rectangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the color.
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    /*
     * Erase the rectangle on screen.
     */
    protected void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Change the position of the Rectangle
     * @param x The distance where the Rectangle will be (Horizontal).
     * @param y The distance where the Rectangle will be (Vertical).
     */
    public void setPosition(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }
}
