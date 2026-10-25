import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Additional, non-duplicated Cycle 1 tests for SlotMachine. */
public class SlotMachineC1Test {
    private SlotMachine machine;

    /** Creates a fresh machine before each test. */
    @BeforeEach
    public void setUp() {
        machine = new SlotMachine();
    }

    /** Keeps graphical state from one test from affecting another. */
    @AfterEach
    public void tearDown() {
        machine.makeInvisible();
    }

    /** The sized constructor creates the requested number of initialized wheels. */
    @Test
    public void sizedConstructorCreatesRequestedWheels() throws SlotMachineException {
        SlotMachine initialized = new SlotMachine(3);

        assertEquals(3, initialized.configuration().length);
        assertTrue(initialized.symbols().length > 0);
        initialized.makeInvisible();
    }

    /** Wheel insertion positions outside the range are clamped to the nearest end. */
    @Test
    public void addWheelClampsPositionsAtBothEnds() throws SlotMachineException {
        machine.addWheel(-10, Wheel.Type.LEFTY);
        machine.addWheel(100, Wheel.Type.CRAZY);

        assertEquals(Wheel.Type.LEFTY, machine.wheelType(1));
        assertEquals(Wheel.Type.CRAZY, machine.wheelType(2));
        assertTrue(machine.ok());
    }

    /** A forced configuration updates every unlocked wheel and detects a jackpot. */
    @Test
    public void forcedSpinCanCreateAJackpot() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.spin(new String[] { "yellow", "yellow", "yellow" });

        assertArrayEquals(new String[] { "yellow", "yellow", "yellow" }, machine.configuration());
        assertTrue(machine.isJackpot());
    }

    /** A locked wheel keeps its color while a forced spin changes unlocked wheels. */
    @Test
    public void forcedSpinPreservesLockedWheelWhenColorMatches() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");
        machine.lock(1);

        machine.spin(new String[] { "red", "cyan" });

        assertArrayEquals(new String[] { "red", "cyan" }, machine.configuration());
        assertTrue(machine.ok());
    }

    /** Rejects forced configurations that would change a locked wheel. */
    @Test
    public void forcedSpinRejectsChangingLockedWheel() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");
        machine.lock(1);

        SlotMachineException exception = assertThrows(SlotMachineException.class,
                () -> machine.spin(new String[] { "black", "cyan" }));

        assertEquals(SlotMachineException.CONFIGURATION_LOCKED, exception.getMessage());
        assertFalse(machine.ok());
        assertArrayEquals(new String[] { "red", "green" }, machine.configuration());
    }

    /** Rejects invalid wheel positions for operations that require an existing wheel. */
    @Test
    public void wheelLookupRejectsMissingPositions() throws SlotMachineException {
        machine.addWheel(1);

        SlotMachineException exception = assertThrows(SlotMachineException.class,
                () -> machine.wheelType(2));

        assertEquals(SlotMachineException.WHEEL_NOT_FOUND, exception.getMessage());
        assertThrows(SlotMachineException.class, () -> machine.lock(0));
        assertFalse(machine.ok());
    }
}
