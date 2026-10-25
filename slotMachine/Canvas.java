import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

/**
 * Shared drawing surface used by the slot machine's graphical shapes.
 *
 * <p>Shapes are retained by object identity so the canvas can redraw them in
 * their current stacking order.</p>
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Returns the shared canvas, creating it the first time it is requested.
     *
     * @return the single canvas instance
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            Dimension screen = screenSize();
            int width = (int) (screen.width * 0.90);
            int height = (int) (screen.height * 0.80);
            canvasSingleton = new Canvas("SlotMachine", width, height,
                                         Color.white);
        }
        canvasSingleton.setVisible(!GraphicsEnvironment.isHeadless());
        return canvasSingleton;
    }

    /**
     * Returns the screen dimensions, or standard dimensions in headless mode.
     *
     * @return usable screen dimensions
     */
    public static Dimension screenSize() {
        if (GraphicsEnvironment.isHeadless()) {
            return new Dimension(1500, 800);
        }
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(screen.width, screen.height);
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Creates a drawing surface with the requested frame and background.
     *
     * @param title title displayed in the frame
     * @param width canvas width in pixels
     * @param height canvas height in pixels
     * @param bgColour canvas background color
     */
    private Canvas(String title, int width, int height, Color bgColour){
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(width, height));
        if (!GraphicsEnvironment.isHeadless()) {
            frame = new JFrame();
            frame.setContentPane(canvas);
            frame.setTitle(title);
            frame.pack();
        }
        backgroundColour = bgColour;
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Sets frame visibility and initializes the off-screen drawing buffer
     * on first use.
     *
     * @param visible whether to show the canvas frame
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            if (size.width <= 0 || size.height <= 0) {
                size = canvas.getPreferredSize();
            }
            canvasImage = GraphicsEnvironment.isHeadless()
                    ? new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB)
                    : canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        if (frame != null) frame.setVisible(visible);
    }

    /**
     * Draws or updates a shape, placing it at the top of the drawing order.
     *
     * @param referenceObject identity used to update or erase this drawing
     * @param color color name used to render the shape
     * @param shape geometry to draw
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
 
    /**
     * Removes the drawing associated with an object identity.
     *
     * @param referenceObject identity of the drawing to remove
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Sets the graphics context color using a supported color name.
     *
     * @param colorString color name; unknown names use black
     */
    public void setForegroundColor(String colorString){
        if(colorString.equals("red"))
            graphic.setColor(Color.red);
        else if(colorString.equals("black"))
            graphic.setColor(Color.black);
        else if(colorString.equals("blue"))
            graphic.setColor(Color.blue);
        else if(colorString.equals("yellow"))
            graphic.setColor(Color.yellow);
        else if(colorString.equals("green"))
            graphic.setColor(Color.green);
        else if(colorString.equals("magenta"))
            graphic.setColor(Color.magenta);
        else if(colorString.equals("white"))
            graphic.setColor(Color.white);
        else if(colorString.equals("orange"))
            graphic.setColor(Color.orange);
        else if(colorString.equals("pink"))
            graphic.setColor(Color.pink);
        else if(colorString.equals("cyan"))
            graphic.setColor(Color.cyan);
        else if(colorString.equals("gray"))
            graphic.setColor(Color.gray);
        else if(colorString.equals("brown"))
            graphic.setColor(new Color(102,51,0));
        else
            graphic.setColor(Color.black);
    }

    /**
     * Pauses the current thread for use between animation frames.
     *
     * @param milliseconds pause duration in milliseconds
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraws all registered shapes in their current drawing order.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Clears the off-screen buffer without requesting a repaint.
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /** Panel that displays the canvas's off-screen image. */
    private class CanvasPane extends JPanel{
        /** Paints the current off-screen image onto the panel. */
        @Override
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /** Stores the geometry and color needed to render one canvas object. */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        /**
         * Creates a drawing description.
         *
         * @param shape geometry to render
         * @param color color name for the geometry
         */
        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        /**
         * Draws the stored geometry with its configured color.
         *
         * @param graphic graphics context to draw into
         */
        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

}
