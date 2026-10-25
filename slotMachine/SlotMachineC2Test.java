import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Casos de prueba unitaria para SlotMachine (Ciclo 2) en modo invisible.
 */
public class SlotMachineC2Test {
    private SlotMachine machine;

    /** Starts each test with a fresh, empty machine. */
    @BeforeEach
    public void setUp() {
        machine = new SlotMachine();
    }

    /** Hides the machine to leave no graphical state after a test. */
    @AfterEach
    public void clean() {
        machine.makeInvisible();
    }

    /** Adds a wheel and verifies the operation succeeds. */
    @Test
    public void accordingCcIcshouldAddWheelSuccessfully() throws SlotMachineException {
        machine.addWheel(1);

        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    /** Adds a supported symbol to an existing wheel. */
    @Test
    public void accordingCcIcshouldAddSymbolToExistingWheel() throws SlotMachineException {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        assertTrue(machine.ok());
        assertArrayEquals(new String[] { "red" }, machine.symbols());
    }

    /** Removes the requested wheel and verifies the remaining wheel count. */
    @Test
    public void accordingCcIcshouldDeleteWheelCorrectly() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.delWheel(1);

        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    /** Swaps two unlocked wheels and verifies their symbols exchange positions. */
    @Test
    public void accordingCcIcshouldSwapUnlockedWheels() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "orange");
        machine.swap(1, 2);

        assertTrue(machine.ok());
        assertArrayEquals(new String[] { "orange", "red" }, machine.configuration());
    }

    /** Locks a wheel and then successfully unlocks it. */
    @Test
    public void accordingCcIcshouldLockAndUnlockWheel() throws SlotMachineException {
        machine.addWheel(1);
        machine.lock(1);
        assertTrue(machine.ok());

        machine.unlock(1);
        assertTrue(machine.ok());
    }

    /** Counts distinct colors across the current wheel configuration. */
    @Test
    public void accordingCcIcshouldCalculateDistinctSymbolsCorrectly() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(3, "green");

        assertEquals(2, machine.distinctSymbols());
        assertTrue(machine.ok());
    }

    /** Spins a wheel a positive number of times and verifies success. */
    @Test
    public void accordingCcIcshouldSpinWheelTheRequestedNumberOfSteps() throws SlotMachineException {
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.spin(1, 3);

        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /** Confirms that spinning zero steps leaves the selected symbol unchanged. */
    @Test
    public void accordingCcIcshouldKeepWheelSymbolWhenSpinHasZeroSteps() throws SlotMachineException {
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.spin(1, 0);

        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /** Sets each wheel to the corresponding requested color. */
    @Test
    public void accordingCcIcshouldSetConfigurationWithSpinArray() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.spin(new String[] { "red", "orange" });

        assertArrayEquals(new String[] { "red", "orange" }, machine.configuration());
        assertTrue(machine.ok());
    }

    /** Rejects adding a tenth wheel while preserving the nine-wheel limit. */
    @Test
    public void accordingCcIcshouldNotExceedMaximumWheelsLimit() throws SlotMachineException {
        for (int i = 1; i <= 9; i++) {
            machine.addWheel(i);
        }

        assertThrows(SlotMachineException.class, () -> machine.addWheel(10));
        assertFalse(machine.ok());
        assertEquals(9, machine.configuration().length);
    }

    /** Rejects wheel operations when the machine has no wheels. */
    @Test
    public void accordingCcIcshouldNotAllowOperationsWhenNoWheelsExist() {
        assertThrows(SlotMachineException.class, () -> machine.delWheel(1));
        assertFalse(machine.ok());

        assertThrows(SlotMachineException.class, () -> machine.addSymbol(1, "red"));
        assertFalse(machine.ok());

        assertThrows(SlotMachineException.class, () -> machine.spin(1));
        assertFalse(machine.ok());
    }

    /** Rejects swapping a wheel with itself. */
    @Test
    public void accordingCcIcshouldNotSwapSameWheel() throws SlotMachineException {
        machine.addWheel(1);

        assertThrows(SlotMachineException.class, () -> machine.swap(1, 1));
        assertFalse(machine.ok());
    }

    /** Rejects swapping a locked wheel. */
    @Test
    public void accordingCcIcshouldNotSwapLockedWheels() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.lock(1);

        assertThrows(SlotMachineException.class, () -> machine.swap(1, 2));
        assertFalse(machine.ok());
    }

    /** Rejects spinning a locked wheel. */
    @Test
    public void accordingCcIcshouldNotSpinLockedWheel() throws SlotMachineException {
        machine.addWheel(1);
        machine.lock(1);

        assertThrows(SlotMachineException.class, () -> machine.spin(1));
        assertFalse(machine.ok());
    }

    /** Rejects negative spin steps without changing the selected symbol. */
    @Test
    public void accordingCcIcshouldNotSpinWheelWithNegativeSteps() throws SlotMachineException {
        machine.addWheel(1);
        machine.placeSymbol(1, "red");

        assertThrows(SlotMachineException.class, () -> machine.spin(1, -1));
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /** Rejects a configuration containing an unsupported symbol color. */
    @Test
    public void accordingCcIcshouldNotSetConfigurationWithUnsupportedSymbol() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "orange");

        assertThrows(SlotMachineException.class,
                () -> machine.spin(new String[] { "red", "purple" }));
        assertFalse(machine.ok());
        assertArrayEquals(new String[] { "red", "orange" }, machine.configuration());
    }

    /** Rejects an obsolete color that is not in the current symbol palette. */
    @Test
    public void accordingCcIcshouldRejectBlueAsAnObsoleteSymbol() throws SlotMachineException {
        machine.addWheel(1);

        assertThrows(SlotMachineException.class, () -> machine.addSymbol(1, "blue"));
        assertFalse(machine.ok());
    }

    /** Accepts the supported red, black, and green symbol colors. */
    @Test
    public void accordingCcIcshouldAcceptTheThreeCurrentSymbolColors() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.spin(new String[] { "red", "black", "green" });

        assertArrayEquals(new String[] { "red", "black", "green" }, machine.configuration());
        assertTrue(machine.ok());
    }

    /** Rejects a configuration whose number of colors differs from wheel count. */
    @Test
    public void accordingCcIcshouldNotSetConfigurationWithWrongNumberOfSymbols() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);

        assertThrows(SlotMachineException.class, () -> machine.spin(new String[] { "red" }));
        assertFalse(machine.ok());
    }

    /** Clamps wheel insertion positions to the valid range. */
    @Test
    public void accordingCcIcshouldAdjustWheelPositionsWhenAdding() throws SlotMachineException {
        machine.addWheel(-5);
        assertTrue(machine.ok());

        machine.addWheel(99);
        assertTrue(machine.ok());
        assertEquals(2, machine.configuration().length);
    }
}
