/**
 * This class creates an extension of the Wheels created before.
 * 
 * @author Samuel Infante Camargo, Juan Pablo Cuervo Contreras 
 * @version 1
 */
import java.util.ArrayList;

public class Wheel {
    // Atributos de la rueda
    private Rectangle Wheel;
    private Rectangle window;
    private ArrayList<String> symbols; // Lista dinámica para los colores CSS
    private boolean isVisible;
    /**
    * This is the constructor of the Wheel Class.
    */
    public Wheel() {
        isVisible = false;
        // Inicializamos el fondo visual
        Wheel = new Rectangle();
        Wheel.changeSize(210, 70);
        Wheel.changeColor("white");
        window = new Rectangle();
        window.changeSize(70,70);
        window.changeColor("white");
        
        // Inicializamos la lista de símbolos vacía
        symbols = new ArrayList<String>();
    }

    public void setPositionWheel(int x, int y){
        Wheel.setPosition(x, y);
        window.setPosition(x, y);
        window.moveVertical(65);
    }

    public void makeWheelVisible() {
        isVisible = true;
        if (isVisible) {
            Wheel.makeVisible();
            window.makeVisible();
        }
    }

    public void makeWheelInvisible() {
        isVisible = false;
        if (isVisible == false) {
            Wheel.makeInvisible();
            window.makeInvisible();
        }
    }

    public void addSymbolWheel(String color) {
        window.changeColor(color);
    }

    public void delSymbolWheel(String color) {
        window.changeColor("white");
    }

    public void placeSymbolWheel(String color) {
        window.changeColor(color);
    }

}