
/**
 * Checked exception for invalid slot-machine operations.
 *
 * <p>The constants in this class provide standard messages for invalid wheel,
 * symbol, locking, and configuration operations.</p>
 */
public class SlotMachineException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** Error message used when a requested wheel does not exist. */
    public static final String WHEEL_NOT_FOUND = "Error: The Wheel does not exist or hasn't been found.";
    /** Error message used when a symbol color is unsupported. */
    public static final String SYMBOL_NOT_ALLOWED = "Error: The Symbol is not allowed";
    /** Error message used when a symbol type cannot be resolved. */
    public static final String SYMBOL_TYPE_NOTFOUND = "Error: The Symbol type is not found or can not be null";
    /** Error message used when adding a wheel exceeds the machine limit. */
    public static final String WHEEL_LIMIT_REACHED = "Error: The machine can not have more than 9 wheels";
    /** Error message used when a wheel type cannot be resolved. */
    public static final String WHEEL_TYPE_NOTFOUND = "Error: The wheel type is unknown or the wheel is null";
    /** Error message used when a rebel wheel operation is forbidden. */
    public static final String WHEEL_REBEL = "Error: A rebel wheel can not be deleted, locked, unlocked or swapped";
    /** Error message used when an operation targets a locked wheel. */
    public static final String WHEEL_LOCKED = "Error: The Wheel is locked";
    /** Error message used when attempting to swap a wheel with itself. */
    public static final String WHEEL_SAME_POSITION = "Error: A wheel can not be swapped with itself";
    /** Error message used when the requested spin step count is negative. */
    public static final String STEPS_NEGATIVE = "Error: The number of steps can not be negative";
    /** Error message used when a spin animation is interrupted. */
    public static final String SPIN_INTERRUPTED = "Error: The spin was interrupted";
    /** Error message used when a configuration is missing or invalid. */
    public static final String CONFIGURATION_INVALID = "Error: The configuration is null, empty or does not contain exactly one symbol per wheel";
    /** Error message used when a configuration changes a locked wheel. */
    public static final String CONFIGURATION_LOCKED = "Error: The configuration changes a locked wheel";

    /**
     * Creates an exception with a descriptive operation error.
     *
     * @param message explanation of the invalid operation
     */
    public SlotMachineException(String message) {
        super(message);
    }
}