import java.awt.Polygon;

/** A movable, resizable triangle rendered on the shared canvas. */
public class Triangle extends Shapes{
    
    /** Number of vertices in a triangle. */
    public static int VERTICES=3;
    
    private int height;
    private int width;

    /** Creates a triangle at its default position, size, and color. */
    public Triangle(){
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }

    /**
     * Changes the height and base width of this triangle.
     *
     * @param newHeight new height in pixels
     * @param newWidth new base width in pixels
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /** Draws this triangle on the shared canvas when it is visible. */
    @Override
    protected void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

}
