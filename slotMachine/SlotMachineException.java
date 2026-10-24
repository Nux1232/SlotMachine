
/**
 * This class manages the SlotMachine exceptions.
 * 
 * @author Juan Pablo Cuervo, Samuel Infante Camargo
 * @version Cierre
 */
public class SlotMachineException extends Exception {
    public static final String WHEEL_NOT_FOUND = "Error: The Wheel does not exist or hasn't been found.";
    public static final String SYMBOL_NOT_ALLOWED = "Error: The Symbol is not allowed";
    public static final String SYMBOL_TYPE_NOTFOUND = "Error: The Symbol type is not found or can not be null";
    public SlotMachineException(String message) {
        super(message);
    }
}