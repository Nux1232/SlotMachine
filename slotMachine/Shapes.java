/**
 * Base class for graphical shapes that can be positioned, colored, and shown.
 *
 * <p>Subclasses provide the concrete drawing implementation.</p>
 */
public abstract class Shapes {
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Makes this shape visible.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }

    /**
     * Draws this shape using its current state.
     *
     * <p>Concrete subclasses must implement this method.</p>
     */
    protected abstract void draw();

    /**
     * Makes this shape invisible.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    /**
     * Moves this shape 20 pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Moves this shape 20 pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Moves this shape 20 pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Moves this shape 20 pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Moves this shape horizontally.
     *
     * @param distance horizontal distance in pixels; negative values move left
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Moves this shape vertically.
     *
     * @param distance vertical distance in pixels; negative values move up
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Moves this shape horizontally one pixel at a time.
     *
     * @param distance horizontal distance in pixels; negative values move left
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
            moveHorizontal(delta);
        }
    }

    /**
     * Moves this shape vertically one pixel at a time.
     *
     * @param distance vertical distance in pixels; negative values move up
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
            moveVertical(delta);
        }
    }

    /**
     * Changes the color of this shape.
     *
     * @param newColor new color name supported by the canvas
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    /** Removes this shape's current drawing from the canvas. */
    protected void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Sets this shape's absolute position.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void setPosition(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }
}
