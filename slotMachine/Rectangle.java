
/** A movable, resizable rectangle rendered on the shared canvas. */
public class Rectangle extends Shapes{

    /** Number of edges in a rectangle. */
    public static int EDGES = 4;
    
    private int height;
    private int width;

    /** Creates a rectangle at its default position, size, and color. */
    public Rectangle(){
        height = 30;
        width = 40;
        super.xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }

    /**
     * Changes the height and width of this rectangle.
     *
     * @param newHeight new height in pixels
     * @param newWidth new width in pixels
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    


    /** Draws this rectangle on the shared canvas when it is visible. */
    @Override
    protected void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }


}
