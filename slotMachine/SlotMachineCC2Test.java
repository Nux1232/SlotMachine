import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Pruebas adicionales para SlotMachine (Ciclo 2). */
public class SlotMachineCC2Test {
    private SlotMachine slotMachine;

    /** Creates an empty machine for the current test. */
    @BeforeEach
    public void setUp() {
        slotMachine = new SlotMachine();
    }

    /** Hides the machine after each test. */
    @AfterEach
    public void clean() {
        slotMachine.makeInvisible();
    }

    /** Verifies that swapping two wheels also swaps their selected symbols. */
    @Test
    public void accordingMsRhShouldSwapSymbolsBetweenTwoValidWheels() throws SlotMachineException {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "black");

        slotMachine.swap(1, 2);

        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[] { "black", "red" }, slotMachine.configuration());
    }

    /** Confirms a locked wheel cannot be spun and keeps its current symbol. */
    @Test
    public void accordingMsRhShouldNotChangeLockedWheelWhenSpinning() throws SlotMachineException {
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "red");
        slotMachine.lock(1);

        assertThrows(SlotMachineException.class, () -> slotMachine.spin(1));

        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[] { "red" }, slotMachine.configuration());
    }

    /** Sets a winning configuration and checks that the machine reports a jackpot. */
    @Test
    public void accordingMsRhShouldDetectJackpotAfterForcedSpin() throws SlotMachineException {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.spin(new String[] { "red", "red" });

        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[] { "red", "red" }, slotMachine.configuration());
        assertTrue(slotMachine.isjackpot());
    }

    /** Confirms an out-of-range deletion position removes the last wheel. */
    @Test
    public void accordingMsRhShouldDeleteLastWheelWhenPositionGreaterThanSize()
            throws SlotMachineException {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.delWheel(10);

        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[] { "white" }, slotMachine.configuration());
    }

    /** Verifies the currently supported symbol colors can be assigned to wheels. */
    @Test
    public void shouldAcceptTheCurrentSymbolColors() throws SlotMachineException {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "black");
        slotMachine.addSymbol(3, "green");

        assertArrayEquals(new String[] { "red", "black", "green" }, slotMachine.configuration());
        assertTrue(slotMachine.ok());
    }

    /** Rejects blue because it is not in the current symbol palette. */
    @Test
    public void shouldRejectBlueAsASymbol() throws SlotMachineException {
        slotMachine.addWheel(1);

        assertThrows(SlotMachineException.class, () -> slotMachine.addSymbol(1, "blue"));

        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[] { "white" }, slotMachine.configuration());
    }
}
