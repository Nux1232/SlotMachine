import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import org.junit.jupiter.api.Test;

/** Regression tests for wheel and symbol features added in Cycle 4. */
public class SlotMachineC4Test {

    @Test
    public void shouldExposeAllFourWheelTypesThroughBlueJFriendlyMethods() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1, "normal");
        machine.addWheel(2, Wheel.Type.LEFTY);
        machine.addWheel(3, "rebel");
        machine.addWheel(4, "CRAZY");

        assertArrayEquals(new Wheel.Type[] {
                Wheel.Type.NORMAL, Wheel.Type.LEFTY, Wheel.Type.REBEL, Wheel.Type.CRAZY
        }, new Wheel.Type[] { machine.wheelType(1), machine.wheelType(2),
                machine.wheelType(3), machine.wheelType(4) });
        assertTrue(machine.ok());
    }

    @Test
    public void shouldRejectUnknownWheelTypeWithoutAddingAWheel() {
        SlotMachine machine = new SlotMachine();

        machine.addWheel(1, "confused");

        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }

    @Test
    public void leftyShouldCopyItsImmediateLeftNeighborWhenSpun() {
        Wheel left = new Wheel(Wheel.Type.NORMAL);
        Wheel lefty = new Wheel(Wheel.Type.LEFTY);
        left.addSymbolWheel("orange");

        lefty.spin("pink", left.symbolColor());

        assertEquals("orange", lefty.symbolColor());
    }

    @Test
    public void crazyWheelShouldOnlyUseItsSpinOptions() {
        Wheel crazy = new Wheel(Wheel.Type.CRAZY);
        crazy.addSymbolWheel("yellow");
        for (int i = 0; i < 100; i++) crazy.spin("red", "black");

        assertTrue("red".equals(crazy.symbolColor())
                || "black".equals(crazy.symbolColor())
                || "yellow".equals(crazy.symbolColor()));
    }

    @Test
    public void machineSpinWithStepsShouldApplyLeftyRule() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2, Wheel.Type.LEFTY);
        machine.placeSymbol(1, "magenta");
        machine.placeSymbol(2, "black");

        machine.spin(2, 1);

        assertArrayEquals(new String[] { "magenta", "magenta" }, machine.configuration());
        assertTrue(machine.ok());
    }

    @Test
    public void rebelShouldRejectLockSwapAndRemoval() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1, Wheel.Type.REBEL);
        machine.addWheel(2);

        machine.lock(1);
        assertFalse(machine.ok());
        machine.swap(1, 2);
        assertFalse(machine.ok());
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertEquals(2, machine.configuration().length);
    }

    @Test
    public void shouldRejectUnsupportedSymbolsWithoutReplacingCurrentSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.placeSymbol(1, "red");

        machine.placeSymbol(1, "purple");

        assertFalse(machine.ok());
        assertArrayEquals(new String[] { "red" }, machine.configuration());
    }

    @Test
    public void readOnlyQueriesShouldNotEraseTheLastOperationResult() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.spin(1, -1);

        machine.configuration();
        machine.symbols();
        machine.distinctSymbols();

        assertFalse(machine.ok());
    }

    @Test
    public void shySymbolShouldAlternateVisibilityOnSpinEffect() {
        ShySymbol symbol = new ShySymbol("green");

        assertFalse(symbol.isHidden());
        symbol.spinEffect();
        assertTrue(symbol.isHidden());
        symbol.spinEffect();
        assertFalse(symbol.isHidden());
    }

    @Test
    public void ephemeralSymbolShouldShrinkToItsMinimumSize() {
        EphemeralSymbol symbol = new EphemeralSymbol("red");

        for (int i = 0; i < 20; i++) symbol.spinEffect();

        assertEquals(10, symbol.getSize());
    }

    @Test
    public void wheelSpinShouldApplySpecialSymbolEffects() {
        Wheel wheel = new Wheel();
        EphemeralSymbol ephemeral = new EphemeralSymbol("red");
        wheel.addSymbolWheel(ephemeral);
        wheel.spin("black", null);
        assertEquals(45, ephemeral.getSize());
        assertEquals("red", wheel.symbolColor());

        ShySymbol shy = new ShySymbol("green");
        wheel.addSymbolWheel(shy);
        wheel.spin("black", null);
        assertTrue(shy.isHidden());
    }

    @Test
    public void oneWheelConstructorShouldFinishAndNegativeCountShouldBeEmpty() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> new SlotMachine(1));
        SlotMachine empty = new SlotMachine(-3);
        assertNotNull(empty.configuration());
        assertEquals(0, empty.configuration().length);
    }
}
