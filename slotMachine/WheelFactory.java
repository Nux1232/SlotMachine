import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Maps the legacy wheel type enum to its corresponding inheritance-based
 * implementation.
 */
final class WheelFactory {
    private static final Map<Wheel.Type, Supplier<? extends Wheel>> WHEEL_TYPES =
            new EnumMap<>(Wheel.Type.class);

    static {
        WHEEL_TYPES.put(Wheel.Type.NORMAL, NormalWheel::new);
        WHEEL_TYPES.put(Wheel.Type.LEFTY, LeftyWheel::new);
        WHEEL_TYPES.put(Wheel.Type.REBEL, RebelWheel::new);
        WHEEL_TYPES.put(Wheel.Type.CRAZY, CrazyWheel::new);
    }

    /** Prevents instances of this utility class. */
    private WheelFactory() { }

    /**
     * Creates a wheel implementation for the requested type.
     *
     * @param type wheel type; {@code null} selects the standard wheel
     * @return a new instance of the matching wheel implementation
     */
    static Wheel create(Wheel.Type type) {
        Supplier<? extends Wheel> constructor = WHEEL_TYPES.get(type == null ? Wheel.Type.NORMAL : type);
        return constructor.get();
    }
}
