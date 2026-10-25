import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests the Cycle 1 slot-machine requirements against the current API. */
public class SlotMachineCC1Test {
    private SlotMachine machine;

    /** Starts each test with an empty machine. */
    @BeforeEach
    public void setUp() {
        machine = new SlotMachine();
    }

    /** Hides the machine after each test. */
    @AfterEach
    public void tearDown() {
        machine.makeInvisible();
    }

    /** A new machine has no wheels and reports an empty configuration. */
    @Test
    public void shouldCreateAnEmptyMachine() {
        assertArrayEquals(new String[0], machine.configuration());
        assertEquals(0, machine.distinctSymbols());
    }

    /** Adds two wheels and verifies their insertion order and types. */
    @Test
    public void shouldAddTypedWheelsInRequestedOrder() throws SlotMachineException {
        machine.addWheel(1, "normal");
        machine.addWheel(2, "lefty");

        assertTrue(machine.ok());
        assertEquals(Wheel.Type.NORMAL, machine.wheelType(1));
        assertEquals(Wheel.Type.LEFTY, machine.wheelType(2));
        assertArrayEquals(new String[] { "white", "white" }, machine.configuration());
    }

    /** Deletes one wheel while retaining the other wheel and its symbol. */
    @Test
    public void shouldDeleteOnlyTheRequestedWheel() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");

        machine.delWheel(1);

        assertTrue(machine.ok());
        assertArrayEquals(new String[] { "green" }, machine.configuration());
    }

    /** Lists each available color once even when it appears on multiple wheels. */
    @Test
    public void shouldReturnUniqueColorsFromAllWheels() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "green");
        machine.addSymbol(2, "red");

        assertArrayEquals(new String[] { "red", "green" }, machine.symbols());
    }

    /** Reports each wheel's currently selected color in positional order. */
    @Test
    public void shouldReturnTheCurrentWheelConfiguration() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "magenta");
        machine.placeSymbol(2, "cyan");

        assertArrayEquals(new String[] { "magenta", "cyan" }, machine.configuration());
    }

    /** Rejects unsupported symbol colors without changing the wheel. */
    @Test
    public void shouldRejectUnsupportedColors() throws SlotMachineException {
        machine.addWheel(1);
        machine.placeSymbol(1, "orange");

        SlotMachineException exception = assertThrows(
                SlotMachineException.class, () -> machine.addSymbol(1, "blue"));

        assertEquals(SlotMachineException.SYMBOL_NOT_ALLOWED, exception.getMessage());
        assertFalse(machine.ok());
        assertArrayEquals(new String[] { "orange" }, machine.configuration());
    }
}
