import java.awt.geom.*;

/** A movable, resizable circle rendered on the shared canvas. */
public class Circle extends Shapes{

    /** Approximation of pi used by the original shape example. */
    public static final double PI=3.1416;
    
    private int diameter;

    /** Creates a circle at its default position and size. */
    public Circle(){
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }

    /** Draws this circle on the shared canvas when it is visible. */
    @Override
    protected void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    /**
     * Changes the diameter of the circle.
     *
     * @param newDiameter new diameter in pixels
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

}
