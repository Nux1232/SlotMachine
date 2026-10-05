import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Cycle 4 regression tests adapted to the project's JUnit 5 API. */
public class SlotMachineCC4Test {
    private SlotMachine machine;

    @BeforeEach
    public void setUp() {
        machine = new SlotMachine();
        String[] types = { "normal", "lefty", "rebel" };
        String[] colors = { "red", "green", "black" };
        for (int wheel = 1; wheel <= types.length; wheel++) {
            machine.addWheel(types[wheel - 1], wheel);
            for (String color : colors) machine.addSymbol(wheel, color);
        }
    }

    @AfterEach
    public void clean() {
        machine.makeInvisible();
    }

    @Test
    public void shouldDecreaseEphemeralSymbolSize() {
        EphemeralSymbol symbol = new EphemeralSymbol("red", "red");
        int initialSize = symbol.getSize();

        symbol.onWheelSpin();

        assertTrue(symbol.getSize() < initialSize);
    }

    @Test
    public void shouldAlternateShySymbolVisibility() {
        ShySymbol symbol = new ShySymbol("green", "green");
        boolean initialState = symbol.isShyVisible();

        symbol.onSelected();
        assertNotEquals(initialState, symbol.isShyVisible());
        symbol.onSelected();
        assertEquals(initialState, symbol.isShyVisible());
    }

    @Test
    public void leftyShouldCopyTheNormalWheelImmediatelyToItsLeft() {
        machine.placeSymbol(1, "magenta");

        machine.spin(2);

        assertTrue(machine.ok());
        assertEquals("magenta", machine.configuration()[1]);
    }

    @Test
    public void rebelShouldRejectLockSwapAndRemoval() {
        String[] original = machine.configuration();

        machine.lock(3);
        assertFalse(machine.ok());
        machine.swap(3, 1);
        assertFalse(machine.ok());
        machine.delWheel(3);
        assertFalse(machine.ok());

        assertArrayEquals(original, machine.configuration());
        assertEquals(3, machine.configuration().length);
    }

    @Test
    public void hiddenShySymbolShouldNotProduceAJackpot() {
        SlotMachine shyMachine = new SlotMachine();
        shyMachine.addWheel(1);
        shyMachine.addWheel(2);
        shyMachine.addSymbol("shy", 1, "red");
        shyMachine.placeSymbol(2, "red");
        assertTrue(shyMachine.isJackpot());

        shyMachine.spin(1);

        assertFalse(shyMachine.isJackpot());
        assertEquals("", shyMachine.configuration()[0]);
        shyMachine.makeInvisible();
    }

    @Test
    public void allWheelTypesAreAcceptedAndCrazyKeepsAValidSymbol() {
        SlotMachine typedMachine = new SlotMachine();
        typedMachine.addWheel(1, Wheel.Type.CRAZY);
        typedMachine.addSymbol(1, "red");
        typedMachine.addSymbol(1, "green");
        for (int i = 0; i < 20; i++) typedMachine.spin(1);

        assertEquals(Wheel.Type.CRAZY, typedMachine.wheelType(1));
        assertTrue("red".equals(typedMachine.configuration()[0])
                || "green".equals(typedMachine.configuration()[0]));
        typedMachine.makeInvisible();
    }
}
