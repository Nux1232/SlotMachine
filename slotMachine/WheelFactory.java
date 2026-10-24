import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/** Creates the built-in wheel implementations for the legacy wheel types. */
final class WheelFactory {
    private static final Map<Wheel.Type, Supplier<? extends Wheel>> WHEEL_TYPES =
            new EnumMap<>(Wheel.Type.class);

    static {
        WHEEL_TYPES.put(Wheel.Type.NORMAL, NormalWheel::new);
        WHEEL_TYPES.put(Wheel.Type.LEFTY, LeftyWheel::new);
        WHEEL_TYPES.put(Wheel.Type.REBEL, RebelWheel::new);
        WHEEL_TYPES.put(Wheel.Type.CRAZY, CrazyWheel::new);
    }

    private WheelFactory() { }

    static Wheel create(Wheel.Type type) {
        Supplier<? extends Wheel> constructor = WHEEL_TYPES.get(type == null ? Wheel.Type.NORMAL : type);
        return constructor.get();
    }
}
