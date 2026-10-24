
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
    public static final String WHEEL_LIMIT_REACHED = "Error: The machine can not have more than 9 wheels";
    public static final String WHEEL_TYPE_NOTFOUND = "Error: The Wheel type is not found or can not be null";
    public static final String WHEEL_REBEL = "Error: A rebel wheel can not be deleted, locked, unlocked or swapped";
    public static final String WHEEL_LOCKED = "Error: The Wheel is locked";
    public static final String WHEEL_SAME_POSITION = "Error: A wheel can not be swapped with itself";
    public static final String STEPS_NEGATIVE = "Error: The number of steps can not be negative";
    public static final String SPIN_INTERRUPTED = "Error: The spin was interrupted";
    public static final String CONFIGURATION_INVALID = "Error: The configuration is null, empty or does not have one symbol per wheel";
    public static final String CONFIGURATION_LOCKED = "Error: The configuration changes a locked wheel";

    public SlotMachineException(String message) {
        super(message);
    }
}